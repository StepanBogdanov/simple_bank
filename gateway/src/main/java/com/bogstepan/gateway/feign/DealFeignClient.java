package com.bogstepan.gateway.feign;

import com.bogstepan.simple_bank.clients.api.DealApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "deal", url = "http://127.0.0.1:8081")
public interface DealFeignClient extends DealApi {
}

