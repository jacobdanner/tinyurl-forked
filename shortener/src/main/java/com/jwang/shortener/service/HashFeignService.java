/* (C)2024 */
package com.jwang.shortener.service;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(value = "hash-depl")
public interface HashFeignService {
    @GetMapping("/hash/retrieve")
    Map<String, Object> retrieve();

    @GetMapping("/hash/{hash}")
    Map<String, Object> markHashAsUsed(@PathVariable String hash);

    @GetMapping("/hash/unused/{hash}")
    Map<String, Object> markHashAsUnUsed(@PathVariable String hash);
}
