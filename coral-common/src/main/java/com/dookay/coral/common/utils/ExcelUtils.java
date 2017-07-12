package com.dookay.coral.common.utils;

import com.dookay.coral.common.json.JsonUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel工具类
 *
 * @author : houkun
 * @version : v0.0.1
 * @since : 2016/12/28
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class ExcelUtils {

    //todo 文件路径问题

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 导出Excel文件
     *
     * @param data
     * @param fileName
     */
    public static <T> File exportExcel(List<T> data, String fileName) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (StringUtils.isEmpty(fileName) || data == null) {
            return null;
        }
        File myFile = new File(fileName + ".xls");
        if (data.size() > 0) {
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            HSSFWorkbook workbook = createWorkbook(data);
            OutputStream os = new FileOutputStream(myFile);
            workbook.write(os);
            os.flush();
            os.close();
            return myFile;
        } else {
            myFile.deleteOnExit();
            return new File(fileName + ".xls");
        }
    }

    /**
     * 复制Excel - 跳过前几行
     *
     * @param file
     * @param rows
     * @return
     * @throws IOException
     */
    public static File copyExcelJumpRows(File file, int rows) throws IOException {
        if (file == null) {
            return null;
        }
        HSSFWorkbook workbookOld = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheetOld = workbookOld.getSheetAt(0);
        int jumpRows = rows > sheetOld.getLastRowNum() + 1 ? sheetOld.getLastRowNum() + 1 : rows;

        File fileNew = new File(FileUtils.getFileName(file.getPath()) + "_trim.xls");
        fileNew.createNewFile();
        HSSFWorkbook workbookNew = new HSSFWorkbook();
        HSSFSheet sheetNew = workbookNew.createSheet();
        for (int i = jumpRows; i <= sheetOld.getLastRowNum(); i++) {
            doCopy(sheetOld, sheetNew, workbookNew, i, i - jumpRows);
        }
        workbookNew.write(new FileOutputStream(fileNew));
        return fileNew;
    }


    /**
    *判断该行是否为空
     */
    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                return false;
        }
        return true;
    }

    /**
     * 导入Excel为对象列表
     *
     * @param file
     * @param dataClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> importExcel(File file, Class dataClass) throws IOException {
        if (file == null) {
            return null;
        }
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (Exception ex) {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }
        Sheet sheet = workbook.getSheetAt(0);
        // 读取表头
        Row sheetHeader = sheet.getRow(0);
        List<String> headerList = new ArrayList<>();
        for (int i = 0; i < sheetHeader.getLastCellNum(); i++) {
            String header = sheetHeader.getCell(i).getStringCellValue();
            headerList.add(org.springframework.util.StringUtils.trimAllWhitespace(header));
        }

        // 构建 Excel表头名 - 字段名 的Map
        //     Excel表头名 - Annotation 的Map
        Field[] fields = dataClass.getDeclaredFields();
        Map<String, Field> headerFieldMap = new HashMap<>();
        Map<String, ExcelColumn> headerExcelColumnMap = new HashedMap();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                headerFieldMap.put(excelColumn.name(), field);
                headerExcelColumnMap.put(excelColumn.name(), excelColumn);
            }
        }

        // 读取数据主体
        List<T> dataList = new LinkedList<>();
        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
            Row row = sheet.getRow(j);
            if(isRowEmpty(row)){
                continue;
            }
            if (row == null) continue;
            try {
                T data = (T) dataClass.newInstance();
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    String header = headerList.get(i);
                    // 对应字段
                    Field field = headerFieldMap.get(header);
                    // 对应Excel注解
                    ExcelColumn excelColumn = headerExcelColumnMap.get(header);
                    // 根据字段类型和注解类型信息，反射设置data字段值
                    if (excelColumn != null) {
                        try {
                            setDataValue(data, excelColumn, field, cell);
                        } catch (Exception e) {
                            e.printStackTrace();
                            field.set(data, null);
                        }
                    }
                }
                dataList.add(data);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    /**
     * 设置字段值
     *
     * @param data        导入模型类
     * @param excelColumn excel注解信息
     * @param field       字段
     * @param cell        excel单元格
     * @throws ParseException
     * @throws IllegalAccessException
     */
    private static void setDataValue(Object data, ExcelColumn excelColumn, Field field, Cell cell) throws ParseException, IllegalAccessException {
        field.setAccessible(true);
        if (field.getType() == Date.class) {
            // 时间
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                field.set(data, cell.getDateCellValue());
            } else {
                if (StringUtils.isEmpty(excelColumn.format())) {
                    field.set(data, DateUtils.parseDate(cell.getStringCellValue()));
                } else {
                    field.set(data, DateUtils.parseDate(cell.getStringCellValue(), excelColumn.format()));
                }
            }
        } else if (excelColumn.type().isEnum()) {
            // 枚举，excel中存储的是描述值，转化为对应int类
            Object cellValue = cell.getCellType() == Cell.CELL_TYPE_NUMERIC ? cell.getNumericCellValue() : cell.getStringCellValue();
            Integer enumValue = (Integer) EnumUtils.getValueByDescription(cellValue, excelColumn.type());
            field.set(data, enumValue);
        } else if (field.getType() == Integer.class) {
            // 整型
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                field.set(data, (int) cell.getNumericCellValue());
            } else {
                field.set(data, Integer.parseInt(cell.getStringCellValue()));
            }
        } else if (field.getType() == Long.class) {
            // 长整型
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                field.set(data, (long) cell.getNumericCellValue());
            } else {
                field.set(data, Long.parseLong(cell.getStringCellValue()));
            }
        } else if (field.getType() == Double.class) {
            // 浮点数
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                field.set(data, cell.getNumericCellValue());
            } else {
                field.set(data, Double.parseDouble(cell.getStringCellValue()));
            }
        } else if (field.getType() == String.class) {
            // 字符串
            Object cellValue = cell.getCellType() == Cell.CELL_TYPE_NUMERIC ? cell.getNumericCellValue() : cell.getStringCellValue();
            field.set(data, cellValue);
        }
    }


    /**
     * 创建工作簿
     *
     * @param data
     * @param <T>
     * @return
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> HSSFWorkbook createWorkbook(List<T> data) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        {
            createHeader(sheet, data.get(0));
            createBody(sheet, data);
        }
        return workbook;
    }

    /**
     * 创建表头
     *
     * @param sheet
     * @param dataRow
     * @param <T>
     */
    private static <T> void createHeader(HSSFSheet sheet, T dataRow) {
        Row header = sheet.createRow(0);
        Field[] fields = dataRow.getClass().getDeclaredFields();
//        Arrays.sort(fields, new Comparator<Field>() {
//            public int compare(Field o1, Field o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        });
        int cellIndex = 0;
        for (Field field : fields) {//取得DTO的导出列
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn export = field.getAnnotation(ExcelColumn.class);
                String title = export.name();
                Cell headerCell = header.createCell(cellIndex);
                headerCell.setCellValue(title);
                cellIndex++;
            }
        }
    }

    /**
     * 创建表主体
     *
     * @param sheet
     * @param data
     * @param <T>
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void createBody(HSSFSheet sheet, List<T> data) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (!data.isEmpty()) {
            int rowIndex = 1;
            for (T dataRow : data) {
                Row sheetRow = sheet.createRow(rowIndex);
                Field[] fields = dataRow.getClass().getDeclaredFields();
//                Arrays.sort(fields, new Comparator<Field>() {
//                    public int compare(Field o1, Field o2) {
//                        return o1.getName().compareTo(o2.getName());
//                    }
//                });
                int cellIndex = 0;
                for (Field field : fields) {//取得DTO的导出列
                    if (field.isAnnotationPresent(ExcelColumn.class)) {
                        field.setAccessible(true);
                        Object cellValue = (Object) field.get(dataRow);
                        Cell sheetCell = sheetRow.createCell(cellIndex);
                        if (null != cellValue) {
                            ExcelColumn export = field.getAnnotation(ExcelColumn.class);
                            String cellStr = cellValue.toString();
                            if (!export.format().equals("")) {
                                //按注解格式化
                                cellStr = String.format(export.format(), cellValue);
                            } else if (cellValue.getClass().equals(Date.class)) {
                                // 日期类型默认格式
                                cellStr = sdf.format(cellValue);
                            } else if (export.type().isEnum()) {
                                //Enum
                                cellStr = (String) EnumUtils.getDescriptionByValue(cellValue, export.type());
                            } else if (cellValue.getClass().equals(Boolean.class)) {
                                // 布尔类型
                                cellStr = (boolean) cellValue ? "是" : "否";
                            } else if (export.json()) {
                                // json
                                cellStr = JsonUtils.toJSONString(export.type().cast(cellValue));
                            }

                            sheetCell.setCellValue(cellStr);
                        }
                        cellIndex++;
                    }
                }
                rowIndex++;
            }
        }
    }

    /**
     * 复制工作表行
     *
     * @param sheetOld
     * @param sheetNew
     * @param workbook
     * @param oldRowNum
     * @param newRowNum
     */
    private static void doCopy(HSSFSheet sheetOld, HSSFSheet sheetNew, HSSFWorkbook workbook, int oldRowNum, int newRowNum) {
        HSSFRow rowOld = sheetOld.getRow(oldRowNum);
        HSSFRow rowNew = sheetNew.createRow(newRowNum);
        for (int i = 0; i < rowOld.getLastCellNum(); i++) {
            HSSFCell oldCell = rowOld.getCell(i);
            HSSFCell newCell = rowNew.createCell(i);

            if (oldCell == null) {
                continue;
            }

            // Set the cell data type
            newCell.setCellType(oldCell.getCellType());

            // Set the cell data value
            switch (oldCell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    newCell.setCellValue(oldCell.getRichStringCellValue());
                    break;
            }
        }
    }

}
