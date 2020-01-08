package com.company;

public class Main {

    public static void main(String[] args) {
        JackAnalyzer analyzer = new JackAnalyzer(Setting.getPath(Setting.SQUARE), false);
        analyzer.compile();
    }
}
