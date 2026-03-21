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
public class TableauWorkbook {

    private String name;
    private String version;
    private String sourceFile;

    @Builder.Default
    private List<TableauDatasource> datasources = new ArrayList<>();

    @Builder.Default
    private List<TableauWorksheet> worksheets = new ArrayList<>();

    public int getTotalFields() {
        return datasources.stream()
                .mapToInt(ds -> ds.getFields().size())
                .sum();
    }

    public int getTotalCalculatedFields() {
        return datasources.stream()
                .mapToInt(ds -> ds.getCalculatedFields().size())
                .sum();
    }

    public List<TableauField> getAllCalculatedFields() {
        return datasources.stream()
                .flatMap(ds -> ds.getCalculatedFields().stream())
                .toList();
    }
}