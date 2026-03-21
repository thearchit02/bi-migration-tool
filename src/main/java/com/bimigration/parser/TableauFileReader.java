package com.bimigration.parser;

import com.bimigration.model.tableau.TableauWorkbook;
import org.springframework.stereotype.Component;
import java.io.File;

@Component
public class TableauFileReader {

    private final TWBParser twbParser;
    private final TWBXUnpacker twbxUnpacker;

    public TableauFileReader(TWBParser twbParser,
            TWBXUnpacker twbxUnpacker) {
        this.twbParser = twbParser;
        this.twbxUnpacker = twbxUnpacker;
    }

    public TableauWorkbook read(File inputFile) throws Exception {
        String fileName = inputFile.getName().toLowerCase();

        if (!inputFile.exists()) {
            throw new Exception("File not found: " +
                    inputFile.getAbsolutePath());
        }

        if (fileName.endsWith(".twb")) {
            return twbParser.parse(inputFile);

        } else if (fileName.endsWith(".twbx")) {
            File extractedTwb = null;
            try {
                extractedTwb = twbxUnpacker.unpack(inputFile);
                return twbParser.parse(extractedTwb);
            } finally {
                twbxUnpacker.cleanup(extractedTwb);
            }

        } else if (fileName.endsWith(".tds")) {
            return parseTDS(inputFile);

        } else {
            throw new Exception(
                    "Unsupported file type. Please upload .twb, .twbx or .tds file");
        }
    }

    private TableauWorkbook parseTDS(File tdsFile) throws Exception {
        TableauWorkbook workbook = twbParser.parse(tdsFile);
        workbook.setName(tdsFile.getName());
        return workbook;
    }
}