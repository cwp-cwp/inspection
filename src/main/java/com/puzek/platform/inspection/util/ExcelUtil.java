package com.puzek.platform.inspection.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import puzekcommon.utils.ObjUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {
    private final static Logger LOG = LoggerFactory.getLogger(ExcelUtil.class);
    private final static String OFFICE_EXCEL_2003_POSTFIX = "xls";
    private final static String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

    public static List<List<String>> readRowsExcel(String path, int[] columns) {
        Sheet sheet = getSheet(path);
        if (sheet == null) {
            return null;
        }
        List<List<String>> excelData = new ArrayList<>();
        // 循环每一行
        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            // 通过下标，获取每行的数据
            Row row = sheet.getRow(rowNum);
            // 如果获取的行是空，返回空list
            if (row == null) {
                excelData.add(new ArrayList<String>());
                continue;
            }
            // 循环每一行的每个单元格
            excelData.add(columns == null ? getRowList(row) : getRowList(row, columns));
        }
        return excelData;
    }

    private static Sheet getSheet(String path) {
        // 获取xssfWorkbook
        Workbook workbook = getWorkBook(path);
        if (workbook == null)
            return null;
        // 默认获取第一张工作簿xssfSheet
        return workbook.getSheetAt(0);
    }

    /*
    * 工作流程
    * 1.先获取文件的后缀，即不同版本的excel
    * 2.根据不同版本，调用相应的方法
    * 3.最后返回工作簿
    * */
    private static Workbook getWorkBook(String path) {
        String postfix = path.substring(path.lastIndexOf(".") + 1);
        FileInputStream is = null;
        try {
            is = new FileInputStream(path);
            switch (postfix) {
                case OFFICE_EXCEL_2003_POSTFIX:
                    return new HSSFWorkbook(is);
                case OFFICE_EXCEL_2010_POSTFIX:
                    return new XSSFWorkbook(is);
                default:
                    LOG.warn("excel path:" + path + " postfix:" + postfix);
                    break;
            }
        } catch (IOException e) {
            LOG.warn("Error occur in ExcelUtil.getWorkBook() ：", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                LOG.warn("ExcelUtil.getWorkBook() io close error ：", e);
            }
        }
        return null;
    }

    private static List<String> getRowList(Row row) {
        List<String> rowList = new ArrayList<>();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            rowList.add(getStringCellValue(cell));
        }
        return rowList;
    }

    private static List<String> getRowList(Row row, int[] Columns) {
        List<String> rowList = new ArrayList<>();
        for (int Column : Columns) {
            Cell cell = row.getCell(Column);
            rowList.add(getStringCellValue(cell));
        }
        return rowList;
    }

    private static String getStringCellValue(Cell cell) {
        if (cell == null)
            return "";
        String strCell;
        switch (cell.getCellTypeEnum()) {
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            // 当cell格式为数值类型的时候，才有可能是日期类型
            case NUMERIC:
                strCell = excelConvertStr(cell);
                break;
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        return strCell;
    }

    private static String excelConvertStr(Cell cell) {
        // 当cell的格式为日期格式的时候，设置excel单元格格式，日期格式判断，如果cellStyle为14,31,57,58代表是日期类型
        short format = cell.getCellStyle().getDataFormat();
        if (format == 14 || format == 31 || format == 57 || format == 58) {
            Date d = cell.getDateCellValue();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            return sf.format(d).trim();
        }
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(cell);
    }

    public static String excelErrorMessages(int row, int column, int[] array) {
        return "Excel第" + (row + 1) + "行," + "第" + (array[column] + 1) + "列数据错误";
    }

    public static String excelTitleJudge(List<String> data, String[] title) {
        if (data.size() != title.length)
            return "表头格式错误，请检查表头";
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).replaceAll(" ", "").equals(title[i].replaceAll(" ", "")))
                return String.format("第%d列表头有误，正确的表头名应为%s", i + 1, title[i]);
        }
        return null;
    }

    /**
     * @param path      路径，绝对路径(不允许为空)
     * @param sheet     工作簿名称
     * @param title     excel表头(和titles是相对的关系，只能存在一个,不存在传null)
     * @param titles    excel多重表头(和title是相对的关系，只能存在一个,不存在传null)
     * @param excelInfo excel数据
     * @return true 表示成功 false表示失败
     */
    public static String writeExcel(String path, String sheet, String[] title, List<List<String>> titles, List<List<String>> excelInfo) {
        if (ObjUtil.isEmpty(path)) {
            LOG.warn("writeExcel path is null");
            return null;
        } else if (excelInfo == null && title == null) {
            LOG.warn("writeExcel excelInfo is null");
            return null;
        } else {
            String absolutePath = makeExcelFile(sheet, path);
            if (absolutePath == null) {
                return null;
            }
            boolean excelRet = writeExcelXlsx2(absolutePath, sheet, title, titles, excelInfo);
            return !excelRet ? null : absolutePath;
        }
    }

    private static String makeExcelFile(String type, String path) {
        String realSavePath = makePath(path);
        if (realSavePath == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        StringBuffer name = (new StringBuffer()).append(type).append("_").append(date).append(".xlsx");
        StringBuffer filePath = (new StringBuffer(realSavePath)).append("/").append(name);
        return filePath.toString();
    }

    private static String makePath(String path) {
        String savePath = null;
        try {
            savePath = ResourceUtils.getFile("classpath:../../").getPath();
        } catch (FileNotFoundException e) {
            LOG.error("error occur in ExcelUtil.makePath ResourceUtils.getFile：", e);
            return savePath;
        }
        savePath += File.separator + path;
        // File既可以代表文件也可以代表目录
        File file = new File(savePath);
        // 如果目录不存在
        if (!file.exists()) {
            // 创建目录
            file.mkdirs();
        }
        return savePath;
    }

    private static boolean writeExcelXlsx2(String path, String sheetType, String[] title,
                                           List<List<String>> titles, List<List<String>> excelInfo) {
        // 创建工作区
        XSSFWorkbook wb = new XSSFWorkbook();
        // 写入工作簿名称， 并获取sheet
        Sheet sheet = wb.createSheet(sheetType == null ? "" : sheetType);
        // 设置单元格边框
        CellStyle setBorder = createCellStyle(wb);
        // 写表头,获取一共写了多少列
        int row = writeExcelTitle(sheet, setBorder, title, titles);
        for (int i = 0; i < excelInfo.size(); i++) {
            Row excelRow = sheet.createRow(i + row);
            for (int j = 0; j < excelInfo.get(i).size(); j++) {
                Cell cell = excelRow.createCell(j);
                cell.setCellValue(excelInfo.get(i).get(j));
                cell.setCellStyle(setBorder);
            }
        }
        int columnSize;
        if (title != null) {
            columnSize = title.length;
        } else {
            int max = 0;
            for (List<String> list : titles) {
                if (list.size() > max) {
                    max = list.size();
                }
            }
            columnSize = max;
        }
        for (int i = 0; i < columnSize; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (int) (sheet.getColumnWidth(i) * 1.5));
        }

        // 写数据，并获取最长的单元格数据
        int[] titleLength = readTitleLength(title, titles);
        int[] cellLength = writeExcelData(sheet, setBorder, row, excelInfo, titleLength);
        // 设置列的宽度
        for (int i = 0; i < cellLength.length; i++) {
            // 长度乘以2是为了解决纯数字列宽度不足会显示科学计数法问题，乘以256得到的数据才是excel真实列宽。
            sheet.setColumnWidth(i, (int) (cellLength[i] * 1.5 * 256));
        }
        return writeExcelIO(path, wb);
    }

    private static int[] readTitleLength(String[] title, List<List<String>> titles) {
        int[] titleLength;
        if (title != null) {
            titleLength = new int[title.length];
            for (int i = 0; i < title.length; i++) {
                titleLength[i] = title[i].getBytes().length;
            }
            return titleLength;
        } else {
            int maxLength = 0;// 找出最长的行
            for (List<String> title1 : titles) {
                if (title1.size() > maxLength) {
                    maxLength = title1.size();
                }
            }
            titleLength = new int[maxLength];
            for (List<String> title1 : titles) {
                for (int j = 0; j < title1.size(); j++) {
                    if (title1.get(j).getBytes().length > titleLength[j]) {
                        titleLength[j] = title1.get(j).getBytes().length;
                    }
                }
            }
            return titleLength;
        }
    }

    // 写excel数据，并返回每列最长数据的长度，设置表格的列宽
    private static int[] writeExcelData(Sheet sheet, CellStyle setBorder, int row, List<List<String>> excelInfo, int[] titleLength) {
        // 写数据
        for (int i = 0; i < excelInfo.size(); i++) {
            Row excelRow = sheet.createRow(i + row);
            for (int j = 0; j < excelInfo.get(i).size(); j++) {
                Cell cell = excelRow.createCell(j);
                cell.setCellValue(excelInfo.get(i).get(j));
                cell.setCellStyle(setBorder);
                try {
                    if (excelInfo.get(i).get(j) != null) {
                        if (titleLength[j] < excelInfo.get(i).get(j).getBytes().length) {
                            titleLength[j] = excelInfo.get(i).get(j).getBytes().length;
                        }
                    }
                } catch (Exception e) {
                    LOG.warn("error occur in writeExcelData", e);
                }
            }
        }
        return titleLength;
    }

    // 写excel表
    private static boolean writeExcelIO(String path, XSSFWorkbook wb) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            wb.write(os);
        } catch (IOException e) {
            LOG.warn("writeExcelIO Error" + e);
            return false;
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                // 关闭输出流
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                LOG.warn("writeExcelIO Error os.close or wb.close", e);
            }
        }
        return true;
    }

    private static CellStyle createCellStyle(XSSFWorkbook wb) {
        CellStyle setBorder = wb.createCellStyle();
        setBorder.setBorderBottom(BorderStyle.THIN); //下边框
        setBorder.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        setBorder.setBorderLeft(BorderStyle.THIN);//左边框
        setBorder.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        setBorder.setBorderTop(BorderStyle.THIN);//上边框
        setBorder.setTopBorderColor(IndexedColors.BLACK.getIndex());

        setBorder.setBorderRight(BorderStyle.THIN);//右边框
        setBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
        return setBorder;
    }

    // 根据两种表头写数据,返回写到了第几行
    private static int writeExcelTitle(Sheet sheet, CellStyle setBorder, String[] title,
                                       List<List<String>> titles) {
        if (title != null) {
            Row row = sheet.createRow(0);
            for (int i = 0; i < title.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(setBorder);
            }
            return 1;
        } else {
            for (int i = 0; i < titles.size(); i++) {
                Row row = sheet.createRow(i);
                for (int j = 0; j < titles.get(i).size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(titles.get(i).get(j));
                }
            }
            return titles.size();
        }
    }

    /**
     * 将带绝对路径的Excel文件转换成带../前缀的相对路径文件
     *
     * @param path Excel的绝对路径，要带文件名
     * @return 形如webinsight1/relativePath/excel.xlsx的相对路径
     */
    public static String getRelativePath(String path) {
        String realPath = null;
        try {
            realPath = ResourceUtils.getFile("classpath:../../").getPath();
        } catch (FileNotFoundException e) {
            LOG.error("error occur in ExcelUtil.getRelativePath ResourceUtils.getFile：", e);
            return realPath;
        }
        int rootPathIndex = realPath.lastIndexOf("/");
        return path.substring(rootPathIndex);
    }

    public static List<List<String>> readColumnExcel(String path) {
        Sheet sheet = getSheet(path);
        if (sheet == null)
            return null;
        List<List<String>> excelData = new ArrayList<>();
        // 第一列是表头，表头是基准，不会存在空的单元格
        // getPhysicalNumberOfCells 是获取不为空的列个数
        // getLastCellNum 是获取最后一个不为空的列是第几个。
        // 获取第一行一共有多少列
        int columnIndex = sheet.getRow(0).getPhysicalNumberOfCells();
        // 循环列
        for (int i = 0; i < columnIndex; i++) {
            List<String> columnData = new ArrayList<>();
            // 循环行
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                if (null == sheet.getRow(j)) {
                    columnData.add("");
                } else {
                    Cell cell = sheet.getRow(j).getCell(i);
                    columnData.add(getStringCellValue(cell));
                }
            }
            excelData.add(columnData);
        }
        return excelData;
    }
}
