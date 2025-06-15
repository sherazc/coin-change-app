package com.sample.coinchange.controller;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.service.CoinExchange;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
public class CoinChangeController {
  private final CoinExchange coinExchange;

  @GetMapping(value = "/api/change/{bill}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<CoinType, Integer> calculateChange(@PathVariable Integer bill) {
    log.info("Calculating change for {}", bill);
    return coinExchange.makeChange(bill);
  }

  @GetMapping(value = "/api/coins/balance",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<CoinType, Integer> coinBalance() {
    log.info("Checking coins balance");
    return coinExchange.getCoinBalance();
  }
}
