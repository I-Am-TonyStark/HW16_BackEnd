package com.mamalimomen.base.controllers.annotations;

import com.mamalimomen.base.controllers.enumerations.CellType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * @return Position of the column. Starts with the 0 index.
     */
    int index();

    /**
     * @return inspected datatype of the column in the excelWorkbook - matched to
     */
    CellType type();
}
