package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoinCalculatorTest {

  private final CoinCalculator underTest = new CoinCalculator();

  @Test
  void total() {
    assertEquals(1, underTest.total(Map.of(CoinType.QUARTER, 4)));
    assertEquals(0, underTest.total(Map.of()));
    assertEquals(.31, underTest.total(Map.of(
        CoinType.QUARTER, 1,
        CoinType.NICKEL, 1,
        CoinType.PENNY, 1)));

    assertEquals(41, underTest.total(Map.of(
        CoinType.QUARTER, 100,
        CoinType.NICKEL, 100,
        CoinType.DIME, 100,
        CoinType.PENNY, 100)));
  }
}