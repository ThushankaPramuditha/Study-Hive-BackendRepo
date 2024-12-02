package com.example.Study_Hive_Backend.notification.controller;

import com.example.Study_Hive_Backend.notification.model.Notification;
import com.example.Study_Hive_Backend.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

//    @PostMapping
//    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
//        return ResponseEntity.ok(notificationService.createNotification(notification));
//    }
@PostMapping
public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
    // Ensure detailId is not null
    if (notification.getDetailId() == null) {
        notification.setDetailId(0L);
    }
    return ResponseEntity.ok(notificationService.createNotification(notification));
}

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
//    @PutMapping("/{id}/status")
//    public ResponseEntity<Notification> updateNotificationStatus(
//            @PathVariable Long id,
//            @RequestParam String status) {
//        return ResponseEntity.ok(notificationService.updateNotificationStatus(id, status));
//    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Notification> updateNotificationStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        try {
            Notification updatedNotification = notificationService.updateNotificationStatus(id, status);
            if (updatedNotification != null) {
                return ResponseEntity.ok(updatedNotification);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
