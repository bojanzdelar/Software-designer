package com.m2z.tools.diagramservice.dto;

import com.m2z.tools.shared.dto.BaseDTO;

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
public class MethodDTO extends BaseDTO<Long> {
    @NotNull(message = "Access type is mandatory")
    private AccessTypeDTO accessType;

    @NotNull(message = "Abstract is mandatory")
    private boolean abstract_;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Return type is mandatory")
    private String returnType;

    @NotNull(message = "Class is mandatory")
    private ClassDTO class_;
}
