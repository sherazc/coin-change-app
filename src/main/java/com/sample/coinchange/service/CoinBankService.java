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

    int remainingCents = addCoins(billCents, change);

    return change;
  }

  private int addCoins(int cents, Map<CoinType, Integer> change) {
    int availableCount = coinRepository.getByType(CoinType.QUARTER);
    int neededCount = coinCalculator.convertCentsToCoins(CoinType.QUARTER, cents);

    int coinCount;
    if (availableCount < neededCount) {
      coinCount = availableCount;
    } else {
      coinCount = neededCount;
    }


    return 0;
  }


}
