package com.mamalimomen.base.controllers.utilities;

import com.mamalimomen.base.controllers.annotations.Column;
import com.mamalimomen.base.domains.BaseEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ApachePOI {
    private static final String fileExtension = ".xlsx";
    private static final String zipFileExtension = ".zip";

    private ApachePOI() {
    }

    public static synchronized File createZipFile(String zipFileName, String inputDirectory) throws IOException {
        File zipFile = new File("D:\\عزم راسخ\\جاوا مکتب\\کلاس\\46\\HW16_BackEnd\\src\\main\\webapp\\upload\\" + zipFileName + zipFileExtension);
        byte[] buffer = new byte[2048];

        try (FileOutputStream fileOut = new FileOutputStream(zipFile); ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fileOut))) {
            File inputDir = new File(inputDirectory);
            String[] filesList = inputDir.list();

            FileInputStream fileIn;
            BufferedInputStream bis;

            for (String fileName : Objects.requireNonNull(filesList)) {
                fileIn = new FileInputStream(new File(inputDirectory, fileName));
                bis = new BufferedInputStream(fileIn);

                ZipEntry entry = new ZipEntry(fileName);
                zos.putNextEntry(entry);

                int count;
                while ((count = bis.read(buffer)) != -1) {
                    zos.write(buffer, 0, count);
                }
                bis.close();
            }
        }

        return zipFile;
    }

    @SafeVarargs
    public static synchronized <T extends BaseEntity> File writeNewExcelFile(String filePath, T... entities) throws IOException, IllegalAccessException {
        File excelFile = new File(filePath + fileExtension);

        // Create blank the workbook instance for XLSX file
        try (XSSFWorkbook excelWorkBook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(excelFile)) {

            // Return sheet from the XLSX workbook
            XSSFSheet excelSheet = excelWorkBook.createSheet();

            // get the last row number to append new data
            int rowNum = excelSheet.getLastRowNum();

            for (T entity : entities) {

                // Creating a new Row in existing XLSX sheet
                Row row = excelSheet.createRow(rowNum++);
                for (Field field : entity.getClass().getDeclaredFields()) {
                    boolean access = field.isAccessible();
                    if (!access) {
                        field.setAccessible(true);
                    }
                    Column annotation = field.getAnnotation(Column.class);
                    Cell cell = row.createCell(annotation.index());

                    switch (annotation.type()) {
                        case NUMERIC -> cell.setCellValue((Long) field.get(entity));
                        case STRING -> cell.setCellValue((String) field.get(entity));
                        case BOOLEAN -> cell.setCellValue((Boolean) field.get(entity));
                    }
                    field.setAccessible(field.isAccessible() && access);
                }
            }

            excelWorkBook.write(fileOut);
        }

        return excelFile;
    }

    public static synchronized <T extends BaseEntity> List<T> readExcelFile(String filePath, Class<T> typeClass, int sheetNumber) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        File excelFile = new File(filePath);

        // Finds the workbook instance for XLSX file
        try (FileInputStream fileIn = new FileInputStream(excelFile); XSSFWorkbook excelWorkBook = new XSSFWorkbook(fileIn)) {
            List<T> list = new ArrayList<>();

            // Return sheet from the XLSX workbook
            XSSFSheet excelSheet = excelWorkBook.getSheetAt(sheetNumber);

            // Traversing over each row of XLSX file
            for (Row row : excelSheet) {
                list.add(setEntity(row, typeClass));
            }

            return list;
        }
    }

    private static <T extends BaseEntity> T setEntity(Row row, Class<T> typeClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Field[] declaredFields = typeClass.getDeclaredFields();
        T entity = typeClass.getDeclaredConstructor().newInstance();

        // For each row, iterate through each columns
        Iterator<Cell> cellIterator = row.cellIterator();

        // Traversing over each cell of row
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            for (Field field : declaredFields) {
                boolean access = field.isAccessible();
                if (!access) {
                    field.setAccessible(true);
                }
                Column annotation = field.getAnnotation(Column.class);
                if (annotation.index() == cell.getColumnIndex()) {

                    switch (annotation.type()) {
                        case NUMERIC -> field.set(entity, (long) cell.getNumericCellValue());
                        case STRING -> field.set(entity, cell.getStringCellValue());
                        case BOOLEAN -> field.set(entity, cell.getBooleanCellValue());
                    }
                }
                field.setAccessible(field.isAccessible() && access);
            }
        }
        return entity;
    }
}