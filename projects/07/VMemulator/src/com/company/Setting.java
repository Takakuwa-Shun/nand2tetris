package com.company;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Map;

public enum Setting {
    BASIC_TEST,
    POINTER,
    STATIC_TEST,
    SIMPLE_ADD,
    STACK_TEST,
    ;

    private static final String BASIC_TEST_VM = "/Users/takakuwashun/app/nand2tetris/projects/07/MemoryAccess/BasicTest/BasicTest.vm";
    private static final String BASIC_TEST_ASM = "/Users/takakuwashun/app/nand2tetris/projects/07/MemoryAccess/BasicTest/BasicTest.asm";
    private static final String POINTER_VM = "/Users/takakuwashun/app/nand2tetris/projects/07/MemoryAccess/PointerTest/PointerTest.vm";
    private static final String POINTER_ASM = "/Users/takakuwashun/app/nand2tetris/projects/07/MemoryAccess/PointerTest/PointerTest.asm";
    private static final String STATIC_TEST_VM = "/Users/takakuwashun/app/nand2tetris/projects/07/MemoryAccess/StaticTest/StaticTest.vm";
    private static final String STATIC_TEST_ASM = "/Users/takakuwashun/app/nand2tetris/projects/07/MemoryAccess/StaticTest/StaticTest.asm";
    private static final String SIMPLE_ADD_VM = "/Users/takakuwashun/app/nand2tetris/projects/07/StackArithmetic/SimpleAdd/SimpleAdd.vm";
    private static final String SIMPLE_ADD_ASM = "/Users/takakuwashun/app/nand2tetris/projects/07/StackArithmetic/SimpleAdd/SimpleAdd.asm";
    private static final String STACK_TEST_VM = "/Users/takakuwashun/app/nand2tetris/projects/07/StackArithmetic/StackTest/StackTest.vm";
    private static final String STACK_TEST_ASM = "/Users/takakuwashun/app/nand2tetris/projects/07/StackArithmetic/StackTest/StackTest.asm";

    public static final Map.Entry<Path, Path> getPath(Setting setting) {
        Map.Entry<Path, Path> pair = null;
        Path read;
        Path write;
        switch (setting) {
            case BASIC_TEST:
                read = Paths.get(BASIC_TEST_VM);
                write = Paths.get(BASIC_TEST_ASM);
                pair = new AbstractMap.SimpleEntry<Path, Path>(read, write);
                break;
            case POINTER:
                read = Paths.get(POINTER_VM);
                write = Paths.get(POINTER_ASM);
                pair = new AbstractMap.SimpleEntry<Path, Path>(read, write);
                break;
            case STATIC_TEST:
                read = Paths.get(STATIC_TEST_VM);
                write = Paths.get(STATIC_TEST_ASM);
                pair = new AbstractMap.SimpleEntry<Path, Path>(read, write);
                break;
            case SIMPLE_ADD:
                read = Paths.get(SIMPLE_ADD_VM);
                write = Paths.get(SIMPLE_ADD_ASM);
                pair = new AbstractMap.SimpleEntry<Path, Path>(read, write);
                break;
            case STACK_TEST:
                read = Paths.get(STACK_TEST_VM);
                write = Paths.get(STACK_TEST_ASM);
                pair = new AbstractMap.SimpleEntry<Path, Path>(read, write);
                break;
            default:
                throw new RuntimeException("settingがおかしい" + setting);
        }
        return pair;
    }
}
