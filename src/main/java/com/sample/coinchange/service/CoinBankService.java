package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CoinBankService {
  public Map<CoinType, Integer> makeChange(Integer bill) {


    return Map.of(
        CoinType.QUARTER, 2,
        CoinType.DIME, 5,
        CoinType.NICKEL, 20
    );
  }
}
