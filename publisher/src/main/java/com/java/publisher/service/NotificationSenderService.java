package com.java.publisher.service;

import com.java.publisher.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationSenderService {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public NotificationSenderService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void publishNotification(Notification notification) {
        notification.setDate(LocalDate.now());
        System.out.println(notification);
        jmsTemplate.convertAndSend("Notifications-Queue", notification.toString());
    }
}
