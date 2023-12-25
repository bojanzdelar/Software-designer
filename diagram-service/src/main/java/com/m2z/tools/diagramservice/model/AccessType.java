package com.m2z.tools.diagramservice.model;

import com.m2z.tools.shared.model.BaseEntity;

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
public class AccessType extends BaseEntity<Long> {
    @Column(name = "name_", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "accessType", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Property> properties = new ArrayList<>();

    @OneToMany(mappedBy = "accessType", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Method> methods = new ArrayList<>();
}
