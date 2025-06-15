package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.repository.CoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoinTransactionTest {

  @InjectMocks
  private CoinTransaction underTest;

  @Mock
  private CoinRepository coinRepository;

  @Mock
  private CoinCalculator coinCalculator;

  @Test
  void withdrawCoinBalance_use_all_available_coins() {
    // Setup
    int balanceCentsIn = 100;
    int availableCoins = 2;
    int neededCoins = 4;
    CoinType coinType = CoinType.QUARTER;

    when(coinRepository.getByType(coinType)).thenReturn(availableCoins);
    when(coinCalculator.centsToCoins(coinType, balanceCentsIn)).thenReturn(neededCoins);
    when(coinCalculator.coinsToCents(coinType, availableCoins)).thenReturn(50);
    doNothing().when(coinRepository).update(coinType, 0);

    Map<CoinType, Integer> coinBag = new HashMap<>();

    // Call
    int balanceCentsForward = underTest.withdrawCoinBalance(coinType, balanceCentsIn, coinBag);

    // Verify
    assertEquals(50, balanceCentsForward);
    assertEquals(1, coinBag.size());
    assertEquals(2, coinBag.get(coinType));

    verify(coinRepository).getByType(coinType);
    verify(coinCalculator).centsToCoins(coinType, balanceCentsIn);
    verify(coinCalculator).coinsToCents(coinType, availableCoins);
    verify(coinRepository).update(coinType, 0);
  }

  @Test
  void withdrawCoinBalance_zero_balance_forward() {
    // Setup
    int balanceCentsIn = 10;
    int availableCoins = 50;
    int neededCoins = 2;

    CoinType coinType = CoinType.NICKEL;

    when(coinRepository.getByType(coinType)).thenReturn(availableCoins);
    when(coinCalculator.centsToCoins(coinType, balanceCentsIn)).thenReturn(neededCoins);
    when(coinCalculator.coinsToCents(coinType, neededCoins)).thenReturn(10);
    doNothing().when(coinRepository).update(coinType, 48);

    Map<CoinType, Integer> coinBag = new HashMap<>();

    // Call
    int balanceCentsForward = underTest.withdrawCoinBalance(coinType, balanceCentsIn, coinBag);

    // Verify
    assertEquals(0, balanceCentsForward);
    assertEquals(1, coinBag.size());
    assertEquals(2, coinBag.get(coinType));

    verify(coinRepository).getByType(coinType);
    verify(coinCalculator).centsToCoins(coinType, balanceCentsIn);
    verify(coinCalculator).coinsToCents(coinType, neededCoins);
    verify(coinRepository).update(coinType, 48);
  }

  @Test
  void withdrawCoinBalance_balance_forward() {
    // Setup
    int balanceCentsIn = 12;
    int availableCoins = 50;
    int neededCoins = 1;

    CoinType coinType = CoinType.DIME;

    when(coinRepository.getByType(coinType)).thenReturn(availableCoins);
    when(coinCalculator.centsToCoins(coinType, balanceCentsIn)).thenReturn(neededCoins);
    when(coinCalculator.coinsToCents(coinType, neededCoins)).thenReturn(10);
    doNothing().when(coinRepository).update(coinType, 49);

    Map<CoinType, Integer> coinBag = new HashMap<>();

    // Call
    int balanceCentsForward = underTest.withdrawCoinBalance(coinType, balanceCentsIn, coinBag);

    // Verify
    assertEquals(2, balanceCentsForward);
    assertEquals(1, coinBag.size());
    assertEquals(1, coinBag.get(coinType));

    verify(coinRepository).getByType(coinType);
    verify(coinCalculator).centsToCoins(coinType, balanceCentsIn);
    verify(coinCalculator).coinsToCents(coinType, neededCoins);
    verify(coinRepository).update(coinType, 49);
  }

  @Test
  void withdrawCoinBalance_no_available_coins() {
    // Setup
    int balanceCentsIn = 100;
    int availableCoins = 0;
    int neededCoins = 4;
    CoinType coinType = CoinType.QUARTER;

    when(coinRepository.getByType(coinType)).thenReturn(availableCoins);

    Map<CoinType, Integer> coinBag = new HashMap<>();

    // Call
    int balanceCentsForward = underTest.withdrawCoinBalance(coinType, balanceCentsIn, coinBag);

    // Verify
    assertEquals(balanceCentsIn, balanceCentsForward);
    assertEquals(0, coinBag.size());
    assertNull(coinBag.get(coinType));

    verify(coinRepository).getByType(coinType);
    verify(coinCalculator, atLeast(0)).centsToCoins(coinType, balanceCentsIn);
    verify(coinCalculator, atLeast(0)).coinsToCents(coinType, availableCoins);
    verify(coinRepository, atLeast(0)).update(coinType, 0);
  }
}