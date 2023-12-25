package com.m2z.tools.requirementservice.controller;

import com.m2z.tools.shared.controller.BaseController;
import com.m2z.tools.requirementservice.dto.RequirementRiskDTO;
import com.m2z.tools.requirementservice.model.RequirementRisk;
import com.m2z.tools.requirementservice.service.RequirementRiskService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/requirement-risks")
public class RequirementRiskController
        extends BaseController<RequirementRisk, RequirementRiskDTO, Long> {
    private final RequirementRiskService service;

    public RequirementRiskController(RequirementRiskService service) {
        super(service);
        this.service = service;
    }
}
