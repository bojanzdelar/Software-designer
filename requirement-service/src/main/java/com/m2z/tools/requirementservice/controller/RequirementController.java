package com.m2z.tools.requirementservice.controller;

import com.m2z.tools.requirementservice.dto.RequirementDTO;
import com.m2z.tools.requirementservice.model.Requirement;
import com.m2z.tools.requirementservice.service.RequirementService;
import com.m2z.tools.shared.controller.BaseController;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requirements")
public class RequirementController extends BaseController<Requirement, RequirementDTO, Long> {
    private final RequirementService service;

    public RequirementController(RequirementService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Page<RequirementDTO>> getAllByProject(
            @ParameterObject Pageable pageable,
            @RequestParam(defaultValue = "") String search,
            @PathVariable String id) {
        return new ResponseEntity<>(service.findAllByProject(pageable, search, id), HttpStatus.OK);
    }
}
