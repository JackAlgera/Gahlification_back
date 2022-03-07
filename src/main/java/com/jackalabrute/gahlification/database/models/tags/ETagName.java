package com.jackalabrute.gahlification.database.models.tags;

public enum ETagName {
    ADMIN("Admin"),
    URGENT("Urgent"),
    GAHLOU("Gahlou"),
    FLOKKIE("Flokkie");

    public final String label;

    ETagName(String label) {
        this.label = label;
    }

    public static ETagName valueOfLabel(String label) {
        for (ETagName e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
