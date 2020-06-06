package com.github.catdou.parse;


import com.github.catdou.parse.model.ExcelTypeVo;
import com.github.catdou.parse.model.ReflectVo;
import com.github.shootercheng.parse.param.ParseParam;
import com.github.shootercheng.parse.utils.FileParseCommonUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengdu
 *
 */
public class ParseCommonTest {

    public ParseParam createReflectParam() {
        Map<String, String> fieldColumnMap = new HashMap<>(16);
        fieldColumnMap.put("A", "id");
        fieldColumnMap.put("B", "userName");
        fieldColumnMap.put("C", "score");
        fieldColumnMap.put("D", "date");
        Map<String, Method> columnMethodMap = FileParseCommonUtil.convertToColumnMethodMap(ReflectVo.class, fieldColumnMap);
        ParseParam parseParam = new ParseParam().setStartLine(1)
                .setFieldSetterMap(columnMethodMap);
        return parseParam;

    }

    public ParseParam createExcelTypeParam() {
        Map<String, String> fieldColumnMap = new HashMap<>(16);
        fieldColumnMap.put("A", "id");
        fieldColumnMap.put("B", "userName");
        fieldColumnMap.put("C", "score");
        fieldColumnMap.put("D", "date");
        fieldColumnMap.put("E", "numDate");
        fieldColumnMap.put("F", "bool");
        Map<String, Method> columnMethodMap = FileParseCommonUtil.convertToColumnMethodMap(ExcelTypeVo.class, fieldColumnMap);
        ParseParam parseParam = new ParseParam().setStartLine(1)
                .setFieldSetterMap(columnMethodMap);
        return parseParam;
    }

    public ParseParam createHeadMapParam() {
        Map<String, List<String>> fieldHeadMap = new HashMap<>(16);
        fieldHeadMap.put("id", Arrays.asList("id", "序号"));
        fieldHeadMap.put("userName", Arrays.asList("userName", "姓名"));
        fieldHeadMap.put("score", Arrays.asList("score", "分数"));
        fieldHeadMap.put("date", Arrays.asList("date", "日期"));
        ParseParam parseParam = new ParseParam().setHeadLine(0).setStartLine(1)
                .setFieldHeadMap(fieldHeadMap);
        return parseParam;
    }
}
