package com.example.demo.service;

import com.example.demo.dto.ChamadoDTO;
import com.example.demo.exceptions.DataIntegrityViolationException;
import com.example.demo.exceptions.ObjectNotFoundException;
import com.example.demo.model.Chamado;
import com.example.demo.model.Cliente;
import com.example.demo.model.Tecnico;
import com.example.demo.model.enums.Prioridade;
import com.example.demo.model.enums.Status;
import com.example.demo.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private ClienteService clienteService;

    public Chamado findById(Integer id) {
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado! Id: " + id));
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado create(ChamadoDTO objDTO) {
        return repository.save(newChamado(objDTO));
    }

    public Chamado update(Integer id, ChamadoDTO objDTO) {
        objDTO.setId(id);
        Chamado oldObj = findById(id);
        oldObj = newChamado(objDTO);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Chamado obj = findById(id);
        
        // Regra 3: Verificar se o chamado está em aberto
        if (obj.getStatus() != null && obj.getStatus().ordinal() != Status.ENCERRADO.ordinal()) {
            throw new DataIntegrityViolationException("Chamado não pode ser deletado pois está em aberto!");
        }
        
        repository.deleteById(id);
    }

    private Chamado newChamado(ChamadoDTO objDTO) {
        Tecnico tecnico = tecnicoService.findById(objDTO.getTecnico());
        Cliente cliente = clienteService.findById(objDTO.getCliente());

        Chamado chamado = new Chamado();
        if (objDTO.getId() != null) {
            chamado.setId(objDTO.getId());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setTitulo(objDTO.getTitulo());
        chamado.setObservacoes(objDTO.getObservacoes());

        if (objDTO.getPrioridade() != null) {
            chamado.setPrioridade(Prioridade.values()[objDTO.getPrioridade()]);
        }
        if (objDTO.getStatus() != null) {
            chamado.setStatus(Status.values()[objDTO.getStatus()]);
        }

        if (objDTO.getStatus() != null && objDTO.getStatus() == 2) {
            chamado.setDataFechamento(java.time.LocalDate.now());
        }

        return chamado;
    }
}

