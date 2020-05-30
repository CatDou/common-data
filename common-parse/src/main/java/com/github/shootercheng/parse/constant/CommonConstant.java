package com.github.shootercheng.parse.constant;


import com.github.shootercheng.parse.type.BaseTypeHandler;
import com.github.shootercheng.parse.type.BooleanTypeHandler;
import com.github.shootercheng.parse.type.DateTypeHandler;
import com.github.shootercheng.parse.type.DoubleTypeHandler;
import com.github.shootercheng.parse.type.IntegerTypeHandler;
import com.github.shootercheng.parse.type.LongTypeHandler;
import com.github.shootercheng.parse.type.StringTypeHandler;
import com.github.shootercheng.parse.type.TypeEnum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengdu
 *
 */
public class CommonConstant {

    public static final Map<String, BaseTypeHandler> HANDLER_MAP;
    static {
        Map<String, BaseTypeHandler> map = new HashMap<>(16);
        map.put(TypeEnum.String.type(), new StringTypeHandler());
        map.put(TypeEnum.Integer.type(), new IntegerTypeHandler());
        map.put(TypeEnum.Double.type(), new DoubleTypeHandler());
        map.put(TypeEnum.Long.type(), new LongTypeHandler());
        map.put(TypeEnum.Date.type(), new DateTypeHandler());
        map.put(TypeEnum.Boolean.type(), new BooleanTypeHandler());
        map.put(TypeEnum.BaseInt.type(), new IntegerTypeHandler());
        map.put(TypeEnum.BaseDouble.type(), new DoubleTypeHandler());
        map.put(TypeEnum.BaseLong.type(), new LongTypeHandler());
        map.put(TypeEnum.BaseBoolean.type(), new BooleanTypeHandler());
        HANDLER_MAP = Collections.unmodifiableMap(map);
    }

    public static final String GBK = "GBK";

    public static final String UTF8 = "UTF-8";

    public static final String UTF16 = "UTF-16";

    public static final String UNICODE = "Unicode";
}
