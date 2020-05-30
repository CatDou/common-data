package com.github.shootercheng.parse.type;

/**
 * @author chengdu
 *
 */
public class StringTypeHandler implements BaseTypeHandler {
    @Override
    public Object convertStrToType(String input) {
        return String.valueOf(input);
    }
}
