package com.github.shootercheng.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author James
 */
public class DataUtil {

    public static char[] UPPER_CHAR = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S',
            'T','U','V','W','X','Y','Z'};

    /**
     * 从 0 开始, AAA-702 不考虑
     * @param num the char num
     * @return excel column str
     */
    public static String calExcelNumChar(int num) {
        if (num < 0 || num >= 702) {
            throw new IllegalArgumentException("column num input error");
        }
        if (num < 26) {
            return String.valueOf(UPPER_CHAR[num]);
        }
        int firstIndex = num / 26 - 1;
        int secondIndex = num % 26;
        return String.valueOf(UPPER_CHAR[firstIndex]) + UPPER_CHAR[secondIndex];
    }

    public static Map<String, Integer> EXCEL_COLUMN = new HashMap<>();

    public static Map<Integer, String> COLUMN_NUM = new HashMap<>();

    static {
        for (int i = 0; i < 702; i++) {
            String columnStr = calExcelNumChar(i);
            EXCEL_COLUMN.put(columnStr, i);
            COLUMN_NUM.put(i, columnStr);
        }
    }
}
