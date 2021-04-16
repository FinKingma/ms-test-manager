package io.componenttesting.testmanager.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Testdata")
public class TestDataEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator="testdata_seq")
    @SequenceGenerator(sequenceName = "testdata_seq", allocationSize = 1, name = "testdata_seq")
    private Long id;

    @Column(nullable = false)
    private Long testRunId;

    @Column(nullable = false)
    private String testName;

    @Column(nullable = false)
    private String result;
}
