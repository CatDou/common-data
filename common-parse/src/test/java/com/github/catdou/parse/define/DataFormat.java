package com.github.catdou.parse.define;

import com.github.shootercheng.parse.parse.define.CellFormat;
import com.github.shootercheng.parse.utils.DateUtil;

import java.util.Date;

/**
 * @author James
 */
public class DataFormat implements CellFormat {

    @Override
    public String format(String column, String cellValue) {
        if ("E".equals(column)) {
            Date date = DateUtil.parseStrToDate(cellValue, "YYYY年MM月dd日HH时mm分");
            return "L" + date.getTime();
        }
        return cellValue;
    }
}
