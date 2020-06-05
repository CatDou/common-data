package com.github.catdou.parse;

import com.github.catdou.parse.model.ExcelTypeVo;
import com.github.catdou.parse.model.MergeDataVo;
import com.github.catdou.parse.model.ReflectVo;
import com.github.shootercheng.common.util.DataUtil;
import com.github.shootercheng.common.util.ExcelUtil;
import com.github.shootercheng.parse.constant.ParseType;
import com.github.shootercheng.parse.param.ParseParam;
import com.github.shootercheng.parse.parse.FileParse;
import com.github.shootercheng.parse.parse.FileParseCreateor;
import com.github.shootercheng.parse.utils.FileParseCommonUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author chengdu
 *
 */
public class ExcelParseTest extends ParseCommonTest {

    @Test
    public void testExcel2003() {
        String filePath = "file/test2003.xls";
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, createReflectParam());
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testExcel2007() {
        String filePath = "file/test2007.xlsx";
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, createReflectParam());
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testExcelType() {
        String filePath = "file/data-type.xlsx";
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        List<ExcelTypeVo> excelTypeVos = fileParse.parseFile(filePath, ExcelTypeVo.class, createExcelTypeParam());
        Assert.assertEquals(6, excelTypeVos.size());
    }

    @Test
    public void testColumn() {
        String name = "ZZ";
        int column = -1;
        char[] charArr = name.toCharArray();
        for (char c : charArr) {
            column = (column + 1) * 26 + c - 'A';
        }
        System.out.print(column);
//        column + 1 / 26
    }

    @Test
    public void testGenChar() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < 26; i++) {
            char curChar = (char) ('A' + i);
            stringBuilder.append("\'").append(curChar).append("\'").append(",");
        }
        System.out.println(stringBuilder.toString());
    }

    @Test
    public void testColumnMap() {
        Assert.assertTrue(DataUtil.EXCEL_COLUMN.get("A") == 0);
        Assert.assertTrue("A".equals( DataUtil.COLUMN_NUM.get(0)));
    }

    @Test
    public void testManySheet() {
        String filePath = "file/many-sheet-data.xlsx";
        // sheet1 param
        Map<String, String> fieldColumnMap = new HashMap<>(16);
        fieldColumnMap.put("A", "string");
        fieldColumnMap.put("B", "date");
        fieldColumnMap.put("C", "doubleData");
        fieldColumnMap.put("D", "utDate");
        Map<String, Method> columnMethodMap = FileParseCommonUtil.convertToColumnMethodMap(MergeDataVo.class, fieldColumnMap);
        ParseParam sheet0Param = new ParseParam().setStartLine(1)
                .setFieldSetterMap(columnMethodMap);
        Map<String, String> fieldColumnMap2 = new HashMap<>(16);
        fieldColumnMap2.put("A", "id");
        fieldColumnMap2.put("B", "userName");
        fieldColumnMap2.put("C", "score");
        fieldColumnMap2.put("D", "rdate");
        Map<String, Method> columnMethodMap2 = FileParseCommonUtil.convertToColumnMethodMap(MergeDataVo.class, fieldColumnMap2);
        ParseParam sheet1Param = new ParseParam().setStartLine(1)
                .setFieldSetterMap(columnMethodMap2);
        Map<Integer, ParseParam> map = new HashMap<>(16);
        map.put(0, sheet0Param);
        map.put(1, sheet1Param);
        FileParse fileParse = FileParseCreateor.createFileParse(ParseType.EXCEL);
        Map<Integer, List<MergeDataVo>> resultMap = fileParse.parseFileSheets(filePath, MergeDataVo.class, map);
        System.out.println(resultMap);
    }

    @Test
    public void testPoi() {
        String filePath = "file/test2003.xls";
        Workbook workbook = ExcelUtil.getWorkBook(filePath);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            int rowNum = row.getRowNum();
            System.out.println("row :" + rowNum);
            StringJoiner stringJoiner = new StringJoiner(",","(",")");
            row.forEach(cell -> {
                String cellValue = ExcelUtil.getCellValue(cell);
                stringJoiner.add(cellValue);
            });
            System.out.println(stringJoiner);
        }
    }

    public ParseParam createHeadMapParam() {
        Map<String, List<String>> fieldHeadMap = new HashMap<>(16);
        fieldHeadMap.put("id", Arrays.asList("id".toLowerCase(), "序号"));
        fieldHeadMap.put("userName", Arrays.asList("userName".toLowerCase(), "姓名"));
        fieldHeadMap.put("score", Arrays.asList("score".toLowerCase(), "分数"));
        fieldHeadMap.put("date", Arrays.asList("date".toLowerCase(), "日期"));
        ParseParam parseParam = new ParseParam().setHeadLine(0).setStartLine(1)
                .setFieldHeadMap(fieldHeadMap);
        return parseParam;

    }

    @Test
    public void testExcelHeadMap2003CN() {
        String filePath = "file/test2003Head-CN.xls";
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, createHeadMapParam());
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testExcelHeadMap2003EN() {
        String filePath = "file/test2003Head-CN.xls";
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, createHeadMapParam());
        Assert.assertEquals(6, reflectVoList.size());
    }
}
