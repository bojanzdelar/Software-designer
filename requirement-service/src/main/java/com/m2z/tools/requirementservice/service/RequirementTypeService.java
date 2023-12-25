package com.m2z.tools.requirementservice.service;

import com.m2z.tools.requirementservice.dto.RequirementTypeDTO;
import com.m2z.tools.requirementservice.mapper.RequirementTypeMapper;
import com.m2z.tools.requirementservice.model.RequirementType;
import com.m2z.tools.requirementservice.repository.RequirementTypeRepository;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.stereotype.Service;

@Service
public class RequirementTypeService extends BaseService<RequirementType, RequirementTypeDTO, Long> {
    private final RequirementTypeRepository repository;
    private final RequirementTypeMapper mapper;

    public RequirementTypeService(
            RequirementTypeRepository repository, RequirementTypeMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
