package io.componenttesting.teammorale.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TeamEvent {
    private String teamName;
    private String happening;
    private BigDecimal fun;
    private BigDecimal learned;
}
