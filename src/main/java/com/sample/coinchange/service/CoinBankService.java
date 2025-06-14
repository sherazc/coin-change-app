package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.exception.InsufficientFundsException;
import com.sample.coinchange.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    return Map.of(
        CoinType.QUARTER, 2,
        CoinType.DIME, 5,
        CoinType.NICKEL, 20
    );
  }


}
