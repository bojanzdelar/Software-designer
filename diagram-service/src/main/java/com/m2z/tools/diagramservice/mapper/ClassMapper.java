package com.m2z.tools.diagramservice.mapper;

import com.m2z.tools.diagramservice.dto.ClassDTO;
import com.m2z.tools.diagramservice.dto.DiagramDTO;
import com.m2z.tools.diagramservice.model.Class;
import com.m2z.tools.diagramservice.model.Diagram;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassDTO toDTO(Class model);

    Class toModel(ClassDTO DTO);

    List<ClassDTO> toDTO(List<Class> model);

    List<Class> toModel(List<ClassDTO> DTO);

    @Mapping(target = "data", ignore = true)
    DiagramDTO toDTO(Diagram model);
}
