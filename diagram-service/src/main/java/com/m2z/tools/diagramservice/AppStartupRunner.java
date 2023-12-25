package com.m2z.tools.diagramservice;

import com.m2z.tools.diagramservice.dto.AccessTypeDTO;
import com.m2z.tools.diagramservice.dto.ClassTypeDTO;
import com.m2z.tools.diagramservice.dto.RelationshipTypeDTO;
import com.m2z.tools.diagramservice.service.AccessTypeService;
import com.m2z.tools.diagramservice.service.ClassTypeService;
import com.m2z.tools.diagramservice.service.RelationshipTypeService;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {
    private final ClassTypeService classTypeService;
    private final AccessTypeService accessTypeService;
    private final RelationshipTypeService relationshipTypeService;

    private final List<String> classTypes = List.of("class", "abstract", "interface");
    private final List<String> accessTypes = List.of("private", "protected", "public");
    private final List<String> relationshipTypes =
            List.of("implements", "extends", "association", "aggregation", "composition", "inner");

    @Override
    public void run(ApplicationArguments args) {
        if (classTypeService.findAll().isEmpty()) {
            classTypes.forEach(type -> classTypeService.forceSave(new ClassTypeDTO(type)));
        }

        if (accessTypeService.findAll().isEmpty()) {
            accessTypes.forEach(type -> accessTypeService.forceSave(new AccessTypeDTO(type)));
        }

        if (relationshipTypeService.findAll().isEmpty()) {
            relationshipTypes.forEach(
                    type -> relationshipTypeService.forceSave(new RelationshipTypeDTO(type)));
        }
    }
}
