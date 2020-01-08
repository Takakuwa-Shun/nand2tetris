package com.company;

public interface JackTokenizer {
    boolean hasMoreTokens();
    void advance();
    void retreat();
    TokenType tokenType();
    KeywordType keyword();
    String symbol();
    String identifier();
    int intVal();
    String stringVal();
}
