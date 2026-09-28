package com.blood.bloodlink.controller;

import com.blood.bloodlink.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(
            StatisticsService statisticsService) {

        this.statisticsService = statisticsService;
    }


   

    @GetMapping("/blood-groups")
    public ResponseEntity<Map<String, Long>>
            getBloodGroupStatistics() {

        return ResponseEntity.ok(
                statisticsService.getBloodGroupStatistics()
        );
    }


    

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>>
            getSummaryStatistics() {

        return ResponseEntity.ok(
                statisticsService.getSummaryStatistics()
        );
    }


    

    @GetMapping("/cities")
    public ResponseEntity<Map<String, Long>>
            getCityStatistics() {

        return ResponseEntity.ok(
                statisticsService.getCityStatistics()
        );
    }

   

@GetMapping("/dashboard")
public ResponseEntity<Map<String, Object>>
        getDashboardStatistics() {

    return ResponseEntity.ok(
            statisticsService.getDashboardStatistics()
    );
}
}