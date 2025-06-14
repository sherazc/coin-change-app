package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.exception.InsufficientFundsException;
import com.sample.coinchange.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoinBankService {
  private final CoinRepository coinRepository;
  private final CoinCalculator coinCalculator;

  public Map<CoinType, Integer> makeChange(Integer bill) {

    Map<CoinType, Integer> allCoins = coinRepository.getAll();

    double availableFunds = coinCalculator.total(allCoins);

    if (availableFunds < bill) {
      throw new InsufficientFundsException("Insufficient Funds");
    }

    Map<CoinType, Integer> change =  new HashMap<>();

    double remaining = addQuarter(bill, change);

    return change;
  }

  private double addQuarter(Integer bill, Map<CoinType, Integer> change) {
    int availableCount = coinRepository.getByType(CoinType.QUARTER);
    int neededCount = coinCalculator.breakBillIntoCoins(CoinType.QUARTER, bill);

    int coinCount;
    if (availableCount < neededCount) {

    }




    return 0;
  }


}
