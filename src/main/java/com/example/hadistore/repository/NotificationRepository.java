package com.example.hadistore.repository;

import com.example.hadistore.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByOrderByIdDesc();

    @Modifying
    @Query(value = "update notification set status = true", nativeQuery = true)
    void readAll();
}
