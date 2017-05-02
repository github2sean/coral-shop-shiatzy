package com.dookay.coral.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Excel工具类
 *
 * @author : houkun
 * @version : v0.0.1
 * @since : 2016/12/6
 */
public class OldExcelUtils {

//    private static String path = "/export/";

    /**
     * 导出Excel - 从旧项目中修改
     *
     * @param title 文件名
     * @param dataset 待导出的数据列表，每条记录的key为该列的表头，value为单元格的值
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static File exportExcel(String title, List<Map<String, Object>> dataset) throws IOException {
        if (StringUtils.isEmpty(title) ||
                dataset == null ||
                dataset.size() == 0) {
            return null;
        }
        File myFile = new File(title + ".xls");
        if (!myFile.exists()) {
            myFile.createNewFile();
        }

        OutputStream out = new FileOutputStream(myFile);

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);

        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);

        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式
        style.setFont(font);

        // 声明一个画图的顶级管理器
        //HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        String[] headers = dataset.get(0).keySet().toArray(new String[dataset.get(0).size()]);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        int i = 0;
        for (Map<String, Object> map : dataset) {
            i++;
            row = sheet.createRow(i);
            int j = 0;
            for (String head : headers) {
                HSSFCell cell = row.createCell((short) j);
                cell.setCellValue(String.valueOf(map.get(head)));
                j++;
            }
        }

        try {
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                out.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return myFile;
    }

}


