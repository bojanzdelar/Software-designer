package com.m2z.tools.diagramservice.controller;

import com.m2z.tools.shared.controller.BaseController;
import com.m2z.tools.diagramservice.dto.ClassTypeDTO;
import com.m2z.tools.diagramservice.model.ClassType;
import com.m2z.tools.diagramservice.service.ClassTypeService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/class-types")
public class ClassTypeController extends BaseController<ClassType, ClassTypeDTO, Long> {
    private final ClassTypeService service;

    public ClassTypeController(ClassTypeService service) {
        super(service);
        this.service = service;
    }
}
