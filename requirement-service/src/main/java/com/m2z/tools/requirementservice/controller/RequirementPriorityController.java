package com.m2z.tools.requirementservice.controller;

import com.m2z.tools.shared.controller.BaseController;
import com.m2z.tools.requirementservice.dto.RequirementPriorityDTO;
import com.m2z.tools.requirementservice.model.RequirementPriority;
import com.m2z.tools.requirementservice.service.RequirementPriorityService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/requirement-priorities")
public class RequirementPriorityController
        extends BaseController<RequirementPriority, RequirementPriorityDTO, Long> {
    private final RequirementPriorityService service;

    public RequirementPriorityController(RequirementPriorityService service) {
        super(service);
        this.service = service;
    }
}
