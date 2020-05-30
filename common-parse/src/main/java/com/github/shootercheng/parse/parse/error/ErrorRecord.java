package com.github.shootercheng.parse.parse.error;

/**
 * @author chengdu
 *
 */
public abstract class ErrorRecord {

    public abstract void writeErrorMsg(String errorInfo);

    public abstract String getErrorMsg();
}
