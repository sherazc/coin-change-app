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
  void breakBillIntoCoins() {
    assertEquals(4, underTest.breakBillIntoCoins(CoinType.QUARTER, 1));
    assertEquals(0, underTest.breakBillIntoCoins(CoinType.QUARTER, 0));
    assertEquals(8, underTest.breakBillIntoCoins(CoinType.QUARTER, 2));

    assertEquals(10, underTest.breakBillIntoCoins(CoinType.DIME, 1));
    assertEquals(0, underTest.breakBillIntoCoins(CoinType.DIME, 0));
    assertEquals(20, underTest.breakBillIntoCoins(CoinType.DIME, 2));

    assertEquals(20, underTest.breakBillIntoCoins(CoinType.NICKEL, 1));
    assertEquals(0, underTest.breakBillIntoCoins(CoinType.NICKEL, 0));
    assertEquals(40, underTest.breakBillIntoCoins(CoinType.NICKEL, 2));

    assertEquals(100, underTest.breakBillIntoCoins(CoinType.PENNY, 1));
    assertEquals(0, underTest.breakBillIntoCoins(CoinType.PENNY, 0));
    assertEquals(200, underTest.breakBillIntoCoins(CoinType.PENNY, 2));
  }
}