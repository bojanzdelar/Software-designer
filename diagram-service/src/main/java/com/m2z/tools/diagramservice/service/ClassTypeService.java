package com.m2z.tools.diagramservice.service;

import com.m2z.tools.diagramservice.dto.ClassTypeDTO;
import com.m2z.tools.diagramservice.mapper.ClassTypeMapper;
import com.m2z.tools.diagramservice.model.ClassType;
import com.m2z.tools.diagramservice.repository.ClassTypeRepository;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.stereotype.Service;

@Service
public class ClassTypeService extends BaseService<ClassType, ClassTypeDTO, Long> {
    private final ClassTypeRepository repository;
    private final ClassTypeMapper mapper;

    public ClassTypeService(ClassTypeRepository repository, ClassTypeMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
