package io.componenttesting.testmanager.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Project {
    @NotNull
    private String name;
}
