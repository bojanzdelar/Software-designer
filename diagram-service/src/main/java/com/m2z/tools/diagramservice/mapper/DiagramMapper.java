package com.m2z.tools.diagramservice.mapper;

import com.m2z.tools.diagramservice.dto.DiagramDTO;
import com.m2z.tools.shared.mapper.BaseMapper;
import com.m2z.tools.diagramservice.model.Diagram;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiagramMapper extends BaseMapper<Diagram, DiagramDTO, Long> {}
