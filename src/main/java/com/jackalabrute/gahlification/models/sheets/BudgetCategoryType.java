package com.jackalabrute.gahlification.models.sheets;

public enum BudgetCategoryType {
    COURSES("Courses", new CellPosition("D", 4)),
    LOGEMENT("Logement", new CellPosition("H", 4)),
    PROJETS("Projets", new CellPosition("L", 4)),
    VOITURE("Voiture", new CellPosition("P", 4)),
    EPARGNES_VACANCES("Epargne vacances", new CellPosition("T", 4)),

    LOISIRS("Loisirs", new CellPosition("D", 14)),
    MATERIAL_FUN("Material Fun", new CellPosition("H", 14)),
    NEWT("Newt", new CellPosition("L", 14)),
    DEPENSES_ANTICIPES("Depenses anticipés", new CellPosition("P", 14)),
    EPARGNE_PROJETS_DE_VIE("Epargne projets de vie", new CellPosition("T", 14)),

    GAHLOU("Gahlou", new CellPosition("Z", 5)),
    FLOKKIE("Flokkie", new CellPosition("Z", 15));

    public String value;
    public CellPosition startCellCostPosition;

    BudgetCategoryType(String value, CellPosition startCellCostPosition) {
        this.value = value;
        this.startCellCostPosition = startCellCostPosition;
    }

    public static BudgetCategoryType getValue(String value) {
        for (BudgetCategoryType category: values()) {
            if (category.value.equalsIgnoreCase(value)) {
                return category;
            }
        }

        return null;
    }

    public static boolean containsValue(String value) {
        for (BudgetCategoryType category: values()) {
            if (category.value.equalsIgnoreCase(value)) {
                return true;
            }
        }

        return false;
    }
}
