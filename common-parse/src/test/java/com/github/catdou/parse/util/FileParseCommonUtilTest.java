package com.github.catdou.parse.util;

import com.github.catdou.parse.model.ReflectVo;
import com.github.shootercheng.parse.param.ParseParam;
import com.github.shootercheng.parse.utils.FileParseCommonUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author James
 */
public class FileParseCommonUtilTest {

    @Test
    public void testBuildParam() {
        Map<String, List<String>> fieldHeadMap = new HashMap<>(16);
        fieldHeadMap.put("id", Arrays.asList("id", "序号"));
        fieldHeadMap.put("userName", Arrays.asList("userName", "姓名"));
        fieldHeadMap.put("score", Arrays.asList("score", "分数"));
        fieldHeadMap.put("date", Arrays.asList("date", "日期"));
        ParseParam parseParam = new ParseParam().setHeadLine(0).setStartLine(1)
                .setFieldHeadMap(fieldHeadMap);
        Map<Integer, String> headMap = new HashMap<>();
        headMap.put(0, "ID");
        headMap.put(1, "USERNAME");
        headMap.put(2, "DATE");
        headMap.put(3, "SCORE");
        FileParseCommonUtil.buildParseParam(ReflectVo.class, parseParam, headMap);
        System.out.println(parseParam);
    }
}
