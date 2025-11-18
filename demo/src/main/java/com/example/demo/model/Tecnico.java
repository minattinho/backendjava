// model/Tecnico.java
package com.example.demo.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Tecnico extends Pessoa {
    private String especialidade;
}
