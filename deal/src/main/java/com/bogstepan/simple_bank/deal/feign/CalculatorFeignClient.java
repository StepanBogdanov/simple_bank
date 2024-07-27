package com.bogstepan.simple_bank.deal.feign;

import com.bogstepan.simple_bank.clients.api.CalculatorApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "calculator",url = "${feign.calculator.url}")
public interface CalculatorFeignClient extends CalculatorApi {}
