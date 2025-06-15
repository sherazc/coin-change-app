package com.sample.coinchange.controller;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.service.CoinBank;
import com.sample.coinchange.service.CoinExchange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoinChangeController.class)
class CoinChangeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CoinExchange coinExchange;

  @MockBean
  private CoinBank coinBank;

  @Test
  void calculateChange() throws Exception {
    Map<CoinType, Integer> coinBag = Map.of(CoinType.QUARTER, 20);
    when(coinExchange.makeChange(5)).thenReturn(coinBag);
    mockMvc.perform(get("/api/change/5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[\"0.25\"]").value("20"));
  }
}