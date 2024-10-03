package com.java.feed.repo;

import com.java.feed.entity.LatestNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
@Repository
public interface LatestNotificationRepository extends JpaRepository<LatestNotification,Long> {
    @Transactional
    @Modifying
    @Query(value = "CALL UpdateLatestNotification(:feedId, :cob)", nativeQuery = true)
    void updateLatestNotification(Long feedId, LocalDate cob);
}
