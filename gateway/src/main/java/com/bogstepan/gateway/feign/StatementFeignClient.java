package com.bogstepan.gateway.feign;

import com.bogstepan.simple_bank.clients.api.StatementApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "statement", url = "${feign.statement.url}")
public interface StatementFeignClient extends StatementApi {
}
