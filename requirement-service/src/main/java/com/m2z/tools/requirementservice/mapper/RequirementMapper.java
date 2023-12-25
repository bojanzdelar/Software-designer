package com.m2z.tools.requirementservice.mapper;

import com.m2z.tools.requirementservice.dto.RequirementDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.requirementservice.model.Requirement;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequirementMapper extends BaseMapper<Requirement, RequirementDTO, Long> {}
