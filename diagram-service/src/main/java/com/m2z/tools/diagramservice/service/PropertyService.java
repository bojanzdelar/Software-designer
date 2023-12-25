package com.m2z.tools.diagramservice.service;

import static com.m2z.tools.security.util.SecurityUtil.getProjects;

import com.m2z.tools.diagramservice.dto.PropertyDTO;
import com.m2z.tools.diagramservice.mapper.PropertyMapper;
import com.m2z.tools.diagramservice.model.Class;
import com.m2z.tools.diagramservice.model.Property;
import com.m2z.tools.diagramservice.repository.ClassRepository;
import com.m2z.tools.diagramservice.repository.PropertyRepository;
import com.m2z.tools.shared.exception.ForbiddenException;
import com.m2z.tools.shared.service.BaseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PropertyService extends BaseService<Property, PropertyDTO, Long> {
    private final PropertyRepository repository;
    private final PropertyMapper mapper;

    private final ClassRepository classRepository;

    public PropertyService(
            PropertyRepository repository, PropertyMapper mapper, ClassRepository classRepository) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.classRepository = classRepository;
    }

    public Page<PropertyDTO> findAllByProject(Pageable pageable, String search, String projectId) {
        HashMap<String, String> projects = getProjects();
        if (!projects.containsKey(projectId)) {
            throw new ForbiddenException("Not allowed to access this project's properties");
        }
        return repository
                .findContainingByProject(pageable, "%" + search + "%", projectId)
                .map(mapper::toDTO);
    }

    public Page<PropertyDTO> findAllByClass(Pageable pageable, String search, String classId) {
        HashMap<String, String> projects = getProjects();
        Class class_ = classRepository.findById(classId).orElse(null);
        if (class_ == null || !projects.containsKey(class_.getDiagram().getProject())) {
            throw new ForbiddenException("Not allowed to access this class's properties");
        }
        return repository
                .findContainingByClass(pageable, "%" + search + "%", classId)
                .map(mapper::toDTO);
    }
}
