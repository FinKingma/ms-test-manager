package io.componenttesting.testmanager.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Project {
    @NotNull
    private String name;
}
