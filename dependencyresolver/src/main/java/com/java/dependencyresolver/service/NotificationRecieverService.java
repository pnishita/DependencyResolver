package com.java.dependencyresolver.service;

import com.java.dependencyresolver.exception.NotificationParsingException;
import com.java.dependencyresolver.model.NotificationDTO;
import com.java.feed.entity.Feed;
import com.java.feed.service.FeedService;
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
    private DependencyResolverService dependencyResolverService;
    private FeedService feedService;
    @Autowired
    public NotificationRecieverService(DependencyResolverService dependencyResolverService,FeedService feedService,NotificationService notificationService) {
        this.notificationService = notificationService;
        this.feedService= feedService;
        this.dependencyResolverService=dependencyResolverService;
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

            dependencyResolverService.resolveFeedGroups(notificationDTO.getCob(),feed.getFeedId());

        } catch (NotificationParsingException e) {
            log.error("Failed to parse notification: " + notification, e);
        } catch (DataIntegrityViolationException | JpaSystemException e) {
            log.error("Database error while saving notification: " + notification, e);
        } catch (Exception e) {
            log.error("Unexpected error processing notification: " + notification, e);
        }
    }
}
