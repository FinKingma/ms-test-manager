package io.componenttesting.teammorale.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class Team {
    @NotNull
    private String teamName;

    @NotNull
    private String vision;

    private BigDecimal happiness;

    private BigDecimal morale;
}
