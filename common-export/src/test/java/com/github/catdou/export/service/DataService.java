package com.github.catdou.export.service;

import com.github.shootercheng.common.constant.CommonConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author James
 */
public class DataService {

    public int countSum() {
        return 1000000;
    }

    public List<String> selectPage(Map<String, Object> paramMap) {
        int pageSize = (int) paramMap.get(CommonConstants.PAGE_QUERY_SIZE);
        int pageIndex = (int) paramMap.get(CommonConstants.PADE_QUERY_INDEX);
        System.out.println("start export page index " + pageIndex);
        List<String> resultList = new ArrayList<>(pageSize);
        for (int i = 0; i < pageSize; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("james").append(",").append("*****")
                    .append(",").append(pageIndex + i);
            resultList.add(stringBuilder.toString());
        }
        return resultList;
    }
}
