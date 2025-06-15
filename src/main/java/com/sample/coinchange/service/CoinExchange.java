package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoinExchange {

  private final BillValidator billValidator;
  private final CoinTransaction coinTransaction;

  public Map<CoinType, Integer> makeChange(Integer bill) {
    billValidator.validateBill(bill);

    Map<CoinType, Integer> coinBag = new HashMap<>();
    int billCents = bill * 100;

    int balanceCents = coinTransaction.withdrawCoinBalance(CoinType.QUARTER, billCents, coinBag);

    if (balanceCents > 0) {
      balanceCents = coinTransaction.withdrawCoinBalance(CoinType.DIME, balanceCents, coinBag);
    }

    if (balanceCents > 0) {
      balanceCents = coinTransaction.withdrawCoinBalance(CoinType.NICKEL, balanceCents, coinBag);
    }

    if (balanceCents > 0) {
      coinTransaction.withdrawCoinBalance(CoinType.PENNY, balanceCents, coinBag);
    }

    return coinBag;
  }
}
