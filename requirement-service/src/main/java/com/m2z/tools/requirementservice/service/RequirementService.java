package com.m2z.tools.requirementservice.service;

import static com.m2z.tools.security.util.SecurityUtil.*;

import com.m2z.tools.requirementservice.dto.RequirementDTO;
import com.m2z.tools.requirementservice.mapper.RequirementMapper;
import com.m2z.tools.requirementservice.model.Requirement;
import com.m2z.tools.requirementservice.repository.RequirementRepository;
import com.m2z.tools.shared.exception.ForbiddenException;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class RequirementService extends BaseService<Requirement, RequirementDTO, Long> {
    private final RequirementRepository repository;
    private final RequirementMapper mapper;

    public RequirementService(RequirementRepository repository, RequirementMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<RequirementDTO> findById(Set<Long> longs) {
        HashMap<String, String> projects = getProjects();
        List<RequirementDTO> requirements = super.findById(longs);
        if (requirements.stream()
                .anyMatch((requirement) -> !projects.containsKey(requirement.getProject()))) {
            throw new ForbiddenException("Not allowed to access this project");
        }
        return requirements;
    }

    @Override
    public RequirementDTO save(RequirementDTO DTO) {
        HashMap<String, String> projects = getProjects();
        String project = DTO.getProject();
        if (!projects.containsKey(project) || !projects.get(project).equals(WRITE)) {
            throw new ForbiddenException("Not allowed to edit this project");
        }
        return super.save(DTO);
    }

    @Override
    public void delete(Set<Long> longs) {
        List<RequirementDTO> requirements = super.findById(longs);
        HashMap<String, String> projects = getProjects();
        if (requirements.stream()
                .anyMatch((requirement) -> !projects.get(requirement.getProject()).equals(WRITE))) {
            throw new ForbiddenException("Not allowed to edit this project");
        }

        super.delete(longs);
    }

    public Page<RequirementDTO> findAllByProject(Pageable pageable, String search, String project) {
        HashMap<String, String> projects = getProjects();
        if (!projects.containsKey(project)) {
            throw new ForbiddenException("Not allowed to access this project");
        }

        return repository
                .findContainingByProject(pageable, "%" + search + "%", project)
                .map(mapper::toDTO);
    }
}
