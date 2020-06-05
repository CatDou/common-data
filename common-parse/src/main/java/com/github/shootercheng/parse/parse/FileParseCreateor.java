package com.github.shootercheng.parse.parse;

import com.github.shootercheng.parse.constant.ParseType;

/**
 * @author chengdu
 *
 */
public class FileParseCreateor {

    private static final class FileParseHolder {
        private static final FileParse CSV_FILE_PARSER = CsvFileParse.instance();
        private static final FileParse EXCEL_FILE_PARSER = ExcelFileParse.instance();
        private static final FileParse EASY_EXCEL_PARSER = EasyExcelParse.instance();
    }

    public static FileParse createFileParse(ParseType parseType) {
        switch (parseType) {
            case CSV:
                return FileParseHolder.CSV_FILE_PARSER;
            case EXCEL:
                return FileParseHolder.EXCEL_FILE_PARSER;
            case EASYEXCEL:
                return FileParseHolder.EASY_EXCEL_PARSER;
            default:
                throw new IllegalArgumentException("input file type error");
        }
    }
}
