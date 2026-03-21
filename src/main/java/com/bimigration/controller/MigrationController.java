package com.bimigration.controller;

import com.bimigration.exception.MigrationException;
import com.bimigration.model.MigrationJob;
import com.bimigration.model.tableau.TableauWorkbook;
import com.bimigration.parser.TableauFileReader;
import com.bimigration.service.MigrationJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/migration")
public class MigrationController {

    private static final Logger log = LoggerFactory.getLogger(MigrationController.class);

    private final MigrationJobService migrationJobService;
    private final TableauFileReader tableauFileReader;

    public MigrationController(MigrationJobService migrationJobService,
            TableauFileReader tableauFileReader) {
        this.migrationJobService = migrationJobService;
        this.tableauFileReader = tableauFileReader;
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

    @GetMapping("/test-error")
    public String testError() {
        throw new MigrationException("TEST_ERROR", "This is a test error message");
    }

    @GetMapping("/parse")
    public String parseFile(@RequestParam String filePath) {
        try {
            log.info("Parsing file: {}", filePath);
            File file = new File(filePath);
            TableauWorkbook workbook = tableauFileReader.read(file);
            return String.format(
                    "Parsed successfully! Version: %s, Datasources: %d, Worksheets: %d, Total Fields: %d, Calculated Fields: %d",
                    workbook.getVersion(),
                    workbook.getDatasources().size(),
                    workbook.getWorksheets().size(),
                    workbook.getTotalFields(),
                    workbook.getTotalCalculatedFields());
        } catch (Exception e) {
            log.error("Parse error: ", e);
            throw new MigrationException("PARSE_ERROR", "Failed to parse file: " + e.getMessage());
        }
    }

    @GetMapping("/parse/details")
    public String parseDetails(@RequestParam String filePath) {
        try {
            File file = new File(filePath);
            TableauWorkbook workbook = tableauFileReader.read(file);
            StringBuilder sb = new StringBuilder();
            sb.append("=== CALCULATED FIELDS ===\n");
            workbook.getAllCalculatedFields().forEach(field -> {
                sb.append("Name: ").append(field.getName()).append("\n");
                sb.append("Formula: ").append(field.getCalculationFormula()).append("\n");
                sb.append("Type: ").append(field.getFormulaType()).append("\n");
                sb.append("---\n");
            });
            return sb.toString();
        } catch (Exception e) {
            log.error("Parse error: ", e);
            throw new MigrationException("PARSE_ERROR", "Failed to parse: " + e.getMessage());
        }
    }
}