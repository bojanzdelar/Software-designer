package com.m2z.tools.diagramservice.dto;

import com.m2z.tools.shared.dto.BaseDTO;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RelationshipDTO extends BaseDTO<Long> {
    @NotNull(message = "Relationship type is mandatory")
    private RelationshipTypeDTO relationshipType;

    @NotNull(message = "Parent class is mandatory")
    private ClassDTO parentClass;

    @NotNull(message = "Child class is mandatory")
    private ClassDTO childClass;
}
