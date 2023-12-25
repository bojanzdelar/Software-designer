package com.m2z.tools.requirementservice.controller;

import com.m2z.tools.shared.controller.BaseController;
import com.m2z.tools.requirementservice.dto.RequirementTypeDTO;
import com.m2z.tools.requirementservice.model.RequirementType;
import com.m2z.tools.requirementservice.service.RequirementTypeService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/requirement-types")
public class RequirementTypeController
        extends BaseController<RequirementType, RequirementTypeDTO, Long> {
    private final RequirementTypeService service;

    public RequirementTypeController(RequirementTypeService service) {
        super(service);
        this.service = service;
    }
}
