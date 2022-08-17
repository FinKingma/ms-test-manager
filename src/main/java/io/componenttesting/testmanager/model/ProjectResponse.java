package io.componenttesting.testmanager.model;

import lombok.Data;

import java.util.List;

@Data
public class ProjectResponse {

    private String name;

    private List<TestData> testdata;
}
