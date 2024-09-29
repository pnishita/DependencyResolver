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
                .addMappings(mapper -> mapper.map(NotificationDTO::getDate, Notification::setReceivedDate));
        Notification notification = modelMapper.map(notificationDTO, Notification.class);
        String feedName = notificationDTO.getFeedName();
        Feed existingFeed = feedService.findFeedByName(feedName);
        if (existingFeed == null) {
            feedService.saveFeed(feedService.findFeedByName(feedName));
        }
        notification.setFeed(existingFeed);
        return notificationRepo.save(notification);
    }
    public List<Notification> getNotificationByReceivedDate(LocalDate receivedDate) {
        return notificationRepo.findByReceivedDate(receivedDate);
    }

    public List<Notification> findByReceivedDate(LocalDate receivedDate) {
        return notificationRepo.findByReceivedDate(receivedDate);
    }
}
