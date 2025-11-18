package com.example.demo.model;

import jakarta.persistence.Entity;

@Entity
public class Cliente extends Pessoa {

    public Cliente() {
        super();
    }

    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
    }
}
