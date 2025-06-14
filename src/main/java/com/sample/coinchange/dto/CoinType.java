package com.sample.coinchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum CoinType {
    PENNY(new BigDecimal("0.01"), 1),
    NICKEL(new BigDecimal("0.05"), 5),
    DIME(new BigDecimal("0.10"), 10),
    QUARTER(new BigDecimal("0.25"), 25)
    ;

    @JsonValue
    private final BigDecimal amount;

    @JsonIgnore
    private final int cents;
}
