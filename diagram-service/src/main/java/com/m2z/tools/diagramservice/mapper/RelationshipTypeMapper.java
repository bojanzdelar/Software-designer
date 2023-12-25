package com.m2z.tools.diagramservice.mapper;

import com.m2z.tools.diagramservice.dto.RelationshipTypeDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.diagramservice.model.RelationshipType;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RelationshipTypeMapper
        extends BaseMapper<RelationshipType, RelationshipTypeDTO, Long> {}
