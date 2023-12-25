package com.m2z.tools.diagramservice.mapper;

import com.m2z.tools.diagramservice.dto.ClassDTO;
import com.m2z.tools.diagramservice.dto.MethodDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.diagramservice.model.Class;
import com.m2z.tools.diagramservice.model.Method;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MethodMapper extends BaseMapper<Method, MethodDTO, Long> {
    @Mapping(target = "diagram", ignore = true)
    ClassDTO toDTO(Class model);
}
