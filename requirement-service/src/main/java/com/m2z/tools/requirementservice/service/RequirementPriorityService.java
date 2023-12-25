package com.m2z.tools.requirementservice.service;

import com.m2z.tools.requirementservice.dto.RequirementPriorityDTO;
import com.m2z.tools.requirementservice.mapper.RequirementPriorityMapper;
import com.m2z.tools.requirementservice.model.RequirementPriority;
import com.m2z.tools.requirementservice.repository.RequirementPriorityRepository;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.stereotype.Service;

@Service
public class RequirementPriorityService
        extends BaseService<RequirementPriority, RequirementPriorityDTO, Long> {
    private final RequirementPriorityRepository repository;
    private final RequirementPriorityMapper mapper;

    public RequirementPriorityService(
            RequirementPriorityRepository repository, RequirementPriorityMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
