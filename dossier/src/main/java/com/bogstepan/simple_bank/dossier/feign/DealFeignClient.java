package com.bogstepan.simple_bank.dossier.feign;

import com.bogstepan.simple_bank.clients.api.DealApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "deal", url = "http://localhost:8081")
public interface DealFeignClient extends DealApi {
}