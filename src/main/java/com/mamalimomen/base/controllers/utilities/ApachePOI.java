package com.mamalimomen.base.controllers.utilities;

import com.mamalimomen.base.controllers.annotations.Column;
import com.mamalimomen.base.domains.BaseEntity;
import com.mamalimomen.domains.Person;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ApachePOI {

    private ApachePOI() {
    }

    public static synchronized File createZipFile(String directory, String zipFileName) throws IOException {
        File file = new File(directory + zipFileName);
        byte[] buffer = new byte[2048];

        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            String[] filesList = new File(directory).list();

            for (String fileName : Objects.requireNonNull(filesList)) {
                if (!fileName.equals(zipFileName)) {
                    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(directory, fileName)))) {

                        ZipEntry entry = new ZipEntry(fileName);
                        zos.putNextEntry(entry);

                        int count;
                        while ((count = bis.read(buffer)) != -1) {
                            zos.write(buffer, 0, count);
                        }
                    }
                }
            }
        }
        return file;
    }

    public static synchronized File writeNewWordFile(String directory, String wordFileName, String text) throws IOException {
        File file = new File(directory + wordFileName);

        // Blank Document
        XWPFDocument document = new XWPFDocument();

        // Write the Document in file system
        FileOutputStream out = new FileOutputStream(file);

        // Blank Paragraph
        XWPFParagraph paragraph = document.createParagraph();

        // Write text into Paragraph
        XWPFRun run = paragraph.createRun();
        run.setText(text);

        document.write(out);
        out.close();

        return file;
    }

    @SafeVarargs
    public static synchronized <T extends BaseEntity> File writeNewExcelFile(String directory, String excelFileName, T... entities) throws IOException, IllegalAccessException {
        File excelFile = new File(directory + excelFileName + ".xlsx");

        // Create blank workbook instance for XLSX file
        try (XSSFWorkbook excelWorkBook = new XSSFWorkbook(); FileOutputStream fileOutputStream = new FileOutputStream(excelFile)) {

            // Return sheet from the XLSX workbook
            XSSFSheet excelSheet = excelWorkBook.createSheet();

            // get the last row number to append new data
            int rowNum = excelSheet.getLastRowNum();

            Field[] declaredFields = entities[0].getClass().getDeclaredFields();

            // write head cells
            Row headRow = excelSheet.createRow(rowNum++);
            for (Field field : declaredFields) {

                boolean access = field.isAccessible();
                if (!access) {
                    field.setAccessible(true);
                }

                Column annotation = field.getAnnotation(Column.class);
                Cell cell = headRow.createCell(annotation.index());

                cell.setCellValue(annotation.name());

                field.setAccessible(field.isAccessible() && access);
            }

            // write entity cells
            for (T entity : entities) {
                if (entity != null) {

                    // Creating new Row in existing XLSX sheet
                    Row row = excelSheet.createRow(rowNum++);
                    for (Field field : declaredFields) {

                        boolean access = field.isAccessible();
                        if (!access) {
                            field.setAccessible(true);
                        }

                        Column annotation = field.getAnnotation(Column.class);
                        Cell cell = row.createCell(annotation.index());

                        switch (annotation.type()) {
                            case DATE -> cell.setCellValue((Date) field.get(entity));
                            case DOUBLE -> cell.setCellValue((Double) field.get(entity));
                            case LONG -> cell.setCellValue((Long) field.get(entity));
                            case STRING -> cell.setCellValue((String) field.get(entity));
                            case BOOLEAN -> cell.setCellValue((Boolean) field.get(entity));
                        }
                        field.setAccessible(field.isAccessible() && access);
                    }
                }
            }

            excelWorkBook.write(fileOutputStream);
        }

        return excelFile;
    }

    public static synchronized <T extends BaseEntity> List<T> readExcelFile(String filePath, Class<T> typeClass, int sheetNumber) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        File excelFile = new File(filePath);

        // Finds the workbook instance for XLSX file
        try (XSSFWorkbook excelWorkBook = new XSSFWorkbook(new FileInputStream(excelFile))) {
            List<T> list = new ArrayList<>();

            // Return sheet from the XLSX workbook
            XSSFSheet excelSheet = excelWorkBook.getSheetAt(sheetNumber);

            // Traversing over each row of XLSX file
            for (Row row : excelSheet) {
                list.add(readEntity(row, typeClass));
            }

            return list;
        }
    }

    private static <T extends BaseEntity> T readEntity(Row row, Class<T> typeClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Field[] declaredFields = typeClass.getDeclaredFields();
        T entity = typeClass.getDeclaredConstructor().newInstance();

        // For each row, iterate through each columns
        Iterator<Cell> cellIterator = row.cellIterator();

        // Traversing over each cell of row
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            int cellIndex = cell.getColumnIndex();

            for (Field field : declaredFields) {

                boolean access = field.isAccessible();
                if (!access) {
                    field.setAccessible(true);
                }

                Column annotation = field.getAnnotation(Column.class);

                if (annotation.index() == cellIndex) {
                    switch (annotation.type()) {
                        case DATE -> field.set(entity, new Date((long) cell.getNumericCellValue()));
                        case DOUBLE -> field.set(entity, cell.getNumericCellValue());
                        case LONG -> field.set(entity, (long) cell.getNumericCellValue());
                        case STRING -> field.set(entity, cell.getStringCellValue());
                        case BOOLEAN -> field.set(entity, cell.getBooleanCellValue());
                    }
                }

                field.setAccessible(field.isAccessible() && access);
            }
        }
        return entity;
    }

    public static synchronized void formatDirectory(String directory) {
        File dir = new File(directory);

        String[] fileNames = dir.list();

        for (String fileName : fileNames) {
            try {
                Files.deleteIfExists(Path.of(directory + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized <T extends BaseEntity> List<File> groupingExcelFiles(String directory, String excelFilesNameFormat, List<T> entities, T[] groupSample) {
        List<File> files = new ArrayList<>();
        int max = (int) Math.ceil(entities.size() / (groupSample.length + 0.0));
        try {
            for (int i = 1; i <= max; i++) {
                int j = 0;
                while (j < groupSample.length) {
                    groupSample[j] = entities.get(j + ((i - 1) * groupSample.length));
                    j++;
                    if ((j + ((i - 1) * groupSample.length)) >= entities.size()) {
                        break;
                    }
                }
                files.add(ApachePOI.writeNewExcelFile(directory, excelFilesNameFormat + i, groupSample));
            }
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}