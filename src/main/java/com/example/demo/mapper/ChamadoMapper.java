package com.example.demo.mapper;

import com.example.demo.dto.ChamadoDTO;
import com.example.demo.model.Chamado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChamadoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "dataAbertura", source = "dataAbertura")
    @Mapping(target = "dataFechamento", source = "dataFechamento")
    @Mapping(target = "prioridade", expression = "java(chamado.getPrioridade() != null ? chamado.getPrioridade().ordinal() : null)")
    @Mapping(target = "status", expression = "java(chamado.getStatus() != null ? chamado.getStatus().ordinal() : null)")
    @Mapping(target = "titulo", source = "titulo")
    @Mapping(target = "observacoes", source = "observacoes")
    @Mapping(target = "tecnico", source = "tecnico.id")
    @Mapping(target = "cliente", source = "cliente.id")
    @Mapping(target = "nomeTecnico", source = "tecnico.nome")
    @Mapping(target = "nomeCliente", source = "cliente.nome")
    ChamadoDTO toDTO(Chamado chamado);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "dataAbertura", source = "dataAbertura")
    @Mapping(target = "dataFechamento", source = "dataFechamento")
    @Mapping(target = "titulo", source = "titulo")
    @Mapping(target = "observacoes", source = "observacoes")
    @Mapping(target = "tecnico", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "prioridade", ignore = true)
    @Mapping(target = "status", ignore = true)
    Chamado toEntity(ChamadoDTO chamadoDTO);
}

