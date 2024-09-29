package com.java.dependencyresolver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

public class DependencyResolverService {
    private final FeedConfigService feedConfigService;
    private final FeedGroupService feedGroupService;
    private final CacheManager cacheManager;

    @Autowired
    public DependencyResolverService(FeedConfigService feedConfigService, FeedGroupService feedGroupService, CacheManager cacheManager) {
        this.feedConfigService = feedConfigService;
        this.feedGroupService = feedGroupService;
        this.cacheManager = cacheManager;
    }

    public void processNotification(Notification notification) {
        Long feedId = notification.getFeed().getFeedId();
        LocalDate receivedDate = notification.getReceivedDate();

        // Find all groups where this feed is part of (using FeedConfigService)
        List<Long> groupIds = feedConfigService.getGroupIdsByFeedId(feedId);

        for (Long groupId : groupIds) {
            // Retrieve the current feed set from cache for this group and date
            Set<Long> receivedFeeds = cacheManager.getCache("groupCache").get(groupId + "_" + receivedDate, Set.class);

            if (receivedFeeds == null) {
                receivedFeeds = new HashSet<>();
            }

            // Add the current feed to the set of received feeds
            receivedFeeds.add(feedId);

            // Store the updated set back in the cache
            cacheManager.getCache("groupCache").put(groupId + "_" + receivedDate, receivedFeeds);

            // Check if all feeds in this group have been received (using FeedGroupService)
            List<Long> requiredFeeds = feedConfigService.getFeedIdsByGroupId(groupId);

            if (receivedFeeds.containsAll(requiredFeeds)) {
                // All feeds for this group have been received, resolve the group
                resolveGroup(groupId);
                // Invalidate the cache entry for this group and date
                cacheManager.getCache("groupCache").evict(groupId + "_" + receivedDate);
            }
        }
    }

    private void resolveGroup(Long groupId) {
        // Logic to send the resolved message for the group
        System.out.println("Resolved group: " + groupId);
        // Further processing or JMS messaging logic here
    }
}
