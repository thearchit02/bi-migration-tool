package com.bimigration.model.tableau;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableauField {

    private String name;
    private String caption;
    private TableauDataType dataType;
    private TableauFieldRole role;
    private boolean isCalculated;
    private boolean isHidden;
    private String calculationFormula;
    private TableauFormulaType formulaType;
    private String dataSourceName;

    public void detectFormulaType() {
        if (calculationFormula == null || calculationFormula.isEmpty()) {
            this.formulaType = TableauFormulaType.UNKNOWN;
            return;
        }

        String formula = calculationFormula.toUpperCase();

        if (formula.contains("FIXED")) {
            this.formulaType = TableauFormulaType.LOD_FIXED;
        } else if (formula.contains("INCLUDE")) {
            this.formulaType = TableauFormulaType.LOD_INCLUDE;
        } else if (formula.contains("EXCLUDE")) {
            this.formulaType = TableauFormulaType.LOD_EXCLUDE;
        } else if (formula.contains("RUNNING_") ||
                formula.contains("WINDOW_") ||
                formula.contains("RANK") ||
                formula.contains("INDEX()") ||
                formula.contains("FIRST()") ||
                formula.contains("LAST()")) {
            this.formulaType = TableauFormulaType.TABLE_CALC;
        } else if (formula.contains("PARAMETER")) {
            this.formulaType = TableauFormulaType.PARAMETER_REF;
        } else {
            this.formulaType = TableauFormulaType.BASIC;
        }
    }
}