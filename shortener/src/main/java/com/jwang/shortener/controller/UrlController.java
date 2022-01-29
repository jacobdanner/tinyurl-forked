package com.jwang.shortener.controller;

import com.jwang.shortener.service.UrlService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/url")
public class UrlController {

    private UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @GetMapping("/retrieve/{hash}")
    public Map<String, Object> retrieveOriginal(@PathVariable String hash){
        String originalUrl = urlService.retrieveOriginalUrl(hash);
        Map<String, Object> res = new HashMap<>();
        if(originalUrl == null){
            res.put("error", "url not found");
        }
        res.put("data", originalUrl);
        return res;
    }

    @PostMapping("/generate")
    public Map<String, Object> createShortUrl(@RequestBody Map<String, String> reqBody){
        String originalUrl = reqBody.get("original");
        String shortenUrl;
        Map<String, Object> res = new HashMap<>();

        if(reqBody.containsKey("hash")){
            shortenUrl = urlService.customizeShortenUrl(originalUrl, reqBody.get("hash"));
            if(shortenUrl==null){
                res.put("error", "url in use");
            }
        }else{
            shortenUrl = urlService.generateShortenUrl(originalUrl);
        }
        res.put("data", shortenUrl);
        return res;
    }
}
