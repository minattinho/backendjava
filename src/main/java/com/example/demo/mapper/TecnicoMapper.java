package com.example.demo.mapper;

import com.example.demo.dto.TecnicoDTO;
import com.example.demo.model.Tecnico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TecnicoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "senha", source = "senha")
    TecnicoDTO toDTO(Tecnico tecnico);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "senha", source = "senha")
    Tecnico toEntity(TecnicoDTO tecnicoDTO);
}

