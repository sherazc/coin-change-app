package com.sample.coinchange.exception;

public class InvalidBillException extends RuntimeException {
  public InvalidBillException() {
    super("Bill is invalid. It should be one of these 1, 2, 5, 10, 20, 50, 100");
  }
}
