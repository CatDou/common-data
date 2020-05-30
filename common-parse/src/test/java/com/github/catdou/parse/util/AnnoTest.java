package com.github.catdou.parse.util;

import com.github.catdou.parse.model.MergeDataVo;
import com.github.catdou.parse.model.UserInfo;
import com.github.shootercheng.parse.utils.AnnotationUtil;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author James
 */
public class AnnoTest {

    @Test
    public void testAnno() {
        Map<String, Method> columnSetter = AnnotationUtil.findOneSheetSetter(UserInfo.class);
        Assert.assertEquals(3, columnSetter.size());
    }

    @Test
    public void testAnnoWithSheet() {
        Map<String, Method> columnSetter = AnnotationUtil.findOneSheetSetterBySheet(UserInfo.class, 0);
        Assert.assertEquals(3, columnSetter.size());
    }

    @Test
    public void testManySheet() {
        Map<Integer, Map<String, Method>> sheetSetterMap = AnnotationUtil.findManySheetSetter(MergeDataVo.class);
        Assert.assertEquals(2, sheetSetterMap.size());
    }
}
