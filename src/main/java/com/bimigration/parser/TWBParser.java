package com.bimigration.parser;

import com.bimigration.model.tableau.*;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class TWBParser {

    public TableauWorkbook parse(File twbFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(twbFile);
        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();
        String version = root.getAttribute("version");

        TableauWorkbook workbook = TableauWorkbook.builder()
                .name(twbFile.getName())
                .version(version)
                .sourceFile(twbFile.getAbsolutePath())
                .build();

        NodeList datasourceNodes = root.getElementsByTagName("datasource");
        for (int i = 0; i < datasourceNodes.getLength(); i++) {
            Element dsElement = (Element) datasourceNodes.item(i);
            TableauDatasource datasource = parseDatasource(dsElement);
            if (datasource != null) {
                workbook.getDatasources().add(datasource);
            }
        }

        NodeList worksheetNodes = root.getElementsByTagName("worksheet");
        for (int i = 0; i < worksheetNodes.getLength(); i++) {
            Element wsElement = (Element) worksheetNodes.item(i);
            TableauWorksheet worksheet = parseWorksheet(wsElement);
            if (worksheet != null) {
                workbook.getWorksheets().add(worksheet);
            }
        }

        return workbook;
    }

    private TableauDatasource parseDatasource(Element dsElement) {
        String name = dsElement.getAttribute("name");
        if (name == null || name.isEmpty())
            return null;

        TableauDatasource datasource = TableauDatasource.builder()
                .name(name)
                .caption(dsElement.getAttribute("caption"))
                .build();

        NodeList connectionNodes = dsElement.getElementsByTagName("connection");
        if (connectionNodes.getLength() > 0) {
            Element connElement = (Element) connectionNodes.item(0);
            datasource.setConnectionType(connElement.getAttribute("class"));
            datasource.setServerAddress(connElement.getAttribute("server"));
            datasource.setDatabaseName(connElement.getAttribute("dbname"));
        }

        NodeList columnNodes = dsElement.getElementsByTagName("column");
        for (int i = 0; i < columnNodes.getLength(); i++) {
            Element colElement = (Element) columnNodes.item(i);
            TableauField field = parseField(colElement, name);
            if (field != null) {
                datasource.getFields().add(field);
            }
        }

        return datasource;
    }

    private TableauField parseField(Element colElement, String datasourceName) {
        String name = colElement.getAttribute("name");
        if (name == null || name.isEmpty())
            return null;

        String dataTypeStr = colElement.getAttribute("datatype");
        String roleStr = colElement.getAttribute("role");
        String hidden = colElement.getAttribute("hidden");

        TableauField field = TableauField.builder()
                .name(name)
                .caption(colElement.getAttribute("caption"))
                .dataType(mapDataType(dataTypeStr))
                .role(mapRole(roleStr))
                .isHidden("true".equals(hidden))
                .dataSourceName(datasourceName)
                .build();

        NodeList calcNodes = colElement.getElementsByTagName("calculation");
        if (calcNodes.getLength() > 0) {
            Element calcElement = (Element) calcNodes.item(0);
            String formula = calcElement.getAttribute("formula");
            field.setCalculated(true);
            field.setCalculationFormula(formula);
            field.detectFormulaType();
        }

        return field;
    }

    private TableauWorksheet parseWorksheet(Element wsElement) {
        String name = wsElement.getAttribute("name");
        if (name == null || name.isEmpty())
            return null;

        TableauWorksheet worksheet = TableauWorksheet.builder()
                .name(name)
                .build();

        NodeList paneNodes = wsElement.getElementsByTagName("pane");
        if (paneNodes.getLength() > 0) {
            Element paneElement = (Element) paneNodes.item(0);
            String markType = paneElement.getAttribute("mark-type");
            worksheet.setMarkType(markType);
        }

        return worksheet;
    }

    private TableauDataType mapDataType(String dataType) {
        if (dataType == null)
            return TableauDataType.UNKNOWN;
        return switch (dataType.toLowerCase()) {
            case "string" -> TableauDataType.STRING;
            case "integer" -> TableauDataType.INTEGER;
            case "real" -> TableauDataType.FLOAT;
            case "boolean" -> TableauDataType.BOOLEAN;
            case "date" -> TableauDataType.DATE;
            case "datetime" -> TableauDataType.DATETIME;
            default -> TableauDataType.UNKNOWN;
        };
    }

    private TableauFieldRole mapRole(String role) {
        if (role == null)
            return TableauFieldRole.UNKNOWN;
        return switch (role.toLowerCase()) {
            case "dimension" -> TableauFieldRole.DIMENSION;
            case "measure" -> TableauFieldRole.MEASURE;
            default -> TableauFieldRole.UNKNOWN;
        };
    }
}