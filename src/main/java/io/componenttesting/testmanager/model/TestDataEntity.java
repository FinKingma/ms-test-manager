package io.componenttesting.testmanager.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Testdata")
@Table(name = "testdata")
public class TestDataEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator="testdata_seq")
    @SequenceGenerator(sequenceName = "testdata_seq", allocationSize = 1, name = "testdata_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectEntity project;

    @Column(nullable = false)
    private Long testrunId;

    @Column(nullable = false)
    private String testname;

    @Column(nullable = false)
    private String result;
}
