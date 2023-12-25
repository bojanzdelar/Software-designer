package com.m2z.tools.requirementservice.mapper;

import com.m2z.tools.requirementservice.dto.RequirementPriorityDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.requirementservice.model.RequirementPriority;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequirementPriorityMapper
        extends BaseMapper<RequirementPriority, RequirementPriorityDTO, Long> {}
