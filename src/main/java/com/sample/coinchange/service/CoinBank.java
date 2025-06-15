package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoinBank {

  private final CoinRepository coinRepository;

  public Map<CoinType, Integer> checkBalance() {
    return coinRepository.getAll();
  }
}
