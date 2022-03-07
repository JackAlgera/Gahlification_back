package com.jackalabrute.gahlification.database.models.tags;

public enum ETagType {
    TASK("TASK");

    public final String label;

    ETagType(String label) {
        this.label = label;
    }

    public static ETagType valueOfLabel(String label) {
        for (ETagType e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
