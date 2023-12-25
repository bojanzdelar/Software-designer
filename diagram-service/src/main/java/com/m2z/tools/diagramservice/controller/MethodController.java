package com.m2z.tools.diagramservice.controller;

import com.m2z.tools.shared.controller.BaseController;
import com.m2z.tools.diagramservice.dto.MethodDTO;
import com.m2z.tools.diagramservice.model.Method;
import com.m2z.tools.diagramservice.service.MethodService;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/methods")
public class MethodController extends BaseController<Method, MethodDTO, Long> {
    private final MethodService service;

    public MethodController(MethodService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Page<MethodDTO>> getAllByProject(
            @ParameterObject Pageable pageable,
            @RequestParam(defaultValue = "") String search,
            @PathVariable String id) {
        return new ResponseEntity<>(service.findAllByProject(pageable, search, id), HttpStatus.OK);
    }

    @GetMapping("/class/{id}")
    public ResponseEntity<Page<MethodDTO>> getAllByClass(
            @ParameterObject Pageable pageable,
            @RequestParam(defaultValue = "") String search,
            @PathVariable String id) {
        return new ResponseEntity<>(service.findAllByClass(pageable, search, id), HttpStatus.OK);
    }
}
