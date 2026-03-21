package com.bimigration.service;

import com.bimigration.model.MigrationJob;
import com.bimigration.repository.MigrationJobRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MigrationJobService {

    private final MigrationJobRepository repository;

    public MigrationJobService(MigrationJobRepository repository) {
        this.repository = repository;
    }

    public MigrationJob createJob(String fileName) {
        MigrationJob job = MigrationJob.builder()
                .fileName(fileName)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        return repository.save(job);
    }

    public List<MigrationJob> getAllJobs() {
        return repository.findAll();
    }
}