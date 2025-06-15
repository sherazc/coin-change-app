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

  public int withdrawCoinBalance(CoinType coinType, int balanceCents, Map<CoinType, Integer> change) {
    int availableCount = coinRepository.getByType(coinType);
    if (availableCount < 1) {
      return balanceCents;
    }
    int neededCount = convertCentsToCoins(coinType, balanceCents);

    int coinCount = Math.min(availableCount, neededCount);

    debitCoins(coinType, coinCount);

    change.put(coinType, coinCount);

    return balanceCents - (coinCount * coinType.getCents());
  }

  private void debitCoins(CoinType coinType, int coinCount) {
    coinRepository.update(coinType, coinRepository.getByType(coinType) - coinCount);
  }

  private int convertCentsToCoins(CoinType type, int cents) {
    return cents / type.getCents();
  }
}
