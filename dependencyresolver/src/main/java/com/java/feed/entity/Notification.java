package com.java.feed.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="Notifications")
public class Notification {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Lob
        private String message;
        private LocalDate receivedDate;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "feed_id", nullable = false)
        private Feed feed;
}
