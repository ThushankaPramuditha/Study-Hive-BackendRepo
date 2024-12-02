package com.example.Study_Hive_Backend.admin.controller;



import com.example.Study_Hive_Backend.admin.dto.DaySummaryDTO;
import com.example.Study_Hive_Backend.admin.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/7days")
    public ResponseEntity<List<DaySummaryDTO>> getPast7DaysSummary() {
        List<DaySummaryDTO> summary = reportService.getPast7DaysSummary();
        return ResponseEntity.ok(summary);
    }
}
