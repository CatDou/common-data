package com.github.shootercheng.parse.type;

import com.github.shootercheng.parse.utils.DateUtil;

import java.util.Date;

/**
 * @author chengdu
 *
 */
public class DateTypeHandler implements BaseTypeHandler {
    @Override
    public Object convertStrToType(String input) {
        if (input.startsWith("L")) {
            long longDate = Long.valueOf(input.substring(1).trim());
            return new Date(longDate);
        }
        return DateUtil.parseStrToDate(input);
    }
}
