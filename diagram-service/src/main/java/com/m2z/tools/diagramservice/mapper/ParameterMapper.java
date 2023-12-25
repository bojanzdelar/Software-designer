package com.m2z.tools.diagramservice.mapper;

import com.m2z.tools.diagramservice.dto.MethodDTO;
import com.m2z.tools.diagramservice.dto.ParameterDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.diagramservice.model.Method;
import com.m2z.tools.diagramservice.model.Parameter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParameterMapper extends BaseMapper<Parameter, ParameterDTO, Long> {
    @Mapping(target = "class_", ignore = true)
    MethodDTO toDTO(Method model);
}
