package com.ministore.pointofsale.utils;

import com.ministore.pointofsale.annotations.ExcelColumn;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ExcelUtils {

    public static void export(Class clazz, List list, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(clazz.getSimpleName());

        Field[] fields = clazz.getDeclaredFields();

        int rowIndex = 0;
        Row row = sheet.createRow(rowIndex);

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeight(14);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        for(Field field : fields) {
            ExcelColumn excelColumnAnnotation = field.getAnnotation(ExcelColumn.class);

            if(excelColumnAnnotation == null) {
                continue;
            }

            createCell(sheet, row, excelColumnAnnotation.order(), excelColumnAnnotation.name(), headerStyle);
        }

        CellStyle bodyStyle = workbook.createCellStyle();
        XSSFFont bodyFont = workbook.createFont();
        bodyFont.setFontHeight(12);
        bodyStyle.setFont(bodyFont);

        for(Object element : list) {
            rowIndex++;
            row = sheet.createRow(rowIndex);

            for(Field field : fields) {
                ExcelColumn excelColumnAnnotation = field.getAnnotation(ExcelColumn.class);

                if(excelColumnAnnotation == null) {
                    continue;
                }

                field.setAccessible(true);

                try {
                    createCell(sheet, row, excelColumnAnnotation.order(), field.get(element), bodyStyle);
                } catch (Exception ignored) { }

            }
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    private static void createCell(XSSFSheet sheet, Row row, int columnIndex, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnIndex);
        Cell cell = row.createCell(columnIndex);
        if(value != null) {
            cell.setCellValue(String.valueOf(value));
            cell.setCellStyle(style);
        }
    }

}
