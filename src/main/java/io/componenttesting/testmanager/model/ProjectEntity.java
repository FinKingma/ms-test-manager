package io.componenttesting.testmanager.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "Project")
@Table(name = "project")
public class ProjectEntity {

    @Getter @Setter
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator="project_seq")
    @SequenceGenerator(sequenceName = "project_seq", allocationSize = 1, name = "project_seq")
    private Long id;

    @Getter @Setter
    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TestDataEntity> testdata;
}
