package com.m2z.tools.diagramservice.dto;

import com.m2z.tools.shared.dto.BaseDTO;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccessTypeDTO extends BaseDTO<Long> {
    @NotBlank(message = "Name is mandatory")
    private String name;
}
