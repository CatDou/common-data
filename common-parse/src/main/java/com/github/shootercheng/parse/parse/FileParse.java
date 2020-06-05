package com.github.shootercheng.parse.parse;


import com.github.shootercheng.parse.param.ParseParam;

import java.util.List;
import java.util.Map;

/**
 * @author chengdu
 *
 */
public interface FileParse {
    <T> List<T> parseFile(String filePath, Class<T> clazz, ParseParam parseParam);

    default void checkParam(ParseParam parseParam) {
        boolean fieldSetter = parseParam.getFieldSetterMap() == null ||
                parseParam.getFieldSetterMap().size() == 0;
        boolean fieldHeadMap = parseParam.getFieldHeadMap() == null ||
                parseParam.getFieldHeadMap().size() == 0;
        if ( !fieldSetter || !fieldHeadMap) {
            throw new IllegalArgumentException("please check field setter mapper");
        }
    }

    /**
     * parse many sheet
     * @param filePath file path
     * @param clazz clazz
     * @param parseParamMap parse param map
     * @param <T> T
     * @return many sheet result
     */
    <T> Map<Integer, List<T>> parseFileSheets(String filePath, Class<T> clazz, Map<Integer, ParseParam> parseParamMap);
}
