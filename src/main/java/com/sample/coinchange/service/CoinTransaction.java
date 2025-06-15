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

  public int withdrawCoinBalance(CoinType coinType, int balanceCents, Map<CoinType, Integer> coinBag) {
    int availableCoins = coinRepository.getByType(coinType);

    // Forward balance if none available
    if (availableCoins < 1) {
      return balanceCents;
    }

    // Find coin change
    int neededCoins = coinCalculator.centsToCoins(coinType, balanceCents);
    int coinCount = Math.min(availableCoins, neededCoins);

    // Remove coins repo
    debitCoins(coinType, coinCount);

    // Add coins in response
    coinBag.put(coinType, coinCount);

    // Calculate balance forward
    return balanceCents - coinCalculator.coinsToCents(coinType, coinCount);
  }

  private void debitCoins(CoinType coinType, int coinCount) {
    coinRepository.update(coinType, coinRepository.getByType(coinType) - coinCount);
  }
}
