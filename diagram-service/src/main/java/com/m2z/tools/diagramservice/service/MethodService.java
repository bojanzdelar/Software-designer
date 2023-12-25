package com.m2z.tools.diagramservice.service;

import static com.m2z.tools.security.util.SecurityUtil.getProjects;

import com.m2z.tools.diagramservice.dto.MethodDTO;
import com.m2z.tools.diagramservice.mapper.MethodMapper;
import com.m2z.tools.diagramservice.model.Class;
import com.m2z.tools.diagramservice.model.Method;
import com.m2z.tools.diagramservice.repository.ClassRepository;
import com.m2z.tools.diagramservice.repository.MethodRepository;
import com.m2z.tools.shared.exception.ForbiddenException;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MethodService extends BaseService<Method, MethodDTO, Long> {
    private final MethodRepository repository;
    private final MethodMapper mapper;
    private final ClassRepository classRepository;

    public MethodService(
            MethodRepository repository, MethodMapper mapper, ClassRepository classRepository) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.classRepository = classRepository;
    }

    public Page<MethodDTO> findAllByProject(Pageable pageable, String search, String projectId) {
        HashMap<String, String> projects = getProjects();
        if (!projects.containsKey(projectId)) {
            throw new ForbiddenException("Not allowed to access this project's methods");
        }
        return repository
                .findContainingByProject(pageable, "%" + search + "%", projectId)
                .map(mapper::toDTO);
    }

    public Page<MethodDTO> findAllByClass(Pageable pageable, String search, String classId) {
        HashMap<String, String> projects = getProjects();
        Class class_ = classRepository.findById(classId).orElse(null);
        if (class_ == null || !projects.containsKey(class_.getDiagram().getProject())) {
            throw new ForbiddenException("Not allowed to access this class's methods");
        }
        return repository
                .findContainingByClass(pageable, "%" + search + "%", classId)
                .map(mapper::toDTO);
    }
}
