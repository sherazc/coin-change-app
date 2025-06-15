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
public class CoinExchange {

  private final BillValidator billValidator;

  private final CoinRepository coinRepository;

  public Map<CoinType, Integer> getCoinBalance() {
    return coinRepository.getAll();
  }

  public Map<CoinType, Integer> makeChange(Integer bill) {
    billValidator.validateBill(bill);

    Map<CoinType, Integer> change = new HashMap<>();
    int billCents = bill * 100;

    int balanceCents = addCoins(CoinType.QUARTER, billCents, change);

    if (balanceCents > 0) {
      balanceCents = addCoins(CoinType.DIME, balanceCents, change);
    }

    if (balanceCents > 0) {
      balanceCents = addCoins(CoinType.NICKEL, balanceCents, change);
    }

    if (balanceCents > 0) {
      addCoins(CoinType.PENNY, balanceCents, change);
    }

    return change;
  }




  private int addCoins(CoinType coinType, int balanceCents, Map<CoinType, Integer> change) {
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



  public int convertCentsToCoins(CoinType type, int cents) {
    return cents / type.getCents();
  }
}
