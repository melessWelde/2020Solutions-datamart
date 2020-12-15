package com.solutions.datamart.Cache;

import com.solutions.datamart.service.HashTagService;
import com.solutions.datamart.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.solutions.datamart.util.Constants.HASH_TAG;
import static com.solutions.datamart.util.Constants.USER_PROFILE;

@Component
public class CacheUtil {

    private Map<String, List<String>> cache = new HashMap<>();

    private HashTagService hashTagService;
    private UserProfileService userProfileService;

    @Autowired
    public CacheUtil(HashTagService hashTagService, UserProfileService userProfileService) {
        this.hashTagService = hashTagService;
        this.userProfileService = userProfileService;
    }

    public List<String> retrieveHashTagFromCache() {
        return cache.get(HASH_TAG);
    }

    public List<String> retrieveUserProfileFromCache() {
        return cache.get(USER_PROFILE);
    }

    public void fetchHashTag() {
        List<String> hashTagList = hashTagService.getHashTags();
        cache.put(HASH_TAG, hashTagList);
    }

    public void fetchUserProfile() {
        List<String> userProfile = userProfileService.getAllScreenNames();
        cache.put(USER_PROFILE, userProfile);
    }

    public int clearCache() {
        cache.clear();
        return !StringUtils.isEmpty(cache) ? cache.size() : 0;
    }

    public int getCacheSize() {
        return !StringUtils.isEmpty(cache) ? cache.size() : 0;
    }
}
