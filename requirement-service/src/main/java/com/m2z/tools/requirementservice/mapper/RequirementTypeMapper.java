package com.m2z.tools.requirementservice.mapper;

import com.m2z.tools.requirementservice.dto.RequirementTypeDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.requirementservice.model.RequirementType;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequirementTypeMapper
        extends BaseMapper<RequirementType, RequirementTypeDTO, Long> {}
