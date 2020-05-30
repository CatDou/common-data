package com.github.catdou.parse.consumer;

import com.github.catdou.parse.model.LargeData;
import com.github.shootercheng.parse.parse.consumer.DataConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author James
 */
public class SaveService implements DataConsumer<LargeData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveService.class);

    private int sum = 0;

    public int getSum() {
        return sum;
    }

    @Override
    public void accept(List<LargeData> resultList, Integer sheet) {
        LOGGER.info("parse sheet {}", sheet);
        sum = sum + resultList.size();
        LOGGER.info("saved data size {}, sum {}", resultList.size(), sum);
    }
}
