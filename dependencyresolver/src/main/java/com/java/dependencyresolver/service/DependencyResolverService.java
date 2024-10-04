package com.java.dependencyresolver.service;

import com.java.feed.entity.FeedConfig;
import com.java.feed.entity.Notification;
import com.java.feed.service.FeedConfigService;
import com.java.feed.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DependencyResolverService {
    private final FeedConfigService feedConfigService;
    private final NotificationService notificationService;

    @Autowired
    public DependencyResolverService(NotificationService notificationService, FeedConfigService feedConfigService) {
        this.feedConfigService = feedConfigService;
        this.notificationService = notificationService;
    }

    Map<Long, Integer> groupVersions = new HashMap<>();

    public void resolveFeedGroups(LocalDate cob, long currentFeedId) {
        List<FeedConfig> feedConfigs = feedConfigService.findAll();

        Map<Long, Set<Long>> groups = new HashMap<>();
        for (FeedConfig feedConfig : feedConfigs) {
            Long groupId = feedConfig.getFeedGroup().getGroupId();
            Long feedId = feedConfig.getFeed().getFeedId();
            groups.computeIfAbsent(groupId, k -> new HashSet<>()).add(feedId);
        }
        System.out.println("Groups: " + groups);

        List<Notification> latestNotificationList = notificationService.findLatestNotificationsByCob(cob);
        Set<Long> feedIdList = latestNotificationList.stream()
                .map(notification -> notification.getFeed().getFeedId())
                .collect(Collectors.toSet());

        System.out.println("Feed IDs from latest notifications: " + feedIdList);

        for (Map.Entry<Long, Set<Long>> groupEntry : groups.entrySet()) {
            Long groupId = groupEntry.getKey();
            Set<Long> groupFeedIds = groupEntry.getValue();

            // Check if the latest notifications contain all feedIds for this group
            if (feedIdList.containsAll(groupFeedIds)) {
                if (groupVersions.containsKey(groupId)) {
                    if (groupFeedIds.contains(currentFeedId)) {
                        groupVersions.put(groupId, groupVersions.get(groupId) + 1);
                        System.out.println("Group " + groupId + " resolved: version " + groupVersions.get(groupId));
                    }
                } else {
                    groupVersions.put(groupId, 1);
                    System.out.println("Group " + groupId + " resolved: version 1");
                }
            }
        }

        System.out.println("Resolved Groups:");
        for (Map.Entry<Long, Integer> groupEntry : groupVersions.entrySet()) {
            System.out.println("[Group " + groupEntry.getKey() + " - version: " + groupEntry.getValue() + "]");
        }
    }
}
