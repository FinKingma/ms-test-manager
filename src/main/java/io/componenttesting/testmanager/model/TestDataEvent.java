package io.componenttesting.testmanager.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TestDataEvent {
    @NotNull
    private String project;
    @NotNull
    private Integer testRunId;
    @NotNull
    private String testName;
    @NotNull
    private String result;
}
