package com.m2z.tools.diagramservice.controller;

import com.m2z.tools.shared.controller.BaseController;
import com.m2z.tools.diagramservice.dto.AccessTypeDTO;
import com.m2z.tools.diagramservice.model.AccessType;
import com.m2z.tools.diagramservice.service.AccessTypeService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/access-types")
public class AccessTypeController extends BaseController<AccessType, AccessTypeDTO, Long> {
    private final AccessTypeService service;

    public AccessTypeController(AccessTypeService service) {
        super(service);
        this.service = service;
    }
}
