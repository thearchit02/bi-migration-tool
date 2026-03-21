package com.bimigration.model.tableau;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableauJoin {

    private String leftTable;
    private String rightTable;
    private String leftField;
    private String rightField;
    private TableauJoinType joinType;
}