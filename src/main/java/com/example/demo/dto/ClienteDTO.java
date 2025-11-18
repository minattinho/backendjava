package com.example.demo.dto;

import com.example.demo.validation.CPF;
import com.example.demo.validation.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    
    @NotEmpty(message = "O campo NOME é requerido")
    private String nome;
    
    @NotEmpty(message = "O campo CPF é requerido")
    @CPF(message = "CPF inválido")
    private String cpf;
    
    @NotEmpty(message = "O campo EMAIL é requerido")
    @Email(message = "Email inválido")
    private String email;
    
    @NotEmpty(message = "O campo SENHA é requerido")
    private String senha;

    public ClienteDTO() {
        super();
    }

    public ClienteDTO(Integer id, String nome, String cpf, String email, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
