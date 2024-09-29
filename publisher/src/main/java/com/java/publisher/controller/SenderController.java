package com.java.publisher.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.java.publisher.model.Notification;
import com.java.publisher.service.NotificationSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publisher")
public class SenderController {
        private NotificationSenderService  service;

        @Autowired
        public SenderController(NotificationSenderService service) {
            this.service = service;
        }
        @PostMapping("/send")
        public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
            service.publishNotification(notification);
            return new ResponseEntity<>("Notification sent to queue successfully!", HttpStatus.OK);
        }

}
