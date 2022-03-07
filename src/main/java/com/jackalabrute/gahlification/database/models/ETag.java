package com.jackalabrute.gahlification.database.models;

public enum ETag {
    ADMIN("Admin"),
    URGENT("Urgent"),
    GAHLOU("Gahlou"),
    FLOKKIE("Flokkie");

    public final String label;

    ETag(String label) {
        this.label = label;
    }

    public static ETag valueOfLabel(String label) {
        for (ETag e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
