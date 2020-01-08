package com.company.api;

import com.company.model.Kind;

public interface SymbolTable {
    void startSubroutine();
    void define(final String name, final String type, final Kind kind);
    int varCount(final Kind kind);
    Kind kindOf(final String name);
    String typeOf(final String name);
    int indexOf(final String name);
}
