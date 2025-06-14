package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.exception.InsufficientFundsException;
import com.sample.coinchange.exception.InvalidBillException;
import com.sample.coinchange.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoinBankService {

  private static final List<Integer> ALLOWED_BILLS = List.of(1, 2, 5, 10, 20, 50, 100);

  private final CoinRepository coinRepository;
  private final CoinCalculator coinCalculator;

  public Map<CoinType, Integer> getCoinBalance() {
    return coinRepository.getAll();
  }

  public Map<CoinType, Integer> makeChange(Integer bill) {
    if (!ALLOWED_BILLS.contains(bill)) {
      throw new InvalidBillException();
    }

    Map<CoinType, Integer> allCoins = coinRepository.getAll();

    double availableFunds = coinCalculator.total(allCoins);

    if (availableFunds < bill) {
      throw new InsufficientFundsException();
    }

    Map<CoinType, Integer> change = new HashMap<>();
    int billCents = bill * 100;

    int balanceCents = addCoins(CoinType.QUARTER, billCents, change);

    if (balanceCents > 0) {
      balanceCents = addCoins(CoinType.DIME, billCents, change);
    }

    if (balanceCents > 0) {
      balanceCents = addCoins(CoinType.NICKEL, billCents, change);
    }

    if (balanceCents > 0) {
      addCoins(CoinType.PENNY, billCents, change);
    }

    return change;
  }

  private int addCoins(CoinType coinType, int balanceCents, Map<CoinType, Integer> change) {
    int availableCount = coinRepository.getByType(coinType);
    if (availableCount < 0) {
      return balanceCents;
    }
    int neededCount = coinCalculator.convertCentsToCoins(coinType, balanceCents);

    int coinCount = Math.min(availableCount, neededCount);

    debitCoins(coinType, coinCount);

    change.put(coinType, coinCount);

    return balanceCents - (coinCount * coinType.getCents());
  }

  private void debitCoins(CoinType coinType, int coinCount) {
    coinRepository.update(coinType, coinRepository.getByType(coinType) - coinCount);
  }
}
