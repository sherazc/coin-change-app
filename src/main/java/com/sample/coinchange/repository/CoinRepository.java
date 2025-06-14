package com.sample.coinchange.repository;

import com.sample.coinchange.dto.CoinType;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CoinRepository {

  private static final Map<CoinType, Integer> COINS =  new ConcurrentHashMap<>(){{
    put(CoinType.PENNY, 100);
    put(CoinType.NICKEL, 100);
    put(CoinType.DIME, 100);
    put(CoinType.QUARTER, 100);
  }};

  public Integer getByType(CoinType coinType) {
    return COINS.get(coinType);
  }

  public Map<CoinType, Integer> getAll() {
    return COINS;
  }

  public void update(CoinType coinType, Integer amount) {
    COINS.put(coinType, amount);
  }
}
