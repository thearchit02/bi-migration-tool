package com.bimigration.model.tableau;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableauDatasource {

    private String name;
    private String caption;
    private String connectionType;
    private String serverAddress;
    private String databaseName;
    private String schemaName;
    private String username;

    @Builder.Default
    private List<TableauField> fields = new ArrayList<>();

    @Builder.Default
    private List<TableauFilter> filters = new ArrayList<>();

    @Builder.Default
    private List<TableauParameter> parameters = new ArrayList<>();

    @Builder.Default
    private List<TableauJoin> joins = new ArrayList<>();

    public List<TableauField> getCalculatedFields() {
        return fields.stream()
                .filter(TableauField::isCalculated)
                .toList();
    }

    public List<TableauField> getDimensions() {
        return fields.stream()
                .filter(f -> f.getRole() == TableauFieldRole.DIMENSION)
                .toList();
    }

    public List<TableauField> getMeasures() {
        return fields.stream()
                .filter(f -> f.getRole() == TableauFieldRole.MEASURE)
                .toList();
    }
}