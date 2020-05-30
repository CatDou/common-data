package com.github.shootercheng.parse.parse.consumer;

import java.util.List;

/**
 * @author James
 */
public interface DataConsumer<T> {
    /**
     * 消费数据
     * @param resultList result list
     * @param sheet sheet
     */
    void accept(List<T> resultList, Integer sheet);
}
