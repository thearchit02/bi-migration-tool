package com.bimigration.repository;

import com.bimigration.model.MigrationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigrationJobRepository extends JpaRepository<MigrationJob, Long> {
}