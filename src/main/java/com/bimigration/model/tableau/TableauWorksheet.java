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
public class TableauWorksheet {

    private String name;
    private String markType;

    @Builder.Default
    private List<String> rowFields = new ArrayList<>();

    @Builder.Default
    private List<String> columnFields = new ArrayList<>();

    @Builder.Default
    private List<String> filterFields = new ArrayList<>();

    @Builder.Default
    private List<String> measureFields = new ArrayList<>();
}