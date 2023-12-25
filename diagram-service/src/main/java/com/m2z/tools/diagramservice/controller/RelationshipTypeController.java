package com.m2z.tools.diagramservice.controller;

import com.m2z.tools.shared.controller.BaseController;
import com.m2z.tools.diagramservice.dto.RelationshipTypeDTO;
import com.m2z.tools.diagramservice.model.RelationshipType;
import com.m2z.tools.diagramservice.service.RelationshipTypeService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relationship-types")
public class RelationshipTypeController
        extends BaseController<RelationshipType, RelationshipTypeDTO, Long> {
    private final RelationshipTypeService service;

    public RelationshipTypeController(RelationshipTypeService service) {
        super(service);
        this.service = service;
    }
}
