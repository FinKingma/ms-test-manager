package io.componenttesting.testmanager.dao;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Entity(name = "Project")
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator="project_seq")
    @SequenceGenerator(sequenceName = "project_seq", allocationSize = 1, name = "project_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private Set<TestDataEntity> testdata;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity that = (ProjectEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
