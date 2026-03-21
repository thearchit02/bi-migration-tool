package com.bimigration.controller;

import com.bimigration.model.MigrationJob;
import com.bimigration.service.MigrationJobService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/migration")
public class MigrationController {

    private final MigrationJobService migrationJobService;

    public MigrationController(MigrationJobService migrationJobService) {
        this.migrationJobService = migrationJobService;
    }

    @GetMapping("/health")
    public String health() {
        return "BI Migration Tool is running!";
    }

    @PostMapping("/job")
    public MigrationJob createJob(@RequestParam String fileName) {
        return migrationJobService.createJob(fileName);
    }

    @GetMapping("/jobs")
    public List<MigrationJob> getAllJobs() {
        return migrationJobService.getAllJobs();
    }
}