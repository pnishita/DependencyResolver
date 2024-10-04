package com.java.feed.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        private LocalDate eventDate;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "feed_id", nullable = false)
        private Feed feed;
        private LocalDate cob;

        @Column(updatable = false, insertable = false)
        private LocalDateTime createdDate;
}
