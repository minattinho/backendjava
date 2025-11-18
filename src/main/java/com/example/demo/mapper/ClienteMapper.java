package com.example.demo.mapper;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "senha", source = "senha")
    ClienteDTO toDTO(Cliente cliente);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "senha", source = "senha")
    Cliente toEntity(ClienteDTO clienteDTO);
}

