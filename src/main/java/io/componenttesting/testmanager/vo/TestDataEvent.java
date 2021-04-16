package io.componenttesting.testmanager.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TestDataEvent {
    private String teamName;
    private String happening;
    private BigDecimal fun;
    private BigDecimal learned;
}
