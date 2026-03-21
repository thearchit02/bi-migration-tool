package com.bimigration.controller;

import com.bimigration.exception.MigrationException;
import com.bimigration.model.MigrationJob;
import com.bimigration.service.MigrationJobService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.io.File;
import com.bimigration.model.tableau.TableauWorkbook;
import com.bimigration.parser.TWBParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/migration")
public class MigrationController {

    private final MigrationJobService migrationJobService;
    private final TWBParser twbParser;
    private static final Logger log = LoggerFactory.getLogger(MigrationController.class);

    public MigrationController(MigrationJobService migrationJobService, TWBParser twbParser) {
        this.migrationJobService = migrationJobService;
        this.twbParser = twbParser;
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
            log.info("File exists: {}", file.exists());
            log.info("File path: {}", file.getAbsolutePath());
            TableauWorkbook workbook = twbParser.parse(file);
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
            TableauWorkbook workbook = twbParser.parse(file);
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