package com.sample.coinchange.exception;

public class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException(int billRequest, double availableFunds) {
    super(String.format("Insufficient funds. Requested amount: $%d. Available Funds: $%.2f", billRequest, availableFunds));
  }
}
