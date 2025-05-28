package com.sample.coinchange.controller;

import com.sample.coinchange.dto.CoinType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class CoinChangeController {

    @GetMapping(value = "/api/change/{bill}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<CoinType, Integer> calculateChange(@PathVariable Integer bill) {
        log.info("Calculating change for {}", bill);
        return Map.of(
                CoinType.QUARTER, 2,
                CoinType.DIME, 5,
                CoinType.NICKEL, 20
        );
    }
}
