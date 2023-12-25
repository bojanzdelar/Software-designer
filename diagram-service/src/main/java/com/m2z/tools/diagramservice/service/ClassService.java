package com.m2z.tools.diagramservice.service;

import static com.m2z.tools.security.util.SecurityUtil.getProjects;

import com.m2z.tools.diagramservice.dto.ClassDTO;
import com.m2z.tools.diagramservice.mapper.ClassMapper;
import com.m2z.tools.diagramservice.model.Class;
import com.m2z.tools.diagramservice.repository.ClassRepository;
import com.m2z.tools.shared.exception.ForbiddenException;
import com.m2z.tools.shared.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepository repository;
    private final ClassMapper mapper;

    public List<ClassDTO> findAll() {
        return mapper.toDTO((List<Class>) repository.findAll());
    }

    public Page<ClassDTO> findAll(Pageable pageable, String search) {
        return repository.findContaining(pageable, "%" + search + "%").map(mapper::toDTO);
    }

    public List<ClassDTO> findById(Set<String> ids) {
        boolean anyNotFound = ids.stream().anyMatch((id) -> !repository.existsById(id));
        if (anyNotFound) {
            throw new NotFoundException("ID not found");
        }
        return mapper.toDTO((List<Class>) repository.findAllById(ids));
    }

    public Page<ClassDTO> findAllByProject(Pageable pageable, String search, String projectId) {
        HashMap<String, String> projects = getProjects();
        if (!projects.containsKey(projectId)) {
            throw new ForbiddenException("Not allowed to access this project's classes");
        }
        return repository
                .findContainingByProject(pageable, "%" + search + "%", projectId)
                .map(mapper::toDTO);
    }

    @Transactional
    public ClassDTO save(ClassDTO DTO) {
        String id = DTO.getId();
        if (id != null && !repository.existsById(id)) {
            throw new NotFoundException("ID not found");
        }

        return this.forceSave(DTO);
    }

    @Transactional
    public ClassDTO forceSave(ClassDTO DTO) {
        Class model = mapper.toModel(DTO);
        return mapper.toDTO(repository.save(model));
    }

    @Transactional
    public void delete(Set<String> ids) {
        boolean anyNotFound = ids.stream().anyMatch((id) -> !repository.existsById(id));
        if (anyNotFound) {
            throw new NotFoundException("ID not found");
        }
        repository.softDeleteByIds(ids);
    }
}
