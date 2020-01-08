package com.company;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Map;

public enum Setting {
    BASIC_LOOP,
    FIBONACCI_SERIES,
    SIMPLE_FUNCTION,
    FIBONACCI_ELEMENT,
    STATICS_TEST,
    NESTED_CALL
    ;

    private static final String BASIC_LOOP_VM = "/Users/takakuwashun/app/nand2tetris/projects/08/ProgramFlow/BasicLoop/BasicLoop.vm";
    private static final String BASIC_LOOP_ASM = "/Users/takakuwashun/app/nand2tetris/projects/08/ProgramFlow/BasicLoop/BasicLoop.asm";
    private static final String FIBONACCI_SERIES_VM = "/Users/takakuwashun/app/nand2tetris/projects/08/ProgramFlow/FibonacciSeries/FibonacciSeries.vm";
    private static final String FIBONACCI_SERIES_ASM = "/Users/takakuwashun/app/nand2tetris/projects/08/ProgramFlow/FibonacciSeries/FibonacciSeries.asm";
    private static final String SIMPLE_FUNCTION_VM = "/Users/takakuwashun/app/nand2tetris/projects/08/FunctionCalls/SimpleFunction/SimpleFunction.vm";
    private static final String SIMPLE_FUNCTION_ASM = "/Users/takakuwashun/app/nand2tetris/projects/08/FunctionCalls/SimpleFunction/SimpleFunction.asm";
    private static final String FIBONACCI_ELEMENT_DIR = "/Users/takakuwashun/app/nand2tetris/projects/08/FunctionCalls/FibonacciElement/";
    private static final String FIBONACCI_ELEMENT_ASM = "/Users/takakuwashun/app/nand2tetris/projects/08/FunctionCalls/FibonacciElement/FibonacciElement.asm";
    private static final String STATICS_TEST_DIR = "/Users/takakuwashun/app/nand2tetris/projects/08/FunctionCalls/StaticsTest/";
    private static final String STATICS_TEST_ASM = "/Users/takakuwashun/app/nand2tetris/projects/08/FunctionCalls/StaticsTest/StaticsTest.asm";
    private static final String NESTED_CALL_DIR = "/Users/takakuwashun/app/nand2tetris/projects/08/FunctionCalls/NestedCall/";
    private static final String NESTED_CALL_ASM = "/Users/takakuwashun/app/nand2tetris/projects/08/FunctionCalls/NestedCall/NestedCall.asm";


    public static final Map.Entry<Path, Path> getPath(Setting setting) {
        Path read;
        Path write;
        switch (setting) {
            case BASIC_LOOP:
                read = Paths.get(BASIC_LOOP_VM);
                write = Paths.get(BASIC_LOOP_ASM);
                break;
            case FIBONACCI_SERIES:
                read = Paths.get(FIBONACCI_SERIES_VM);
                write = Paths.get(FIBONACCI_SERIES_ASM);
                break;
            case SIMPLE_FUNCTION:
                read = Paths.get(SIMPLE_FUNCTION_VM);
                write = Paths.get(SIMPLE_FUNCTION_ASM);
                break;
            case FIBONACCI_ELEMENT:
                read = Paths.get(FIBONACCI_ELEMENT_DIR);
                write = Paths.get(FIBONACCI_ELEMENT_ASM);
                break;
            case STATICS_TEST:
                read = Paths.get(STATICS_TEST_DIR);
                write = Paths.get(STATICS_TEST_ASM);
                break;
            case NESTED_CALL:
                read = Paths.get(NESTED_CALL_DIR);
                write = Paths.get(NESTED_CALL_ASM);
                break;
            default:
                throw new RuntimeException("settingがおかしい" + setting);
        }
        return new AbstractMap.SimpleEntry<Path, Path>(read, write);
    }
}
