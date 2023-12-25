package com.m2z.tools.diagramservice.mapper;

import com.m2z.tools.diagramservice.dto.ClassDTO;
import com.m2z.tools.diagramservice.dto.RelationshipDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.diagramservice.model.Class;
import com.m2z.tools.diagramservice.model.Relationship;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RelationshipMapper extends BaseMapper<Relationship, RelationshipDTO, Long> {
    @Mapping(target = "diagram", ignore = true)
    ClassDTO toDTO(Class model);
}
