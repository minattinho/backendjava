package com.example.demo.service;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.exceptions.DataIntegrityViolationException;
import com.example.demo.exceptions.ObjectNotFoundException;
import com.example.demo.model.Chamado;
import com.example.demo.model.Cliente;
import com.example.demo.model.enums.Status;
import com.example.demo.repository.ChamadoRepository;
import com.example.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        
        // Regra 1: Verificar duplicidade de CPF
        Optional<Cliente> objByCpf = repository.findByCpf(objDTO.getCpf());
        if (objByCpf.isPresent()) {
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }
        
        // Regra 2: Verificar duplicidade de Email
        Optional<Cliente> objByEmail = repository.findByEmail(objDTO.getEmail());
        if (objByEmail.isPresent()) {
            throw new DataIntegrityViolationException("Email já cadastrado na base de dados!");
        }
        
        Cliente newObj = new Cliente();
        newObj.setNome(objDTO.getNome());
        newObj.setCpf(objDTO.getCpf());
        newObj.setEmail(objDTO.getEmail());
        newObj.setSenha(objDTO.getSenha());
        return repository.save(newObj);
    }

    public Cliente update(Integer id, ClienteDTO objDTO) {
        Cliente oldObj = findById(id);
        
        // Regra 1: Verificar duplicidade de CPF (exceto o próprio registro)
        Optional<Cliente> objByCpf = repository.findByCpf(objDTO.getCpf());
        if (objByCpf.isPresent() && !objByCpf.get().getId().equals(id)) {
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }
        
        // Regra 2: Verificar duplicidade de Email (exceto o próprio registro)
        Optional<Cliente> objByEmail = repository.findByEmail(objDTO.getEmail());
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
        Cliente obj = findById(id);
        
        // Regra 3: Verificar se existem chamados em aberto
        List<Chamado> chamados = chamadoRepository.findByCliente(obj);
        for (Chamado chamado : chamados) {
            if (chamado.getStatus() != null && chamado.getStatus().ordinal() != Status.ENCERRADO.ordinal()) {
                throw new DataIntegrityViolationException("Cliente possui ordens de serviço em aberto e não pode ser deletado!");
            }
        }
        
        repository.deleteById(id);
    }
}

