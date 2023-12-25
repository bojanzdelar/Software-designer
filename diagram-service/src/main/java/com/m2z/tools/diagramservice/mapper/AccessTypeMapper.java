package com.m2z.tools.diagramservice.mapper;

import com.m2z.tools.diagramservice.dto.AccessTypeDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.diagramservice.model.AccessType;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccessTypeMapper extends BaseMapper<AccessType, AccessTypeDTO, Long> {}
