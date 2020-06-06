package com.github.shootercheng.parse.parse;

import com.github.shootercheng.common.util.DataUtil;
import com.github.shootercheng.common.util.ExcelUtil;
import com.github.shootercheng.parse.constant.MapperType;
import com.github.shootercheng.parse.exception.FileParseException;
import com.github.shootercheng.parse.param.ParseParam;
import com.github.shootercheng.parse.utils.FileParseCommonUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chengdu
 *
 */
public class ExcelFileParse implements FileParse {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelFileParse.class);

    private ExcelFileParse() {
    }

    private static class ExcelFileParseHolder {
        private static final ExcelFileParse parser = new ExcelFileParse();
    }

    public static ExcelFileParse instance() {
        return ExcelFileParseHolder.parser;
    }

    @Override
    public <T> List<T> parseFile(String filePath, Class<T> clazz, ParseParam parseParam) {
        checkParam(parseParam);
        Workbook workbook = null;
        List<T> resultList = new ArrayList<>();
        try {
            workbook = ExcelUtil.getWorkBook(filePath);
            Sheet sheet = workbook.getSheetAt(parseParam.getSheetNum());
            addSheetResultList(sheet, clazz, parseParam, resultList);
        } catch (Exception e) {
            LOGGER.error("parse excel error {}", e.getMessage());
            throw new FileParseException("parse excel error " + filePath, e);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultList;
    }

    private <T> void addSheetResultList(Sheet sheet, Class<T> clazz, ParseParam parseParam, List<T> resultList) {
        int headLine = parseParam.getHeadLine();
        int startLine = parseParam.getStartLine();
        int i = 0;
        for (Row row : sheet) {
            if ( parseParam.getMapperType() == MapperType.HEAD && i == headLine ) {
                Map<Integer, String> headMap = getHeadMap(row);
                FileParseCommonUtil.buildParseParam(clazz, parseParam, headMap);
            } else if (i >= startLine) {
                T t = convertRowToVo(clazz, row, parseParam);
                if (t != null) {
                    resultList.add(t);
                } else {
                    parseParam.getErrorRecord()
                            .writeErrorMsg("line " + i + ":" + row +
                                    "covert to vo null");
                }
            }
            i++;
        }
    }

    private Map<Integer, String> getHeadMap(Row row) {
        Map<Integer, String> headMap = new HashMap<>();
        row.forEach(cell -> {
            int columnIndex = cell.getColumnIndex();
            String cellValue = ExcelUtil.getCellValue(cell);
            headMap.put(columnIndex, cellValue);
        });
        return headMap;
    }

    @Override
    public <T> Map<Integer, List<T>> parseFileSheets(String filePath, Class<T> clazz, Map<Integer, ParseParam> parseParamMap) {
        // 解析多个 sheet 页
        Workbook workbook = null;
        Map<Integer, List<T>> resultMap = new HashMap<>(16);
        try {
            workbook = ExcelUtil.getWorkBook(filePath);
            Set<Map.Entry<Integer, ParseParam>> entrySet = parseParamMap.entrySet();
            for (Map.Entry<Integer, ParseParam> entry : entrySet) {
                Integer sheetNum = entry.getKey();
                ParseParam parseParam = entry.getValue();
                List<T> sheetResultList = new ArrayList<>();
                Sheet sheet = workbook.getSheetAt(sheetNum);
                addSheetResultList(sheet, clazz, parseParam, sheetResultList);
                resultMap.put(sheetNum, sheetResultList);
            }
        } catch (Exception e) {
            LOGGER.error("parse excel error {}", e.getMessage());
            throw new FileParseException("parse excel error " + filePath, e);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultMap;
    }

    private  <T> T convertRowToVo(Class<T> clazz, Row row, ParseParam parseParam) {
        T t = null;
        try {
            t = clazz.newInstance();
            Map<String, Method> fieldSetterMap = parseParam.getFieldSetterMap();
            for (Map.Entry<String, Method> entry : fieldSetterMap.entrySet()) {
                Integer column = DataUtil.EXCEL_COLUMN.get(entry.getKey());
                String cellValue = ExcelUtil.getCellValue(row, column);
                if (parseParam.getCellFormat() != null) {
                    cellValue = parseParam.getCellFormat().format(entry.getKey(), cellValue);
                }
                FileParseCommonUtil.invokeValue(t, entry.getValue(), cellValue);
            }
            if (parseParam.getBusinessDefineParse() != null) {
                parseParam.getBusinessDefineParse().defineParse(t, row, parseParam);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
