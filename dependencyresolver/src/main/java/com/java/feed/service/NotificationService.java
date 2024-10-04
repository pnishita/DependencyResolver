package com.java.feed.service;

import com.java.dependencyresolver.model.NotificationDTO;
import com.java.feed.entity.Feed;
import com.java.feed.entity.Notification;
import com.java.feed.repo.NotificationRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class NotificationService {
    private final FeedService feedService;
    private final ModelMapper modelMapper;
    private final NotificationRepo notificationRepo;

    @Autowired
    public NotificationService(NotificationRepo notificationRepo, ModelMapper modelMapper, FeedService feedService) {
        this.notificationRepo = notificationRepo;
        this.modelMapper = modelMapper;
        this.feedService = feedService;
    }

    public Notification saveNotification(NotificationDTO notificationDTO) {
        modelMapper.typeMap(NotificationDTO.class, Notification.class)
                .addMappings(mapper -> mapper.map(NotificationDTO::getCob, Notification::setCob));

        Notification notification = modelMapper.map(notificationDTO, Notification.class);
        String feedName = notificationDTO.getFeedName();
        Feed existingFeed = feedService.findFeedByName(feedName);
        notification.setFeed(existingFeed);
        return notificationRepo.save(notification);
    }

    public List<Notification> findByCob(LocalDate cob) {
        return notificationRepo.findByCob(cob);
    }
    public List<Notification> getByFeedId(Set<Long> feedIds) {
        return notificationRepo.findByFeed_FeedIdIn(feedIds);
    }

    // New method to get the latest notifications by cob using your query
    public List<Notification> findLatestNotificationsByCob(LocalDate cob) {
        return notificationRepo.findLatestNotificationsByCob(cob);
    }
}
