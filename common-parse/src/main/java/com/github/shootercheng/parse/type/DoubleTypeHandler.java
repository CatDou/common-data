package com.github.shootercheng.parse.type;

/**
 * @author chengdu
 *
 */
public class DoubleTypeHandler implements BaseTypeHandler {
    @Override
    public Object convertStrToType(String input) {
        return Double.valueOf(input);
    }
}
