package com.java.feed.entity;


import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.util.Date;


@Entity
public class LatestNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "notification_id", unique = true)
    private Long notificationId;

    @Column(name="message")
    private String message;

    private Integer feedId;

    private Date cob;
}

