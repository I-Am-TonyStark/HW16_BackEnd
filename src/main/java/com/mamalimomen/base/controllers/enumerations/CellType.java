package com.mamalimomen.base.controllers.enumerations;

public enum CellType {

    /**
     * Numeric cell type (whole numbers, fractional numbers, dates)
     */
    NUMERIC(0),

    /**
     * String (text) cell type
     */
    STRING(1),

    /**
     * Boolean cell type
     */
    BOOLEAN(2);

    private final int code;

    CellType(int code) {
        this.code = code;
    }

    /**
     * @deprecated Used to transition code from <code>int</code>s to <code>CellType</code>s.
     */
    public static CellType forInt(int code) {
        for (CellType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CellType code: " + code);
    }

    public int getCode() {
        return code;
    }
}
