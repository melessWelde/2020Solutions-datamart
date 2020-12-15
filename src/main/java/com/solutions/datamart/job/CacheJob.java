package com.solutions.datamart.job;

import com.solutions.datamart.Cache.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
public class CacheJob {

    @Autowired
    private CacheUtil cacheUtil;

    @Scheduled(fixedRate = 30 * 60 * 1000, initialDelay = 0)
    public void refreshCache() {
        int clearSize = cacheUtil.clearCache();
        log.info("Cache size after clear is {} ", clearSize);

        cacheUtil.fetchHashTag();
        cacheUtil.fetchUserProfile();

        log.info("Cache size after refresh is {} ", cacheUtil.getCacheSize());

    }
}
