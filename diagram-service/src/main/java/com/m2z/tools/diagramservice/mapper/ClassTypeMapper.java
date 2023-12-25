package com.m2z.tools.diagramservice.mapper;

import com.m2z.tools.diagramservice.dto.ClassTypeDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.diagramservice.model.ClassType;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassTypeMapper extends BaseMapper<ClassType, ClassTypeDTO, Long> {}
