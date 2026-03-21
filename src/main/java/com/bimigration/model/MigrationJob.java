package com.bimigration.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "migration_jobs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MigrationJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private Integer totalFields;
    private Integer successCount;
    private Integer failureCount;
    private Double confidenceScore;
}