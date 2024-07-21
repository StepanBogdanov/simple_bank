package com.bogstepan.gateway.feign;

import com.bogstepan.simple_bank.clients.api.StatementApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "statement", url = "http://localhost:8082")
public interface StatementFeignClient extends StatementApi {
}
