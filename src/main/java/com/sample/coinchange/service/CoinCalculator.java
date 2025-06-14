package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CoinCalculator {
  public double total(Map<CoinType, Integer> coins) {
    double total = 0;
    if (coins.get(CoinType.PENNY) != null) {
      total += coins.get(CoinType.PENNY) * .01;
    }

    if (coins.get(CoinType.NICKEL) != null) {
      total += coins.get(CoinType.NICKEL) * .05;
    }

    if (coins.get(CoinType.DIME) != null) {
      total += coins.get(CoinType.DIME) * .1;
    }

    if (coins.get(CoinType.QUARTER) != null) {
      total += coins.get(CoinType.QUARTER) * .25;
    }

    return total;
  }
}
