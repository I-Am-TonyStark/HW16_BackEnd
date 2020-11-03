package com.mamalimomen.base.controllers.utilities;

import com.creditdatamw.zerocell.Reader;
import com.creditdatamw.zerocell.ZeroCellException;
import com.mamalimomen.base.domains.BaseEntity;

import java.io.File;
import java.util.List;

public final class ExcelReader {
    private ExcelReader() {
    }

    public static synchronized <T extends BaseEntity> List<T> readFile(String filePath, Class<T> typeClass, int sheetNumber) throws ZeroCellException {
        return Reader.of(typeClass)
                .from(new File(filePath))
                .sheet("Sheet " + sheetNumber)
                .list();
    }

    public static synchronized <T extends BaseEntity> String[] inspectColumnNames(Class<T> typeClass) {
        return Reader.columnsOf(typeClass);
    }
}
