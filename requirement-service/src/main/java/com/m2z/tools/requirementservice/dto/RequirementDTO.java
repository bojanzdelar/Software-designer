package com.m2z.tools.requirementservice.dto;

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
public class RequirementDTO extends BaseDTO<Long> {
    @NotBlank(message = "Project is mandatory")
    private String project;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Type is mandatory")
    private RequirementTypeDTO type;

    @NotNull(message = "Priority is mandatory")
    private RequirementPriorityDTO priority;

    @NotBlank(message = "Stakeholders are mandatory")
    private String stakeholders;

    @NotNull(message = "Risk is mandatory")
    private RequirementRiskDTO risk;

    @NotNull(message = "Status is mandatory")
    private RequirementStatusDTO status;

    @NotNull(message = "Effort assessment is mandatory")
    private String effortAssessment;
}
