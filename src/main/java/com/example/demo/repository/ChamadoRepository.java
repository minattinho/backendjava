package com.example.demo.repository;

import com.example.demo.model.Chamado;
import com.example.demo.model.Cliente;
import com.example.demo.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

    List<Chamado> findByTecnico(Tecnico tecnico);
    
    List<Chamado> findByCliente(Cliente cliente);
    
    List<Chamado> findByTecnicoAndStatus(Tecnico tecnico, Integer status);
    
    List<Chamado> findByClienteAndStatus(Cliente cliente, Integer status);
}

