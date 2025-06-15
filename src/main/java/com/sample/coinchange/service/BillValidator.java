package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.exception.InsufficientFundsException;
import com.sample.coinchange.exception.InvalidBillException;
import com.sample.coinchange.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BillValidator {
  private static final List<Integer> ALLOWED_BILLS = List.of(1, 2, 5, 10, 20, 50, 100);

  private final CoinRepository coinRepository;

  public void validateBill(Integer bill) {
    if (!ALLOWED_BILLS.contains(bill)) {
      throw new InvalidBillException(ALLOWED_BILLS);
    }

    Map<CoinType, Integer> allCoins = coinRepository.getAll();

    double availableFunds = total(allCoins);

    if (availableFunds < bill) {
      throw new InsufficientFundsException(bill, availableFunds);
    }
  }

  private double total(Map<CoinType, Integer> coins) {
    return coins.keySet().stream()
        .filter(ct -> coins.get(ct) != null)
        .map(ct -> coins.get(ct) * ct.getAmount().doubleValue())
        .reduce(0d, Double::sum);
  }
}
