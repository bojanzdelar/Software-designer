package com.m2z.tools.diagramservice.service;

import com.m2z.tools.diagramservice.dto.AccessTypeDTO;
import com.m2z.tools.diagramservice.mapper.AccessTypeMapper;
import com.m2z.tools.diagramservice.model.AccessType;
import com.m2z.tools.diagramservice.repository.AccessTypeRepository;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.stereotype.Service;

@Service
public class AccessTypeService extends BaseService<AccessType, AccessTypeDTO, Long> {
    private final AccessTypeRepository repository;
    private final AccessTypeMapper mapper;

    public AccessTypeService(AccessTypeRepository repository, AccessTypeMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
