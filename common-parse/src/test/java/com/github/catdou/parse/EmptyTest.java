package com.github.catdou.parse;

import com.github.catdou.parse.model.Label;
import com.github.shootercheng.parse.constant.ParseType;
import com.github.shootercheng.parse.param.ParseParam;
import com.github.shootercheng.parse.parse.FileParse;
import com.github.shootercheng.parse.parse.FileParseCreateor;
import com.github.shootercheng.parse.utils.AnnotationUtil;
import com.github.shootercheng.parse.utils.FileParseCommonUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


/**
 * @author James
 */
public class EmptyTest {

    @Test
    public void testCellEmpty() {
        String excelPath = "file/test-empty.xlsx";
        ParseParam parseParam = new ParseParam()
                .setStartLine(1)
                .setFieldSetterMap(AnnotationUtil.findOneSheetSetter(Label.class))
                .setParseType(ParseType.EASYEXCEL);
        FileParse fileParse = FileParseCreateor.createFileParse(
                FileParseCommonUtil.findParserType(excelPath, parseParam));
        List<Label> labelList = fileParse.parseFile(excelPath, Label.class, parseParam);
        Assert.assertTrue(labelList.size() > 0);
    }
}
