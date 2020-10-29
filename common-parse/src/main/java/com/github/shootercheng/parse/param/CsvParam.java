package com.github.shootercheng.parse.param;

import java.util.Arrays;
import java.util.List;

/**
 * @author James
 */
public class CsvParam {
    private char delimiter = ',';

    private char lineSeperator = '\n';

    private char quote = '"';

    private List<Character> filterCharList = Arrays.asList('\r');

    public CsvParam() {

    }

    public CsvParam(char delimiter, char lineSeperator, char quote, List<Character> filterCharList) {
        this.delimiter = delimiter;
        this.lineSeperator = lineSeperator;
        this.quote = quote;
        this.filterCharList = filterCharList;
    }

    public char getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    public char getLineSeperator() {
        return lineSeperator;
    }

    public void setLineSeperator(char lineSeperator) {
        this.lineSeperator = lineSeperator;
    }

    public char getQuote() {
        return quote;
    }

    public void setQuote(char quote) {
        this.quote = quote;
    }

    public List<Character> getFilterCharList() {
        return filterCharList;
    }

    public void setFilterCharList(List<Character> filterCharList) {
        this.filterCharList = filterCharList;
    }
}
