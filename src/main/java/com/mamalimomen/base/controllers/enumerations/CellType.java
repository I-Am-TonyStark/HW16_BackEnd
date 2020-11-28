package com.mamalimomen.base.controllers.enumerations;

public enum CellType {

    /**
     * String cell type (text).
     */
    STRING(0),

    /**
     * Boolean cell type.
     */
    BOOLEAN(1),

    /**
     * Long cell type.
     */
    LONG(2),

    /**
     * Double cell type.
     */
    DOUBLE(3),

    /**
     * Date cell type.
     */
    DATE(4);

    private final int code;

    CellType(int code) {
        this.code = code;
    }

    /**
     * @deprecated Used to transition code from <code>int</code>s to <code>CellType</code>s.
     */
    public static CellType forInt(int code) {
        for (CellType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CellType code: " + code);
    }

    public int getCode() {
        return code;
    }
}
