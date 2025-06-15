package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CoinCalculator {

  public int coinsToCents(CoinType coinType, int coins) {
    return coins * coinType.getCents();
  }

  public int centsToCoins(CoinType type, int cents) {
    return cents / type.getCents();
  }

  public double total(Map<CoinType, Integer> coins) {
    return coins.keySet().stream()
        .filter(ct -> coins.get(ct) != null)
        .map(ct -> coins.get(ct) * ct.getAmount().doubleValue())
        .reduce(0d, Double::sum);
  }
}
