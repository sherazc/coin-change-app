package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.exception.InsufficientFundsException;
import com.sample.coinchange.exception.InvalidBillException;
import com.sample.coinchange.repository.CoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillValidatorTest {

  @InjectMocks
  private BillValidator underTest;

  @Mock
  private CoinRepository coinRepository;

  @Mock
  private CoinCalculator coinCalculator;

  @Test
  void validateBill() {
    // Setup
    Map<CoinType, Integer> allCoins = Map.of(CoinType.QUARTER, 100);
    when(coinRepository.getAll()).thenReturn(allCoins);
    when(coinCalculator.total(allCoins)).thenReturn(25d);

    // Call
    underTest.validateBill(5);

    // Verify
    verify(coinRepository).getAll();
    verify(coinCalculator).total(allCoins);
  }

  @Test
  void validateBill_bad_bill() {
    // Setup
    Map<CoinType, Integer> allCoins = Map.of();

    // Call
    assertThrows(InvalidBillException.class, () -> underTest.validateBill(-1));
    assertThrows(InvalidBillException.class, () -> underTest.validateBill(0));
    assertThrows(InvalidBillException.class, () -> underTest.validateBill(3));

    // Verify
    verify(coinRepository, atLeast(0)).getAll();
    verify(coinCalculator, atLeast(0)).total(allCoins);
  }

  @Test
  void validateBill_insufficient_funds() {
    // Setup
    Map<CoinType, Integer> allCoins = Map.of();
    when(coinRepository.getAll()).thenReturn(allCoins);
    when(coinCalculator.total(allCoins)).thenReturn(10d);

    // Call
    assertThrows(InsufficientFundsException.class, () -> underTest.validateBill(20));

    // Verify
    verify(coinRepository).getAll();
    verify(coinCalculator).total(allCoins);
  }
}
