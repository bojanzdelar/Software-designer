package com.m2z.tools.requirementservice.mapper;

import com.m2z.tools.requirementservice.dto.RequirementStatusDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.requirementservice.model.RequirementStatus;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequirementStatusMapper
        extends BaseMapper<RequirementStatus, RequirementStatusDTO, Long> {}
