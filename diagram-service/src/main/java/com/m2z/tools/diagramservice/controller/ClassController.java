package com.m2z.tools.diagramservice.controller;

import com.m2z.tools.diagramservice.dto.ClassDTO;
import com.m2z.tools.diagramservice.service.ClassService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService service;

    @GetMapping("/all")
    public ResponseEntity<List<ClassDTO>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ClassDTO>> getAll(
            @ParameterObject Pageable pageable, @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(service.findAll(pageable, search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ClassDTO>> get(@PathVariable Set<String> id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Page<ClassDTO>> getAllByProject(
            @ParameterObject Pageable pageable,
            @RequestParam(defaultValue = "") String search,
            @PathVariable String id) {
        return new ResponseEntity<>(service.findAllByProject(pageable, search, id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClassDTO> create(@Valid @RequestBody ClassDTO DTO) {
        DTO.setId(null);
        return new ResponseEntity<>(service.save(DTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDTO> update(
            @PathVariable String id, @Valid @RequestBody ClassDTO DTO) {
        DTO.setId(id);
        return new ResponseEntity<>(service.save(DTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Set<String> id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
