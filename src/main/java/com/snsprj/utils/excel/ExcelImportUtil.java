package com.snsprj.utils.excel;

import com.snsprj.utils.excel.annotation.ExcelSheet;
import com.snsprj.utils.excel.util.FieldReflectionUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

@Slf4j
public class ExcelImportUtil {

    /**
     * 从Workbook导入Excel文件，并封装成对象
     *
     * @return List<Object>
     */
    public static List<Object> importExcel(Workbook workbook, Class<?> sheetClass) {
        List<Object> sheetDataList = importSheet(workbook, sheetClass);
        return sheetDataList;
    }

    public static List<Object> importSheet(Workbook workbook, Class<?> sheetClass) {
        try {
            // sheet
            ExcelSheet excelSheet = sheetClass.getAnnotation(ExcelSheet.class);
            String sheetName =
                (excelSheet != null && excelSheet.name() != null && excelSheet.name().trim().length() > 0) ? excelSheet
                    .name().trim() : sheetClass.getSimpleName();

            // sheet field
            List<Field> fields = new ArrayList<Field>();
            if (sheetClass.getDeclaredFields() != null && sheetClass.getDeclaredFields().length > 0) {
                for (Field field : sheetClass.getDeclaredFields()) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    fields.add(field);
                }
            }

            if (fields == null || fields.size() == 0) {
                throw new RuntimeException(">>>>>>>>>>> xxl-excel error, data field can not be empty.");
            }

            // sheet data
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                return null;
            }

            Iterator<Row> sheetIterator = sheet.rowIterator();
            int rowIndex = 0;
            List<Object> dataList = new ArrayList<Object>();
            while (sheetIterator.hasNext()) {
                Row rowX = sheetIterator.next();
                if (rowIndex > 0) {
                    Object rowObj = sheetClass.newInstance();
                    for (int i = 0; i < fields.size(); i++) {

                        // cell
                        Cell cell = rowX.getCell(i);
                        if (cell == null) {
                            continue;
                        }

                        // call val str
                        cell.setCellType(CellType.STRING);
                        String fieldValueStr = cell.getStringCellValue();       // cell.getCellTypeEnum()

                        // java val
                        Field field = fields.get(i);
                        Object fieldValue = FieldReflectionUtil.parseValue(field, fieldValueStr);
                        if (fieldValue == null) {
                            continue;
                        }

                        // fill val
                        field.setAccessible(true);
                        field.set(rowObj, fieldValue);
                    }
                    dataList.add(rowObj);
                }
                rowIndex++;
            }
            return dataList;
        } catch (IllegalAccessException | InstantiationException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 导入Excel文件，并封装成对象
     *
     * @return List<Object>
     */
    public static List<Object> importExcel(File excelFile, Class<?> sheetClass) {
        try {
            Workbook workbook = WorkbookFactory.create(excelFile);
            return importExcel(workbook, sheetClass);
        } catch (IOException | InvalidFormatException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 从文件路径导入Excel文件，并封装成对象
     *
     * @return List<Object>
     */
    public static List<Object> importExcel(String filePath, Class<?> sheetClass) {
        File excelFile = new File(filePath);
        return importExcel(excelFile, sheetClass);
    }

    /**
     * 导入Excel数据流，并封装成对象
     *
     * @return List<Object>
     */
    public static List<Object> importExcel(InputStream inputStream, Class<?> sheetClass) {
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            return importExcel(workbook, sheetClass);
        } catch (IOException | InvalidFormatException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
