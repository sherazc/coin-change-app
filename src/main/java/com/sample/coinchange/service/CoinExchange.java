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

    Map<CoinType, Integer> change = new HashMap<>();
    int billCents = bill * 100;

    int balanceCents = coinTransaction.withdrawCoinBalance(CoinType.QUARTER, billCents, change);

    if (balanceCents > 0) {
      balanceCents = coinTransaction.withdrawCoinBalance(CoinType.DIME, balanceCents, change);
    }

    if (balanceCents > 0) {
      balanceCents = coinTransaction.withdrawCoinBalance(CoinType.NICKEL, balanceCents, change);
    }

    if (balanceCents > 0) {
      coinTransaction.withdrawCoinBalance(CoinType.PENNY, balanceCents, change);
    }

    return change;
  }
}
