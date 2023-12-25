package com.m2z.tools.requirementservice.repository;

import com.m2z.tools.requirementservice.model.Requirement;
import com.m2z.tools.shared.repository.BaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementRepository extends BaseRepository<Requirement, Long> {
    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search "
                    + "or x.title like :search or x.description like :search "
                    + "or x.stakeholders like :search or x.effortAssessment like :search)")
    Page<Requirement> findContaining(Pageable pageable, String search);

    @Query(
            "select x from #{#entityName} x where x.deleted = false and x.project = :project "
                    + "and (cast(x.id as string) like :search "
                    + "or x.title like :search or x.description like :search "
                    + "or x.stakeholders like :search or x.effortAssessment like :search)")
    Page<Requirement> findContainingByProject(Pageable pageable, String search, String project);
}
