package io.componenttesting.testmanager.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

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
    private Integer testrunId;

    @Column(nullable = false)
    private String testname;

    @Column(nullable = false)
    private String result;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestDataEntity that = (TestDataEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(testrunId, that.testrunId) && Objects.equals(testname, that.testname) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, testrunId, testname, result);
    }
}
