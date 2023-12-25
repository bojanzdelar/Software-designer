package com.m2z.tools.diagramservice.service;

import com.m2z.tools.diagramservice.dto.RelationshipTypeDTO;
import com.m2z.tools.diagramservice.mapper.RelationshipTypeMapper;
import com.m2z.tools.diagramservice.model.RelationshipType;
import com.m2z.tools.diagramservice.repository.RelationshipTypeRepository;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.stereotype.Service;

@Service
public class RelationshipTypeService
        extends BaseService<RelationshipType, RelationshipTypeDTO, Long> {
    private final RelationshipTypeRepository repository;
    private final RelationshipTypeMapper mapper;

    public RelationshipTypeService(
            RelationshipTypeRepository repository, RelationshipTypeMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
