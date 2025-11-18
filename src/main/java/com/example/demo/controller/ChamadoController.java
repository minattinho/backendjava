package com.example.demo.controller;

import com.example.demo.dto.ChamadoDTO;
import com.example.demo.mapper.ChamadoMapper;
import com.example.demo.model.Chamado;
import com.example.demo.service.ChamadoService;
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
@RequestMapping(value = "/chamados")
@Tag(name = "Chamados", description = "Endpoints para gerenciamento de chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService service;

    @Autowired
    private ChamadoMapper mapper;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar chamado por ID", description = "Retorna um chamado específico pelo seu ID")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
        Chamado obj = service.findById(id);
        return ResponseEntity.ok().body(mapper.toDTO(obj));
    }

    @GetMapping
    @Operation(summary = "Listar todos os chamados", description = "Retorna uma lista com todos os chamados cadastrados")
    public ResponseEntity<List<ChamadoDTO>> findAll() {
        List<Chamado> list = service.findAll();
        List<ChamadoDTO> listDTO = list.stream().map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    @Operation(summary = "Criar novo chamado", description = "Cadastra um novo chamado no sistema")
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO objDTO) {
        Chamado newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar chamado", description = "Atualiza os dados de um chamado existente")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO objDTO) {
        Chamado obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(mapper.toDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletar chamado", description = "Remove um chamado do sistema pelo ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

