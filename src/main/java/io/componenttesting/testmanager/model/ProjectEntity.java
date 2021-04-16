package io.componenttesting.testmanager.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "project")
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator="project_seq")
    @SequenceGenerator(sequenceName = "project_seq", allocationSize = 1, name = "project_seq")
    private Long id;

    @Column(nullable = false)
    private String name;
//
//    @OneToMany(
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    @JoinColumn(name = "project_id")
//    private List<TestDataEntity> testdata = new ArrayList<>();
}
