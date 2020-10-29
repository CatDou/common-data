package com.github.shootercheng.common.util;

public abstract class StringUtils {
    public static final String CRLF_SYMBOL = "\r\n";

    public static final char NEWLINE_SYMBOL = '\n';

    public static final char CR_SYMBOL = '\r';
    
    public static boolean isEmpty( String str) {
        return str == null || "".equals(str);
    }

    public static String removeInvalidChar(String inputStr) {
        if (inputStr == null) {
            return inputStr;
        }
        inputStr = inputStr.replace(CRLF_SYMBOL, " ");
        inputStr = inputStr.replace(NEWLINE_SYMBOL, ' ');
        inputStr = inputStr.replace(CR_SYMBOL, ' ');
        return inputStr.trim();
    }
}
