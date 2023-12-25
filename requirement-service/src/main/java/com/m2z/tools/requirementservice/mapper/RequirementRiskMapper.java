package com.m2z.tools.requirementservice.mapper;

import com.m2z.tools.requirementservice.dto.RequirementRiskDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.requirementservice.model.RequirementRisk;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequirementRiskMapper
        extends BaseMapper<RequirementRisk, RequirementRiskDTO, Long> {}
