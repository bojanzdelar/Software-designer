package com.m2z.tools.requirementservice.controller;

import com.m2z.tools.shared.controller.BaseController;
import com.m2z.tools.requirementservice.dto.RequirementStatusDTO;
import com.m2z.tools.requirementservice.model.RequirementStatus;
import com.m2z.tools.requirementservice.service.RequirementStatusService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/requirement-statuses")
public class RequirementStatusController
        extends BaseController<RequirementStatus, RequirementStatusDTO, Long> {
    private final RequirementStatusService service;

    public RequirementStatusController(RequirementStatusService service) {
        super(service);
        this.service = service;
    }
}
