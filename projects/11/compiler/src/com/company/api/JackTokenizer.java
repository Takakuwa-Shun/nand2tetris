package com.company.api;

import com.company.model.KeywordType;
import com.company.model.TokenType;

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
