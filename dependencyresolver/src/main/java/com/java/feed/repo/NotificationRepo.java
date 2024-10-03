package com.java.feed.repo;

import com.java.feed.entity.Feed;
import com.java.feed.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long> {
   List<Notification> findByCob(LocalDate cob);

    List<Notification> findByFeedAndCob(Feed feed, LocalDate cob);


    Optional<Notification> findLatestByFeedAndCob(Feed feed, LocalDate cob);
}
