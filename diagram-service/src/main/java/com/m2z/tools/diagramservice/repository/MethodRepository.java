package com.m2z.tools.diagramservice.repository;

import com.m2z.tools.diagramservice.model.Method;
import com.m2z.tools.shared.repository.BaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodRepository extends BaseRepository<Method, Long> {
    @Query(
            value =
                    "select x from #{#entityName} x where x.deleted = false "
                            + "and (cast(x.id as string) like :search or x.name like :search or x.returnType like :search)")
    Page<Method> findContaining(Pageable pageable, @Param("search") String search);

    @Query(
            value =
                    "select x from #{#entityName} x where x.deleted = false and x.class_.diagram.project = :projectId "
                            + "and (cast(x.id as string) like :search or x.name like :search or x.returnType like :search)")
    Page<Method> findContainingByProject(
            Pageable pageable,
            @Param("search") String search,
            @Param("projectId") String projectId);

    @Query(
            value =
                    "select x from #{#entityName} x where x.deleted = false and x.class_.id = :classId "
                            + "and (cast(x.id as string) like :search or x.name like :search or x.returnType like :search)")
    Page<Method> findContainingByClass(
            Pageable pageable, @Param("search") String search, @Param("classId") String classId);
}
