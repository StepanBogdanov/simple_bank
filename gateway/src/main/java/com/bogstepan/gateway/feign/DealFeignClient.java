package com.bogstepan.gateway.feign;

import com.bogstepan.simple_bank.clients.api.DealApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "deal", url = "${feign.deal.url}")
public interface DealFeignClient extends DealApi {
}

