package com.m2z.tools.diagramservice.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Class {
    @Id protected String id;

    @Column(nullable = false, columnDefinition = "boolean default false")
    protected boolean deleted = false;

    @ManyToOne(optional = false)
    private ClassType classType;

    @Column(name = "name_", nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean innerClass;

    @ManyToOne(optional = false)
    private Diagram diagram;

    @OneToMany(mappedBy = "class_", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Property> properties = new ArrayList<>();

    @OneToMany(mappedBy = "class_", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Method> methods = new ArrayList<>();

    @OneToMany(mappedBy = "parentClass", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Relationship> parentRelationships = new ArrayList<>();

    @OneToMany(mappedBy = "childClass", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Relationship> childRelationships = new ArrayList<>();
}
