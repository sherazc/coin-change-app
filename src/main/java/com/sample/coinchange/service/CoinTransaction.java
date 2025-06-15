package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class CoinTransaction {

  private final CoinRepository coinRepository;
  private final CoinCalculator coinCalculator;

  public int withdrawCoinBalance(CoinType coinType, int balanceCents, Map<CoinType, Integer> change) {
    int availableCount = coinRepository.getByType(coinType);
    if (availableCount < 1) {
      return balanceCents;
    }
    int neededCount = coinCalculator.centsToCoins(coinType, balanceCents);

    int coinCount = Math.min(availableCount, neededCount);

    // Remove coins count
    debitCoins(coinType, coinCount);

    // Add coin count
    change.put(coinType, coinCount);

    // Calculate balance
    return balanceCents - coinCalculator.coinsToCents(coinType, coinCount);
  }


  private void debitCoins(CoinType coinType, int coinCount) {
    coinRepository.update(coinType, coinRepository.getByType(coinType) - coinCount);
  }
}
