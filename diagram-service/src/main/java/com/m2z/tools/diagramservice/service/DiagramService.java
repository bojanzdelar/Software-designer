package com.m2z.tools.diagramservice.service;

import static com.m2z.tools.security.util.SecurityUtil.*;

import com.m2z.tools.diagramservice.dto.DiagramDTO;
import com.m2z.tools.diagramservice.mapper.DiagramMapper;
import com.m2z.tools.diagramservice.model.Diagram;
import com.m2z.tools.diagramservice.repository.DiagramRepository;
import com.m2z.tools.shared.exception.ForbiddenException;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class DiagramService extends BaseService<Diagram, DiagramDTO, Long> {
    private final DiagramRepository repository;
    private final DiagramMapper mapper;

    public DiagramService(DiagramRepository repository, DiagramMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<DiagramDTO> findById(Set<Long> longs) {
        HashMap<String, String> projects = getProjects();
        List<DiagramDTO> diagrams = super.findById(longs);
        if (diagrams.stream().anyMatch((diagram) -> !projects.containsKey(diagram.getProject()))) {
            throw new ForbiddenException("Not allowed to access this project");
        }
        return diagrams;
    }

    @Override
    public DiagramDTO save(DiagramDTO DTO) {
        HashMap<String, String> projects = getProjects();
        String project = DTO.getProject();
        if (!projects.containsKey(project) || !projects.get(project).equals(WRITE)) {
            throw new ForbiddenException("Not allowed to edit this project");
        }
        return super.save(DTO);
    }

    @Override
    public void delete(Set<Long> longs) {
        List<DiagramDTO> diagrams = super.findById(longs);
        HashMap<String, String> projects = getProjects();
        if (diagrams.stream()
                .anyMatch((diagram) -> !projects.get(diagram.getProject()).equals(WRITE))) {
            throw new ForbiddenException("Not allowed to edit this project");
        }

        super.delete(longs);
    }

    public Page<DiagramDTO> findAllByProject(Pageable pageable, String search, String project) {
        HashMap<String, String> projects = getProjects();
        if (!projects.containsKey(project)) {
            throw new ForbiddenException("Not allowed to access this project");
        }

        return repository
                .findContainingByProject(pageable, "%" + search + "%", project)
                .map(mapper::toDTO);
    }
}
