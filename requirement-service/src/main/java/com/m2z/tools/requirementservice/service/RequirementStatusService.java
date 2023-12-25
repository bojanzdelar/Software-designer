package com.m2z.tools.requirementservice.service;

import com.m2z.tools.requirementservice.dto.RequirementStatusDTO;
import com.m2z.tools.requirementservice.mapper.RequirementStatusMapper;
import com.m2z.tools.requirementservice.model.RequirementStatus;
import com.m2z.tools.requirementservice.repository.RequirementStatusRepository;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.stereotype.Service;

@Service
public class RequirementStatusService
        extends BaseService<RequirementStatus, RequirementStatusDTO, Long> {
    private final RequirementStatusRepository repository;
    private final RequirementStatusMapper mapper;

    public RequirementStatusService(
            RequirementStatusRepository repository, RequirementStatusMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
