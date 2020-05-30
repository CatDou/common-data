package com.github.shootercheng.parse.parse.define;


import com.github.shootercheng.parse.param.ParseParam;

/**
 * @author chengdu
 *
 */
public interface RowDefineParse<T> {
    void defineParse(T t, Object rowData, ParseParam parseParam);
}
