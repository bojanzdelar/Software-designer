package com.m2z.tools.diagramservice.repository;

import com.m2z.tools.diagramservice.model.Class;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClassRepository extends CrudRepository<Class, String> {
    @Override
    @Query("select x from #{#entityName} x where x.deleted = false")
    Iterable<Class> findAll();

    @Query("select x from #{#entityName} x where x.deleted = true")
    Iterable<Class> findAllDeleted();

    @Query("select x from #{#entityName} x")
    Iterable<Class> findAllWithSoftDeleted();

    @Query("update #{#entityName} x set x.deleted = true where x.id = :id")
    @Modifying
    void softDeleteById(String id);

    @Query("update #{#entityName} x set x.deleted = true where x.id in :ids")
    @Modifying
    void softDeleteByIds(Set<String> ids);

    @Query("update #{#entityName} x set x.deleted = false where x.id in :id")
    @Modifying
    void restoreById(String id);

    @Query("update #{#entityName} x set x.deleted = false where x.id in :ids")
    @Modifying
    void restoreByIds(Set<String> ids);

    @Query(
            value =
                    "select x from #{#entityName} x where x.deleted = false and "
                            + "(x.id like :search or x.name like :search)")
    Page<Class> findContaining(Pageable pageable, @Param("search") String search);

    @Query(
            value =
                    "select x from #{#entityName} x where x.deleted = false and x.diagram.project = :project and "
                            + "(x.id like :search or x.name like :search)")
    Page<Class> findContainingByProject(
            Pageable pageable, @Param("search") String search, @Param("project") String project);
}
