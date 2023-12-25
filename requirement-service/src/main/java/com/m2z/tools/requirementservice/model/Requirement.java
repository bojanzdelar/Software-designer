package com.m2z.tools.requirementservice.model;

import com.m2z.tools.shared.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Requirement extends BaseEntity<Long> {
    @Column(nullable = false)
    private String project;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @ManyToOne(optional = false)
    private RequirementType type;

    @ManyToOne(optional = false)
    private RequirementPriority priority;

    @Column(nullable = false)
    private String stakeholders;

    @ManyToOne(optional = false)
    private RequirementRisk risk;

    @ManyToOne(optional = false)
    private RequirementStatus status;

    @Column(nullable = false)
    private String effortAssessment;
}
