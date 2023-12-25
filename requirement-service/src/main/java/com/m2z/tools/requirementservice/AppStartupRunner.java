package com.m2z.tools.requirementservice;

import com.m2z.tools.requirementservice.dto.RequirementPriorityDTO;
import com.m2z.tools.requirementservice.dto.RequirementRiskDTO;
import com.m2z.tools.requirementservice.dto.RequirementStatusDTO;
import com.m2z.tools.requirementservice.dto.RequirementTypeDTO;
import com.m2z.tools.requirementservice.service.RequirementPriorityService;
import com.m2z.tools.requirementservice.service.RequirementRiskService;
import com.m2z.tools.requirementservice.service.RequirementStatusService;
import com.m2z.tools.requirementservice.service.RequirementTypeService;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {
    private final RequirementTypeService requirementTypeService;
    private final RequirementPriorityService requirementPriorityService;
    private final RequirementRiskService requirementRiskService;
    private final RequirementStatusService requirementStatusService;

    private final List<String> requirementTypes = List.of("Functional", "Nonfunctional");
    private final List<String> requirementPriorities =
            List.of("Essential", "Very desirable", "Desirable", "Optional", "Undesirable");
    private final List<String> requirementRisks = List.of("High", "Medium", "Low");
    private final List<String> requirementStatuses = List.of("Draft", "Defined", "Confirmed");

    @Override
    public void run(ApplicationArguments args) {
        if (requirementTypeService.findAll().isEmpty()) {
            requirementTypes.forEach(
                    type -> requirementTypeService.forceSave(new RequirementTypeDTO(type)));
        }

        if (requirementPriorityService.findAll().isEmpty()) {
            requirementPriorities.forEach(
                    priority ->
                            requirementPriorityService.forceSave(
                                    new RequirementPriorityDTO(priority)));
        }

        if (requirementRiskService.findAll().isEmpty()) {
            requirementRisks.forEach(
                    risk -> requirementRiskService.forceSave(new RequirementRiskDTO(risk)));
        }

        if (requirementStatusService.findAll().isEmpty()) {
            requirementStatuses.forEach(
                    status -> requirementStatusService.forceSave(new RequirementStatusDTO(status)));
        }
    }
}
