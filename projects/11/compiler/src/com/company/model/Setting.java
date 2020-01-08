package com.company.model;

public enum Setting {
    ARRAY_TEST,
    EXPRESSIONLESS_SQUARE,
    SEVEN,
    CONVERT_TO_BIN,
    SQUARE,
    AVERAGE,
    PONG,
    COMPLEX_ARRAYS,
    ;
    private static final String ARRAY_TEST_DIR = "/Users/takakuwashun/app/nand2tetris/projects/10/ArrayTest/";
    private static final String EXPRESSIONLESS_SQUARE_DIR = "/Users/takakuwashun/app/nand2tetris/projects/10/ExpressionLessSquare/";
    private static final String SEVEN_DIR = "/Users/takakuwashun/app/nand2tetris/projects/11/Seven/";
    private static final String CONVERT_TO_BIN_DIR = "/Users/takakuwashun/app/nand2tetris/projects/11/ConvertToBin/";
    private static final String SQUARE_DIR = "/Users/takakuwashun/app/nand2tetris/projects/11/Square/";
    private static final String AVERAGE_DIR = "/Users/takakuwashun/app/nand2tetris/projects/11/Average/";
    private static final String PONG_DIR = "/Users/takakuwashun/app/nand2tetris/projects/11/Pong/";
    private static final String COMPLEX_ARRAYS_DIR = "/Users/takakuwashun/app/nand2tetris/projects/11/ComplexArrays/";

    public static final String getPath(Setting setting) {
        switch (setting) {
            case ARRAY_TEST:
                return ARRAY_TEST_DIR;
            case EXPRESSIONLESS_SQUARE:
                return EXPRESSIONLESS_SQUARE_DIR;
            case SEVEN:
                return SEVEN_DIR;
            case CONVERT_TO_BIN:
                return CONVERT_TO_BIN_DIR;
            case SQUARE:
                return SQUARE_DIR;
            case AVERAGE:
                return AVERAGE_DIR;
            case PONG:
                return PONG_DIR;
            case COMPLEX_ARRAYS:
                return COMPLEX_ARRAYS_DIR;
            default:
                throw new RuntimeException("settingがおかしい" + setting);
        }
    }
}
