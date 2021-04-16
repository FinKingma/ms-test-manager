package io.componenttesting.testmanager.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
