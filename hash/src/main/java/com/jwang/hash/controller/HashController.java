/* (C)2024 */
package com.jwang.hash.controller;

import com.jwang.hash.service.RetrieveService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hash")
public class HashController {

    private final RetrieveService retrieveService;

    public HashController(RetrieveService retrieveService) {
        this.retrieveService = retrieveService;
    }

    @GetMapping("/retrieve")
    public Map<String, Object> retrieve() {
        Map<String, Object> result = new HashMap<>();
        String hash = retrieveService.retrieveOne();
        result.put("data", hash);
        return result;
    }

    @GetMapping("/{hash}")
    public Map<String, Object> markHashAsUsed(@PathVariable String hash) {
        Map<String, Object> result = new HashMap<>();
        retrieveService.markHashAsUsed(hash);
        result.put("status", 200);
        return result;
    }

    @GetMapping("/unused/{hash}")
    public Map<String, Object> markHashAsUnUsed(@PathVariable String hash) {
        Map<String, Object> result = new HashMap<>();
        retrieveService.markHashAsUnused(hash);
        result.put("status", 200);
        return result;
    }

    @GetMapping("/")
    public String test() {
        return "hash service";
    }
}
