package com.example.demo.controller;

import com.example.demo.dto.TecnicoDTO;
import com.example.demo.mapper.TecnicoMapper;
import com.example.demo.model.Tecnico;
import com.example.demo.service.TecnicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tecnicos")
@Tag(name = "Técnicos", description = "Endpoints para gerenciamento de técnicos")
public class TecnicoController {

    @Autowired
    private TecnicoService service;

    @Autowired
    private TecnicoMapper mapper;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar técnico por ID", description = "Retorna um técnico específico pelo seu ID")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(mapper.toDTO(obj));
    }

    @GetMapping
    @Operation(summary = "Listar todos os técnicos", description = "Retorna uma lista com todos os técnicos cadastrados")
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = list.stream().map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    @Operation(summary = "Criar novo técnico", description = "Cadastra um novo técnico no sistema")
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar técnico", description = "Atualiza os dados de um técnico existente")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(mapper.toDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletar técnico", description = "Remove um técnico do sistema pelo ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

