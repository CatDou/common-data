package com.github.catdou.parse;


import com.github.catdou.parse.model.ExcelTypeVo;
import com.github.catdou.parse.model.ReflectVo;
import com.github.shootercheng.parse.param.ParseParam;
import com.github.shootercheng.parse.utils.FileParseCommonUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
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
}
