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

  @Test
  void centsToCoins() {
    assertEquals(4, underTest.centsToCoins(CoinType.QUARTER, 100));
    assertEquals(0, underTest.centsToCoins(CoinType.QUARTER, 0));
    assertEquals(0, underTest.centsToCoins(CoinType.QUARTER, 24));
    assertEquals(1, underTest.centsToCoins(CoinType.QUARTER, 25));
    assertEquals(1, underTest.centsToCoins(CoinType.QUARTER, 26));
    assertEquals(8, underTest.centsToCoins(CoinType.QUARTER, 200));

    assertEquals(10, underTest.centsToCoins(CoinType.DIME, 100));
    assertEquals(0, underTest.centsToCoins(CoinType.DIME, 0));
    assertEquals(0, underTest.centsToCoins(CoinType.DIME, 9));
    assertEquals(1, underTest.centsToCoins(CoinType.DIME, 10));
    assertEquals(1, underTest.centsToCoins(CoinType.DIME, 11));
    assertEquals(20, underTest.centsToCoins(CoinType.DIME, 200));

    assertEquals(20, underTest.centsToCoins(CoinType.NICKEL, 100));
    assertEquals(0, underTest.centsToCoins(CoinType.NICKEL, 0));
    assertEquals(0, underTest.centsToCoins(CoinType.NICKEL, 4));
    assertEquals(1, underTest.centsToCoins(CoinType.NICKEL, 5));
    assertEquals(1, underTest.centsToCoins(CoinType.NICKEL, 6));
    assertEquals(40, underTest.centsToCoins(CoinType.NICKEL, 200));

    assertEquals(100, underTest.centsToCoins(CoinType.PENNY, 100));
    assertEquals(0, underTest.centsToCoins(CoinType.PENNY, 0));
    assertEquals(1, underTest.centsToCoins(CoinType.PENNY, 1));
    assertEquals(200, underTest.centsToCoins(CoinType.PENNY, 200));
  }
}