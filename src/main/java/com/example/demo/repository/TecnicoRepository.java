package com.example.demo.repository;

import com.example.demo.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    Optional<Tecnico> findByCpf(String cpf);
    
    Optional<Tecnico> findByEmail(String email);
}

