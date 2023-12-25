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
public class Method extends BaseEntity<Long> {
    @ManyToOne(optional = false)
    private AccessType accessType;

    @Column(name = "abstract", nullable = false)
    private boolean abstract_;

    @Column(name = "name_", nullable = false)
    private String name;

    @Column(nullable = false)
    private String returnType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_id")
    private Class class_;

    @OneToMany(mappedBy = "method", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Parameter> parameters = new ArrayList<>();
}
