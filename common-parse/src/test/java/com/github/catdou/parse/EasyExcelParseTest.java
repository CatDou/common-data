package com.github.catdou.parse;

import com.github.catdou.parse.consumer.SaveService;
import com.github.catdou.parse.model.DemoData;
import com.github.catdou.parse.model.ExcelTypeVo;
import com.github.catdou.parse.model.LargeData;
import com.github.catdou.parse.model.MergeDataVo;
import com.github.catdou.parse.model.ReflectVo;
import com.github.shootercheng.parse.constant.ParseType;
import com.github.shootercheng.parse.param.ParseParam;
import com.github.shootercheng.parse.parse.FileParse;
import com.github.shootercheng.parse.parse.FileParseCreateor;
import com.github.shootercheng.parse.utils.FileParseCommonUtil;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengdu
 *
 */
public class EasyExcelParseTest extends ParseCommonTest {

    @Test
    public void testParse() {
        String filePath = "file/demo.xlsx";
        ParseParam parseParam = createDemoParam();
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<DemoData> demoDataList = fileParse.parseFile(filePath, DemoData.class, parseParam);
        Assert.assertTrue(demoDataList.size() > 0);
    }

    @Test
    public void testParseDemoXls() {
        String filePath = "file/demo2003.xls";
        ParseParam parseParam = createDemoParam();
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<DemoData> demoDataList = fileParse.parseFile(filePath, DemoData.class, parseParam);
        Assert.assertTrue(demoDataList.size() > 0);
    }

    @Test
    public void testExcelType() {
        String filePath = "file/data-type.xlsx";
        ParseParam parseParam = createExcelTypeParam();
        parseParam.setParseType(ParseType.EASYEXCEL);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<ExcelTypeVo> excelTypeVos = fileParse.parseFile(filePath, ExcelTypeVo.class, parseParam);
        Assert.assertEquals(6, excelTypeVos.size());
    }

    public ParseParam createDemoParam() {
        Map<String, String> fieldColumnMap = new HashMap<>(16);
        fieldColumnMap.put("A", "string");
        fieldColumnMap.put("B", "date");
        fieldColumnMap.put("C", "doubleData");
        fieldColumnMap.put("D", "utDate");
        Map<String, Method> columnMethodMap = FileParseCommonUtil.convertToColumnMethodMap(DemoData.class, fieldColumnMap);
        ParseParam parseParam = new ParseParam().setStartLine(1)
                .setFieldSetterMap(columnMethodMap).setParseType(ParseType.EASYEXCEL);
        return parseParam;
    }

    private static Map<String, Method> largeDataMethodMap;

    static {
        Map<String, String> fieldColumnMap = new HashMap<>(16);
        fieldColumnMap.put("A", "str1");
        fieldColumnMap.put("B", "str2");
        fieldColumnMap.put("C", "str3");
        fieldColumnMap.put("D", "str4");
        fieldColumnMap.put("E", "str5");
        fieldColumnMap.put("F", "str6");
        fieldColumnMap.put("G", "str7");
        fieldColumnMap.put("H", "str8");
        fieldColumnMap.put("I", "str9");
        fieldColumnMap.put("J", "str10");
        fieldColumnMap.put("K", "str11");
        fieldColumnMap.put("L", "str12");
        fieldColumnMap.put("M", "str13");
        largeDataMethodMap = FileParseCommonUtil.convertToColumnMethodMap(LargeData.class, fieldColumnMap);
    }

    @Test
    public void testLargeExcelTest() {
        long startTime = System.currentTimeMillis();
        String filePath = "file/large07.xlsx";
        ParseParam parseParam = new ParseParam().setStartLine(1)
                .setFieldSetterMap(largeDataMethodMap).setParseType(ParseType.EASYEXCEL);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<LargeData> largeDataList = fileParse.parseFile(filePath, LargeData.class, parseParam);
        System.out.println("time " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println(largeDataList.size());
    }

    @Test
    public void testLargeExcelWithConsumerTest() {
        long startTime = System.currentTimeMillis();
        String filePath = "file/large07.xlsx";
        SaveService saveService = new SaveService();
        ParseParam parseParam = new ParseParam().setStartLine(1)
                .setFieldSetterMap(largeDataMethodMap)
                .setParseType(ParseType.EASYEXCEL)
                .setDataConsumer(saveService);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<LargeData> largeDataList = fileParse.parseFile(filePath, LargeData.class, parseParam);
        System.out.println("time " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println(saveService.getSum());
        Assert.assertEquals(0, largeDataList.size());
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
        FileParse fileParse = FileParseCreateor.createFileParse(ParseType.EASYEXCEL);
        Map<Integer, List<MergeDataVo>> resultMap = fileParse.parseFileSheets(filePath, MergeDataVo.class, map);
        System.out.println(resultMap);
    }

    @Test
    public void testExcelHeadMap2003CN() {
        String filePath = "file/test2003Head-CN.xls";
        ParseParam parseParam = createHeadMapParam().setParseType(ParseType.EASYEXCEL);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, parseParam);
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testExcelHeadMap2003EN() {
        String filePath = "file/test2003Head-EN.xls";
        ParseParam parseParam = createHeadMapParam().setParseType(ParseType.EASYEXCEL);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, parseParam);
        Assert.assertEquals(6, reflectVoList.size());
    }
}
