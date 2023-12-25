package com.m2z.tools.diagramservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassDTO {
    @NotBlank(message = "ID is mandatory")
    private String id;

    @NotNull(message = "Class type is mandatory")
    private ClassTypeDTO classType;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Inner class is mandatory")
    private boolean innerClass;

    @NotNull(message = "Diagram is mandatory")
    private DiagramDTO diagram;
}
