package com.bimigration.model.tableau;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableauParameter {

    private String name;
    private String caption;
    private TableauDataType dataType;
    private String defaultValue;
    private String currentValue;
    private List<String> allowableValues;
    private String minValue;
    private String maxValue;
    private String stepSize;
}