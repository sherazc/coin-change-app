package com.sample.coinchange.exception;

import com.sample.coinchange.service.CoinExchange;

import java.util.List;
import java.util.stream.Collectors;

public class InvalidBillException extends RuntimeException {
  public InvalidBillException(List<Integer> allowedBills) {
    super(String.format("Invalid bill. It should be one of these: %s",
        allowedBills.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", "))));
  }
}
