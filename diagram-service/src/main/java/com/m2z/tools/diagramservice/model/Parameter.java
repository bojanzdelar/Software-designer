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
public class Parameter extends BaseEntity<Long> {
    @Column(name = "name_", nullable = false)
    private String name;

    @Column(name = "type_", nullable = false)
    private String type;

    @ManyToOne(optional = false)
    private Method method;
}
