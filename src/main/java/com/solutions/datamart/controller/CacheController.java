package com.solutions.datamart.controller;

import com.solutions.datamart.Cache.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.solutions.datamart.util.Constants.*;

@Slf4j
@RestController
public class CacheController {
    @Value("${spring.social.twitter.appId}")
    private String consumerKey;
    @Autowired
    CacheUtil cacheUtil;

    @GetMapping("/cachedate")
    public String cacheDate() {
        try {
            cacheUtil.fetchHashTag();
            cacheUtil.fetchUserProfile();
            return SUCCESS_CACHE_MSG;
        } catch (Exception e) {
            log.error(EXCEPTION_MESSAGE, "caching data", "cacheDate", e.getMessage(), e.getCause());
            return ERROR_CACHE_MSG;
        }
    }

    @GetMapping("/clearcache")
    public String clearCache() {
        try {
            cacheUtil.clearCache();
            return SUCCESS_CLEAR_CACHE_MSG;
        } catch (Exception e) {
            log.error(EXCEPTION_MESSAGE, "clearing cache", "clearCache", e.getMessage(), e.getCause());
            return ERROR_CLEAR_CACHE_MSG;
        }
    }
}
