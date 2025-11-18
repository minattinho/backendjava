package com.example.demo.model;

import jakarta.persistence.Entity;

@Entity
public class Tecnico extends Pessoa {

    public Tecnico() {
        super();
    }

    public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
    }
}
