package com.company;

public interface Parser {
    boolean hasMoreCommands();
    void advance();
    CommandType commandType();
    String symbol();
    String dest();
    String comp();
    String jump();
    void clear();
}
