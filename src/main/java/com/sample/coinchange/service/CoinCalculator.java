package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CoinCalculator {

  public double total(Map<CoinType, Integer> coins) {
    return coins.keySet().stream()
        .filter(ct -> coins.get(ct) != null)
        .map(ct -> coins.get(ct) * ct.getAmount().doubleValue())
        .reduce(0d, Double::sum);
  }

  public int breakBillIntoCoins(CoinType type, Integer bill) {
    return bill * 100 / type.getCents();
  }
}
