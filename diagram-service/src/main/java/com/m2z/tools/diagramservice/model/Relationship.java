package com.m2z.tools.diagramservice.model;

import com.m2z.tools.shared.model.BaseEntity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Relationship extends BaseEntity<Long> {
    @ManyToOne(optional = false)
    private RelationshipType relationshipType;

    @ManyToOne(optional = false)
    private Class parentClass;

    @ManyToOne(optional = false)
    private Class childClass;
}
