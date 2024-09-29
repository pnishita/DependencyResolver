package com.java.dependencyresolver.service;

import com.java.feed.entity.FeedConfig;
import com.java.feed.entity.FeedGroup;
import com.java.feed.entity.Notification;
import com.java.feed.service.FeedConfigService;
import com.java.feed.service.FeedGroupService;
import com.java.feed.service.NotificationService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
@Service
public class DependencyResolverService {
    private final FeedConfigService feedConfigService;
    private final NotificationService notificationService;
    private final FeedGroupService feedGroupService;

    public DependencyResolverService(FeedConfigService feedConfigService, NotificationService notificationService, FeedGroupService feedGroupService) {
        this.feedConfigService = feedConfigService;
        this.notificationService = notificationService;
        this.feedGroupService = feedGroupService;
    }

    public List<FeedGroup> resolveFeedGroups(LocalDate receivedDate) {
        List<Notification> notification=notificationService.getNotificationByReceivedDate(receivedDate);
        List<FeedConfig> feedConfigs=feedConfigService.findAll();
        List<FeedGroup> feedGroups=feedGroupService.getAllGroups();

        List<Long> feedIdInNotification=notification.stream()
                .map(n -> n.getFeed().getFeedId())
                .toList();
        System.out.println("Feed IDs in notification: " + feedIdInNotification);
        Map<Long, Set<Long>> groupIdToFeedIds = new HashMap<>();
        for (FeedConfig feedConfig : feedConfigs) {
            Long groupId = feedConfig.getFeedGroup().getGroupId();
            Long feedId = feedConfig.getFeed().getFeedId();
            groupIdToFeedIds.computeIfAbsent(groupId, k -> new HashSet<>()).add(feedId);
        }

        groupIdToFeedIds.forEach((groupId, feedIds) ->
            System.out.printf("Group ID: %d, Feed IDs: %s%n", groupId, feedIds));

        List<FeedGroup> resolvedGroups = new ArrayList<>();
        for (Map.Entry<Long, Set<Long>> entry : groupIdToFeedIds.entrySet()) {
            Long groupId = entry.getKey();
            Set<Long> feedIds = entry.getValue();
            if (feedIdInNotification.containsAll(feedIds)) {
                Optional<FeedGroup> group = feedGroups.stream()
                        .filter(g -> g.getGroupId().equals(groupId))
                        .findFirst();

                group.ifPresent(resolvedGroups::add);
                System.out.printf("Group ID %d is resolved with Feed IDs: %s%n", groupId, feedIds);
            }
        }

        return resolvedGroups;
    }


}
