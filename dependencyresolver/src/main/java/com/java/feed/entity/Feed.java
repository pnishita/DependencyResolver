package com.java.feed.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notifications;

}