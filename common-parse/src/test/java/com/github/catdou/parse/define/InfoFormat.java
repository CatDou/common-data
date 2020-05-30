package com.github.catdou.parse.define;


import com.github.shootercheng.parse.parse.define.CellFormat;

/**
 * @author chengdu
 *
 */
public class InfoFormat implements CellFormat {

    @Override
    public String format(String column, String cellValue) {
        if ("B".equals(column)) {
            if (cellValue != null && cellValue.length() > 1) {
                cellValue =cellValue.substring(0, cellValue.length() - 1);
            }
        }
        return cellValue;
    }
}
