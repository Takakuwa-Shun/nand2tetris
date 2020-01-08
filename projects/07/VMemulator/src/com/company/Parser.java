package com.company;

public interface Parser {
    boolean hasMoreCommands();
    void advance();
    CommandType commandType();
    String arg1();
    int arg2();
}
