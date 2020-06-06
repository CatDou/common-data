package com.github.shootercheng.parse.utils;

import com.github.shootercheng.common.util.DataUtil;
import com.github.shootercheng.common.util.ReflectUtil;
import com.github.shootercheng.common.util.StringUtils;
import com.github.shootercheng.parse.constant.CommonConstant;
import com.github.shootercheng.parse.constant.ParseType;
import com.github.shootercheng.parse.exception.FileParseException;
import com.github.shootercheng.parse.exception.ParamBuildException;
import com.github.shootercheng.parse.param.ParseParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chengdu
 *
 */
public class FileParseCommonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileParseCommonUtil.class);

    public static Map<String, Method> convertToColumnMethodMap(Class<?> clazz, Map<String, String>  fieldColumnMap) {
        Set<Map.Entry<String, String>> entrySet = fieldColumnMap.entrySet();
        Map<String, Method> columnFieldSetter = new HashMap<>(16);
        Map<String, Method> allBeanSetter = ReflectUtil.getBeanSetterMap(clazz);
        for (Map.Entry<String, String> entry : entrySet) {
            String column = entry.getKey();
            String fieldName = entry.getValue().toLowerCase();
            Method setterMethod = allBeanSetter.get(fieldName);
            if (setterMethod == null) {
                throw new IllegalArgumentException("Bean " + clazz + " not contain field " + fieldName +
                        " ,please check config column map");
            }
            columnFieldSetter.put(column, allBeanSetter.get(fieldName));
        }
        return columnFieldSetter;
    }

    /**
     * 根据 head 字段参数 匹配每一列对应的 setXX 方法
     * @param clazz
     * @param parseParam
     * @param headMap
     */
    public static void buildParseParam(Class<?> clazz, ParseParam parseParam, Map<Integer, String> headMap) {
        Map<String, List<String>> fieldHeadMap = parseParam.getFieldHeadMap();
        Map<String, String> fieldColumnMap = new HashMap<>();
        Set<Map.Entry<Integer, String>> entrySet = headMap.entrySet();
        for (Map.Entry<Integer, String> entry : entrySet) {
            Integer key = entry.getKey();
            String excelColumn = DataUtil.COLUMN_NUM.get(key);
            String head = entry.getValue();
            String field = findHeadField(fieldHeadMap, head);
            fieldColumnMap.put(excelColumn, field.toLowerCase());
        }
        Map<String, Method> columnSetterMap = FileParseCommonUtil.convertToColumnMethodMap(clazz, fieldColumnMap);
        parseParam.setFieldSetterMap(columnSetterMap);

    }

    /**
     * 找到当前 head 对应的 字段
     * @param fieldHeadMap
     * @param head
     * @return 模型对应的字段
     */
    private static String findHeadField(Map<String, List<String>> fieldHeadMap, String head) {
        Set<Map.Entry<String, List<String>>> entrySet = fieldHeadMap.entrySet();
        String field = null;
        for (Map.Entry<String, List<String>> entry : entrySet) {
            String key = entry.getKey();
            List<String> headList = entry.getValue();
            if (isMatchHead(headList, head)) {
                field = key;
                break;
            }
        }
        if (field == null) {
            throw new ParamBuildException("file head ["+ head + "] can not map bean field, " +
                    "please check field map config");
        }
        fieldHeadMap.remove(field);
        return field;
    }

    private static boolean isMatchHead(List<String> headList, String head) {
        for (String value : headList) {
            if (value.equalsIgnoreCase(head)) {
                return true;
            }
        }
        return false;
    }

    public static <T> void invokeValue( T t, Method method, String value) {
        if (StringUtils.isEmpty(value)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("input value is empty");
            }
            return;
        }
        if (method == null) {
            throw new IllegalArgumentException("input set method is null");
        }
        if (method.getParameterTypes().length != 1) {
            LOGGER.error("input method parameter type error");
            return;
        }
        if (!method.getName().startsWith("set")) {
            LOGGER.error("input method not setter method {}", method.getName());
            return;
        }
        String typeName = method.getParameterTypes()[0].getTypeName();
        if (!CommonConstant.HANDLER_MAP.containsKey(typeName)) {
            LOGGER.error("unknown type handler {}", typeName);
            return ;
        }
        Object typeValue = CommonConstant.HANDLER_MAP.get(typeName).convertStrToType(value);
        try {
            method.invoke(t, typeValue);
        } catch (Exception e) {
            throw new FileParseException("invoke value error", e);
        }
    }

    public static ParseType findParserType(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("input filepath is empty");
        }
        ParseType parseType;
        if (filePath.endsWith(".csv")) {
            parseType = ParseType.CSV;
        } else if (filePath.endsWith(".xls")|| filePath.endsWith(".xlsx")) {
            parseType = ParseType.EXCEL;
        } else {
            throw new IllegalArgumentException("input filepath error " + filePath);
        }
        return parseType;
    }

    public static ParseType findParserType(String filePath, ParseParam parseParam) {
        ParseType parseTypeFind = findParserType(filePath);
        // 如果参数 说明要 EasyExcel 解析就使用 Easy Excel
        if (ParseType.EXCEL == parseTypeFind && parseParam.getParseType() != null) {
            if (ParseType.EASYEXCEL == parseParam.getParseType()) {
                return ParseType.EASYEXCEL;
            }
        }
        return parseTypeFind;
    }
}
