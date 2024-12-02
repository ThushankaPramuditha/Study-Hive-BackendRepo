//package com.example.Study_Hive_Backend.notification.model;
//
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name = "notifications")
//@Data
//public class Notification {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "user_id", nullable = false)
//    private Long userId;
//
//    @Column(name = "detail_id", nullable = false)
//    private Long detailId;
//
//    @Column(name = "detail", nullable = false)
//    private String detail;
//
//    @Column(name = "status", nullable = false)
//    private String status;
//}

package com.example.Study_Hive_Backend.notification.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "detail_id", nullable = false)
    private Long detailId;

    @Column(name = "detail", nullable = false)
    private String detail;

    @Column(name = "status", nullable = false)
    private String status;

    // Add a default value for detailId
    public Notification() {
        this.detailId = 0L; // Set a default value
    }
}