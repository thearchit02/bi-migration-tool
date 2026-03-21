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
public class TableauFilter {

    private String fieldName;
    private String filterType;
    private List<String> includeValues;
    private List<String> excludeValues;
    private String condition;
    private String dataSourceName;
}