package com.java.dependencyresolver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.dependencyresolver.model.NotificationDTO;

public class NotificationParser {

    private final ObjectMapper objectMapper;

    public NotificationParser() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public NotificationDTO parseNotification(String notificationString) {
        try {
            String jsonString = convertToJsonString(notificationString);
            System.out.println("Converted JSON String: " + jsonString);
            return objectMapper.readValue(jsonString, NotificationDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String convertToJsonString(String notificationString) {
        String content = notificationString.replace("Notification(", "").replace(")", "");
        String[] parts = content.split(", ");
        StringBuilder jsonBuilder = new StringBuilder("{");
        for (String part : parts) {
            String[] keyValue = part.split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            jsonBuilder.append("\"").append(key).append("\":\"").append(value).append("\",");
        }
        jsonBuilder.setLength(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}
