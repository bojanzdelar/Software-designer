package com.m2z.tools.requirementservice.service;

import com.m2z.tools.requirementservice.dto.RequirementRiskDTO;
import com.m2z.tools.requirementservice.mapper.RequirementRiskMapper;
import com.m2z.tools.requirementservice.model.RequirementRisk;
import com.m2z.tools.requirementservice.repository.RequirementRiskRepository;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.stereotype.Service;

@Service
public class RequirementRiskService extends BaseService<RequirementRisk, RequirementRiskDTO, Long> {
    private final RequirementRiskRepository repository;
    private final RequirementRiskMapper mapper;

    public RequirementRiskService(
            RequirementRiskRepository repository, RequirementRiskMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
