package com.company;

public interface SymbolTable {
    void addEntry(final String symbol, final int address);
    boolean contains(final String symbol);
    int getAddress(final String symbol);
}
