package io.componenttesting.teammorale.model;

import javax.persistence.*;

@Entity(name = "Teams")
public class TeamsEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator="teams_seq")
    @SequenceGenerator(sequenceName = "teams_seq", allocationSize = 1, name = "teams_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String vision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }
}
