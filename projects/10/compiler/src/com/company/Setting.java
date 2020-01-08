package com.company;

public enum Setting {
    ARRAY_TEST,
    EXPRESSIONLESS_SQUARE,
    SQUARE,
    ;
    private static final String ARRAY_TEST_DIR = "/Users/takakuwashun/app/nand2tetris/projects/10/ArrayTest/";
    private static final String EXPRESSIONLESS_SQUARE_DIR = "/Users/takakuwashun/app/nand2tetris/projects/10/ExpressionLessSquare/";
    private static final String SQUARE_DIR = "/Users/takakuwashun/app/nand2tetris/projects/10/Square/";

    public static final String getPath(Setting setting) {
        switch (setting) {
            case ARRAY_TEST:
                return ARRAY_TEST_DIR;
            case EXPRESSIONLESS_SQUARE:
                return EXPRESSIONLESS_SQUARE_DIR;
            case SQUARE:
                return SQUARE_DIR;
            default:
                throw new RuntimeException("settingがおかしい" + setting);
        }
    }
}
