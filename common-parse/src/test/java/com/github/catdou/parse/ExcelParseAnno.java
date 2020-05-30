package com.github.catdou.parse;

import com.github.catdou.parse.model.MergeDataVo;
import com.github.catdou.parse.model.ReflectVo;
import com.github.shootercheng.parse.constant.ParseType;
import com.github.shootercheng.parse.param.ParseParam;
import com.github.shootercheng.parse.parse.FileParse;
import com.github.shootercheng.parse.parse.FileParseCreateor;
import com.github.shootercheng.parse.utils.AnnotationUtil;
import com.github.shootercheng.parse.utils.FileParseCommonUtil;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author James
 */
public class ExcelParseAnno {

    @Test
    public void testExcelAnno() {
        String filePath = "file/many-sheet-data.xlsx";
        Map<Integer, Map<String, Method>> sheetMethodMap = AnnotationUtil.findManySheetSetter(MergeDataVo.class);
        Assert.assertEquals(2, sheetMethodMap.size());
        ParseParam sheet0Param = new ParseParam().setStartLine(1)
                .setFieldSetterMap(sheetMethodMap.get(0));
        ParseParam sheet1Param = new ParseParam().setStartLine(1)
                .setFieldSetterMap(sheetMethodMap.get(1));
        Map<Integer, ParseParam> map = new HashMap<>(16);
        map.put(0, sheet0Param);
        map.put(1, sheet1Param);
        FileParse fileParse = FileParseCreateor.createFileParse(ParseType.EXCEL);
        Map<Integer, List<MergeDataVo>> resultMap = fileParse.parseFileSheets(filePath, MergeDataVo.class, map);
        System.out.println(resultMap);
    }

    @Test
    public void testEasyExcelAnno() {
        String filePath = "file/many-sheet-data.xlsx";
        Map<Integer, Map<String, Method>> sheetMethodMap = AnnotationUtil.findManySheetSetter(MergeDataVo.class);
        Assert.assertEquals(2, sheetMethodMap.size());
        ParseParam sheet0Param = new ParseParam().setStartLine(1)
                .setFieldSetterMap(sheetMethodMap.get(0));
        ParseParam sheet1Param = new ParseParam().setStartLine(1)
                .setFieldSetterMap(sheetMethodMap.get(1));
        Map<Integer, ParseParam> map = new HashMap<>(16);
        map.put(0, sheet0Param);
        map.put(1, sheet1Param);
        FileParse fileParse = FileParseCreateor.createFileParse(ParseType.EASYEXCEL);
        Map<Integer, List<MergeDataVo>> resultMap = fileParse.parseFileSheets(filePath, MergeDataVo.class, map);
        System.out.println(resultMap);
    }

    @Test
    public void testExcel2003() {
        String filePath = "file/test2003.xls";
        ParseParam parseParam = new ParseParam()
                .setFieldSetterMap(AnnotationUtil.findOneSheetSetter(ReflectVo.class))
                .setStartLine(1);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, parseParam);
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testExcel2007() {
        String filePath = "file/test2007.xlsx";
        ParseParam parseParam = new ParseParam()
                .setFieldSetterMap(AnnotationUtil.findOneSheetSetter(ReflectVo.class))
                .setStartLine(1);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, parseParam);
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testExcel2003EasyExcel() {
        String filePath = "file/test2003.xls";
        ParseParam parseParam = new ParseParam()
                .setFieldSetterMap(AnnotationUtil.findOneSheetSetter(ReflectVo.class))
                .setStartLine(1).setParseType(ParseType.EASYEXCEL);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, parseParam);
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testExcel2007EasyExcel() {
        String filePath = "file/test2007.xlsx";
        ParseParam parseParam = new ParseParam()
                .setFieldSetterMap(AnnotationUtil.findOneSheetSetterBySheet(ReflectVo.class, 0))
                .setStartLine(1).setParseType(ParseType.EASYEXCEL);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath, parseParam));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, parseParam);
        Assert.assertEquals(6, reflectVoList.size());
    }
}
