package com.java.dependencyresolver.service;

import com.java.dependencyresolver.model.NotificationDTO;
import com.java.feed.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationRecieverService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationRecieverService.class);
    private final NotificationService notificationService;

    @Autowired
    public NotificationRecieverService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @JmsListener(destination = "Notifications-Queue")
    public void receiveNotification(String notification) {
        logger.info("Received Notification: " + notification);
        try {
            NotificationParser parser = new NotificationParser();
            NotificationDTO notificationDTO = parser.parseNotification(notification);
            //System.out.println("Parsed NotificationDTO Date: " + notificationDTO.getDate());

            notificationService.saveNotification(notificationDTO);
            logger.info("Notification saved successfully: " + notificationDTO);
        } catch (Exception e) {
            logger.error("Error processing notification: " + notification, e);
        }
    }
}
