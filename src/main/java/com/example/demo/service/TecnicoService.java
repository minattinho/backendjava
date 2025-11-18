package com.example.demo.service;

import com.example.demo.dto.TecnicoDTO;
import com.example.demo.exceptions.DataIntegrityViolationException;
import com.example.demo.exceptions.ObjectNotFoundException;
import com.example.demo.model.Chamado;
import com.example.demo.model.Tecnico;
import com.example.demo.model.enums.Status;
import com.example.demo.repository.ChamadoRepository;
import com.example.demo.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Técnico não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        
        // Regra 1: Verificar duplicidade de CPF
        Optional<Tecnico> objByCpf = repository.findByCpf(objDTO.getCpf());
        if (objByCpf.isPresent()) {
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }
        
        // Regra 2: Verificar duplicidade de Email
        Optional<Tecnico> objByEmail = repository.findByEmail(objDTO.getEmail());
        if (objByEmail.isPresent()) {
            throw new DataIntegrityViolationException("Email já cadastrado na base de dados!");
        }
        
        Tecnico newObj = new Tecnico();
        newObj.setNome(objDTO.getNome());
        newObj.setCpf(objDTO.getCpf());
        newObj.setEmail(objDTO.getEmail());
        newObj.setSenha(objDTO.getSenha());
        return repository.save(newObj);
    }

    public Tecnico update(Integer id, TecnicoDTO objDTO) {
        Tecnico oldObj = findById(id);
        
        // Regra 1: Verificar duplicidade de CPF (exceto o próprio registro)
        Optional<Tecnico> objByCpf = repository.findByCpf(objDTO.getCpf());
        if (objByCpf.isPresent() && !objByCpf.get().getId().equals(id)) {
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }
        
        // Regra 2: Verificar duplicidade de Email (exceto o próprio registro)
        Optional<Tecnico> objByEmail = repository.findByEmail(objDTO.getEmail());
        if (objByEmail.isPresent() && !objByEmail.get().getId().equals(id)) {
            throw new DataIntegrityViolationException("Email já cadastrado na base de dados!");
        }
        
        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setEmail(objDTO.getEmail());
        oldObj.setSenha(objDTO.getSenha());
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        
        // Regra 3: Verificar se existem chamados em aberto
        List<Chamado> chamados = chamadoRepository.findByTecnico(obj);
        for (Chamado chamado : chamados) {
            if (chamado.getStatus() != null && chamado.getStatus().ordinal() != Status.ENCERRADO.ordinal()) {
                throw new DataIntegrityViolationException("Técnico possui ordens de serviço em aberto e não pode ser deletado!");
            }
        }
        
        repository.deleteById(id);
    }
}

