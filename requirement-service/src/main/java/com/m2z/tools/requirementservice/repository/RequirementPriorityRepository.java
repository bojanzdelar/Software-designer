package com.m2z.tools.requirementservice.repository;

import com.m2z.tools.requirementservice.model.RequirementPriority;
import com.m2z.tools.shared.repository.BaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementPriorityRepository extends BaseRepository<RequirementPriority, Long> {
    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search "
                    + "or x.title like :search)")
    Page<RequirementPriority> findContaining(Pageable pageable, String search);
}
