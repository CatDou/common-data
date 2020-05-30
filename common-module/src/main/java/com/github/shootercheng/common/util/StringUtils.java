package com.github.shootercheng.common.util;

public abstract class StringUtils {
    
    public static boolean isEmpty( String str) {
        return str == null || "".equals(str);
    }
}
