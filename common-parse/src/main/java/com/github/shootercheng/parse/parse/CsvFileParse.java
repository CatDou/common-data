package com.github.shootercheng.parse.parse;

import com.github.shootercheng.common.util.DataUtil;
import com.github.shootercheng.common.util.StringUtils;
import com.github.shootercheng.parse.constant.CommonConstant;
import com.github.shootercheng.parse.constant.MapperType;
import com.github.shootercheng.parse.exception.FileParseException;
import com.github.shootercheng.parse.param.ParseParam;
import com.github.shootercheng.parse.utils.FileParseCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengdu
 *
 */
public class CsvFileParse implements FileParse {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileParse.class);

    private static final String SPLIT_REGEX = "[,\t]";

    private CsvFileParse() {
    }

    private static class CsvFileParseHolder {
        private static final CsvFileParse parser = new CsvFileParse();
    }

    public static CsvFileParse instance() {
        return CsvFileParseHolder.parser;
    }

    @Override
    public <T> List<T> parseFile(String filePath, Class<T> clazz, ParseParam parseParam) {
        // 校验入参
        checkParam(parseParam);
        List<T> resultList = new ArrayList<>();
        String charsetName = parseParam.getEncode() != null ?
                parseParam.getEncode() : CommonConstant.GBK;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), charsetName))) {
            int readLine = 0;
            int readInt;
            while ((readInt = reader.read()) != -1) {
                List<String> rowData = readLine(readInt, reader, parseParam);
                int headLine = parseParam.getHeadLine();
                // 匹配 head
                if (parseParam.getMapperType() == MapperType.HEAD && readLine == headLine) {
                    Map<Integer, String> headMap = getHeadMap(rowData);
                    FileParseCommonUtil.buildParseParam(clazz, parseParam, headMap);
                } else if (readLine >= parseParam.getStartLine()) {
                    T t = convertArrToVo(clazz, rowData, parseParam);
                    if (t != null) {
                        resultList.add(t);
                    } else {
                        parseParam.getErrorRecord()
                        .writeErrorMsg("line " + readLine + ":" + rowData +
                        "covert to null");
                    }
                    if (parseParam.getDataConsumer() != null) {
                        if (resultList.size() >= parseParam.getBatchNum()) {
                            parseParam.getDataConsumer().accept(resultList, 0);
                        }
                    }
                }
                readLine++;
            }
            if (parseParam.getDataConsumer() != null) {
                if (resultList.size() > 0) {
                    parseParam.getDataConsumer().accept(resultList,0);
                }
            }
        } catch (Exception e) {
            LOGGER.error("parse csv file error {}", e.getMessage());
            throw new FileParseException("parse csv file error", e);
        }
        return resultList;
    }

    private List<String> readLine(int readInt, BufferedReader reader, ParseParam parseParam) throws IOException {
        List<String> rowData = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        char delimiter = parseParam.getCsvParam().getDelimiter();
        char lineSeperator = parseParam.getCsvParam().getLineSeperator();
        char quote = parseParam.getCsvParam().getQuote();
        List<Character> filterCharList = parseParam.getCsvParam().getFilterCharList();
        while (readInt != -1) {
            char readChar = (char) readInt;
            if (readChar == lineSeperator) {
                rowData.add(word.toString());
                return rowData;
            } else if (readChar == delimiter) {
                rowData.add(word.toString());
                word.setLength(0);
            } else if (readChar == quote) {
                while (true) {
                    readInt = reader.read();
                    if (readInt == -1) {
                        break;
                    }
                    readChar = (char) readInt;
                    if (readChar == quote) {
                        break;
                    } else {
                        word.append(readChar);
                    }
                }
            } else if (!filterCharList.contains(readChar)) {
                word.append(readChar);
            }
            readInt = reader.read();
        }
        return rowData;
    }

    private Map<Integer, String> getHeadMap(List<String> rowData) {
        Map<Integer, String> headMap = new HashMap<>();
        for (int i = 0; i < rowData.size(); i++) {
            headMap.put(i, StringUtils.removeInvalidChar(rowData.get(i)));
        }
        return headMap;
    }

    private <T> T convertArrToVo(Class<T> clazz, List<String> rowData, ParseParam parseParam) {
        T t = null;
        try {
            t = clazz.newInstance();
            Map<String, Method> fieldSetterMap = parseParam.getFieldSetterMap();
            for (Map.Entry<String, Method> entry : fieldSetterMap.entrySet()) {
                Integer column = DataUtil.EXCEL_COLUMN.get(entry.getKey());
                String cellValue = rowData.get(column);
                if (parseParam.getCellFormat() != null) {
                    cellValue = parseParam.getCellFormat().format(entry.getKey(), cellValue);
                }
                FileParseCommonUtil.invokeValue(t, entry.getValue(), cellValue);
            }
            if (parseParam.getBusinessDefineParse() != null) {
                parseParam.getBusinessDefineParse().defineParse(t, rowData, parseParam);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public <T> Map<Integer, List<T>> parseFileSheets(String filePath, Class<T> clazz, Map<Integer, ParseParam> parseParamMap) {
        return null;
    }
}
