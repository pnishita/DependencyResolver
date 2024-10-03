package com.java.dependencyresolver.service;

import com.java.dependencyresolver.exception.NotificationParsingException;
import com.java.dependencyresolver.model.NotificationDTO;
import com.java.feed.repo.LatestNotificationRepository;
import com.java.feed.entity.Feed;
import com.java.feed.service.FeedService;
import com.java.feed.service.LatestNotificationService;
import com.java.feed.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@Slf4j
public class NotificationRecieverService {
    private NotificationService notificationService;
    private LatestNotificationService latestNotificationService;
    private FeedService feedService;
    @Autowired
    public NotificationRecieverService(FeedService feedService,LatestNotificationService latestNotificationService,NotificationService notificationService) {
        this.notificationService = notificationService;
        this.latestNotificationService=latestNotificationService;
        this.feedService= feedService;
    }
    @JmsListener(destination = "Notifications-Queue")
    public void receiveNotification(String notification) {
        try {
            NotificationParser parser = new NotificationParser();
            NotificationDTO notificationDTO = parser.parseNotification(notification);
            notificationDTO.setCob(LocalDate.now());
            notificationService.saveNotification(notificationDTO);
            log.info("Notification saved successfully: " + notificationDTO.toString());
            Feed feed=feedService.findFeedByName(notificationDTO.getFeedName());
            latestNotificationService.updateLatestNotification(feed.getFeedId(),notificationDTO.getCob());
        } catch (NotificationParsingException e) {
            log.error("Failed to parse notification: " + notification, e);
        } catch (DataIntegrityViolationException | JpaSystemException e) {
            log.error("Database error while saving notification: " + notification, e);
        } catch (Exception e) {
            log.error("Unexpected error processing notification: " + notification, e);
        }
    }
}
