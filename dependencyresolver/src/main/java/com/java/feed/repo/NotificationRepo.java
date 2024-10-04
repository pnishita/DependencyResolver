package com.java.feed.repo;

import com.java.feed.entity.Feed;
import com.java.feed.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long> {
   List<Notification> findByCob(LocalDate cob);

    List<Notification> findByFeedAndCob(Feed feed, LocalDate cob);

    @Query(value = "SELECT * FROM notifications " +
            "WHERE id IN (SELECT MAX(id) FROM notifications " +
            "WHERE cob = :cob AND feed_id IN (SELECT feed_id FROM feed) " +
            "GROUP BY feed_id)", nativeQuery = true)
    List<Notification> findLatestNotificationsByCob(LocalDate cob);

    // New method to get notifications by a set of feed IDs
    List<Notification> findByFeed_FeedIdIn(Set<Long> feedIds);
    Optional<Notification> findLatestByFeedAndCob(Feed feed, LocalDate cob);
}
