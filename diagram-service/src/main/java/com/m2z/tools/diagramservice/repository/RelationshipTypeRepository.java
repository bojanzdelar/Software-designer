package com.m2z.tools.diagramservice.repository;

import com.m2z.tools.diagramservice.model.RelationshipType;
import com.m2z.tools.shared.repository.BaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipTypeRepository extends BaseRepository<RelationshipType, Long> {
    @Query(
            value =
                    "select x from #{#entityName} x where x.deleted = false "
                            + "and (cast(x.id as string) like :search or x.name like :search)")
    Page<RelationshipType> findContaining(Pageable pageable, @Param("search") String search);
}
