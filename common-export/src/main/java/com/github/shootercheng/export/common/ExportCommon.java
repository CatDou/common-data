package com.github.shootercheng.export.common;

import com.github.shootercheng.common.util.ReflectUtil;
import com.github.shootercheng.export.exception.ParamBuildException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author James
 */
public class ExportCommon {
    /**
     * calculate page start index
     * @param sum total number of data
     * @param pageNum page size
     * @return index collection
     */
    public static List<Integer> calIndexList(int sum, int pageNum) {
        List<Integer> list = new ArrayList<>(sum / pageNum);
        Integer startIndex = 0;
        if (sum <= pageNum) {
            list.add(startIndex);
            return list;
        }
        while (startIndex + pageNum < sum) {
            list.add(startIndex);
            startIndex = startIndex + pageNum;
        }
        // the last page
        list.add(startIndex);
        return list;
    }

    public static List<Method> buildParamGetter(Class<?> clazz, Map<String, String> fieldColumnMap) {
        Set<String> keySet = fieldColumnMap.keySet();
        List<String> keyList = new ArrayList<>(keySet);
        keyList = keyList.stream().sorted().collect(Collectors.toList());
        Map<String, Method> allBeanGetterMap = ReflectUtil.getBeanGetterMap(clazz);
        List<Method> resultGetter = new ArrayList<>(keyList.size());
        for (String key : keyList) {
            String fieldName = fieldColumnMap.get(key);
            Method getterMethod = allBeanGetterMap.get(fieldName.toLowerCase());
            if (getterMethod == null) {
                throw new ParamBuildException("Bean " + clazz + " not contain field getter " + fieldName +
                        " ,please check config column map");
            }
            resultGetter.add(getterMethod);
        }
        return resultGetter;
    }
}
