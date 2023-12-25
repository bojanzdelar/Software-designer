package com.m2z.tools.diagramservice.repository;

import com.m2z.tools.diagramservice.model.ClassType;
import com.m2z.tools.shared.repository.BaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTypeRepository extends BaseRepository<ClassType, Long> {
    @Query(
            value =
                    "select x from #{#entityName} x where x.deleted = false "
                            + "and (cast(x.id as string) like :search or x.name like :search)")
    Page<ClassType> findContaining(Pageable pageable, @Param("search") String search);
}
