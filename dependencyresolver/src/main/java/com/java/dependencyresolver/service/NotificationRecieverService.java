package com.java.dependencyresolver.service;

import com.java.dependencyresolver.model.NotificationDTO;
import com.java.feed.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class NotificationRecieverService {
    private NotificationService notificationService;
    private DependencyResolverService dependencyResolverService;
    @Autowired
    public NotificationRecieverService(NotificationService notificationService,DependencyResolverService dependencyResolverService) {
        this.notificationService = notificationService;
        this.dependencyResolverService=dependencyResolverService;
    }
    @JmsListener(destination = "Notifications-Queue")
    public void receiveNotification(String notification) {
        //log.info("Received Notification: " + notification);
        try {
            NotificationParser parser = new NotificationParser();
            NotificationDTO notificationDTO = parser.parseNotification(notification);
            notificationService.saveNotification(notificationDTO);
            log.info("Notification saved successfully: " + notificationDTO);
            System.out.println(dependencyResolverService.resolveFeedGroups(notificationDTO.getDate()));
        } catch (Exception e) {
            log.error("Error processing notification: " + notification, e);
        }
    }
}
