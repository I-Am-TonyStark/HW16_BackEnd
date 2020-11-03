package com.mamalimomen.base.controllers.utilities;

public enum PersistenceUnits {
    UNIT_ONE("ogm-mongodb");

    private final String unitName;

    PersistenceUnits(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return this.unitName;
    }
}
