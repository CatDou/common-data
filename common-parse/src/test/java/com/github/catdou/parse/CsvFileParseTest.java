package com.github.catdou.parse;

import com.github.catdou.parse.define.InfoFormat;
import com.github.catdou.parse.define.ReflectVoDefineParse;
import com.github.catdou.parse.model.ReflectVo;
import com.github.catdou.parse.model.UserInfo;
import com.github.shootercheng.parse.constant.CommonConstant;
import com.github.shootercheng.parse.param.ParseParam;
import com.github.shootercheng.parse.parse.FileParse;
import com.github.shootercheng.parse.parse.FileParseCreateor;
import com.github.shootercheng.parse.utils.FileParseCommonUtil;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengdu
 *
 */
public class CsvFileParseTest extends ParseCommonTest {

    @Test
    public void testCsvParse() {
        String filePath = "file/test.csv";
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, createReflectParam());
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testDefineParser() {
        String filePath = "file/test.csv";
        ParseParam parseParam = super.createReflectParam()
                .setBusinessDefineParse(new ReflectVoDefineParse())
                .setEncode(CommonConstant.GBK);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, parseParam);
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testDefineFormat() {
        String filePath = "file/test.csv";
        ParseParam parseParam = super.createReflectParam()
                .setBusinessDefineParse(new ReflectVoDefineParse())
                .setEncode(CommonConstant.GBK).setCellFormat(new InfoFormat());
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, parseParam);
        Assert.assertEquals(6, reflectVoList.size());
        Assert.assertEquals("chengdu", reflectVoList.get(0).getUserName());
    }

    @Test
    public void testErrorColumnMap() {
        try {
            Map<String, String> fieldColumnMap = new HashMap<>(16);
            fieldColumnMap.put("A", "id");
            fieldColumnMap.put("B", "gender");
            fieldColumnMap.put("C", "num");
            Map<String, Method> columnMethodMap = FileParseCommonUtil.convertToColumnMethodMap(UserInfo.class, fieldColumnMap);
            Assert.fail("---------bug------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQuotation() {
        String filePath = "file/a.csv";
        ParseParam parseParam = createInfoParam().setEncode(CommonConstant.GBK);
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        List<UserInfo> userInfoList = fileParse.parseFile(filePath, UserInfo.class, parseParam);
        System.out.println(userInfoList);
    }

    public ParseParam createInfoParam() {
        Map<String, String> fieldColumnMap = new HashMap<>(16);
        fieldColumnMap.put("A", "name");
        fieldColumnMap.put("B", "gender");
        fieldColumnMap.put("C", "num");
        Map<String, Method> columnMethodMap = FileParseCommonUtil.convertToColumnMethodMap(UserInfo.class, fieldColumnMap);
        ParseParam parseParam = new ParseParam().setStartLine(1)
                .setFieldSetterMap(columnMethodMap);
        return parseParam;
    }

    @Test
    public void testExcelHeadMap2003CN() {
        String filePath = "file/testHead-CN.csv";
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        ParseParam parseParam = createHeadMapParam().setEncode(StandardCharsets.UTF_8.name());
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, parseParam);
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testExcelHeadMap2003EN() {
        String filePath = "file/testHead-EN.csv";
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        ParseParam parseParam = createHeadMapParam().setEncode(StandardCharsets.UTF_8.name());
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, parseParam);
        Assert.assertEquals(6, reflectVoList.size());
    }

    @Test
    public void testCsvHeadNewLineParse() {
        String filePath = "file/test_head_line.csv";
        FileParse fileParse = FileParseCreateor.createFileParse(FileParseCommonUtil.findParserType(filePath));
        List<ReflectVo> reflectVoList = fileParse.parseFile(filePath, ReflectVo.class, createReflectParam());
        Assert.assertEquals(6, reflectVoList.size());
    }
}
