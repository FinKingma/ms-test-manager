package io.componenttesting.teammorale.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
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

    @Column(nullable = false)
    private BigDecimal morale;

    @Column(nullable = false)
    private BigDecimal happiness;

    @Column(nullable = false)
    private Integer numberOfUpdates;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedOn;
}
