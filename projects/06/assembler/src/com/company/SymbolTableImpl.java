package com.company;

import java.util.HashMap;
import java.util.Map;

public class SymbolTableImpl implements SymbolTable {

    private final Map<String, Integer> table;
    private int varAddress = 15;

    public SymbolTableImpl() {
        this.table = new HashMap<>();

        this.addEntry("SP", 0);
        this.addEntry("LCL", 1);
        this.addEntry("ARG", 2);
        this.addEntry("THIS", 3);
        this.addEntry("THAT", 4);
        this.addEntry("SCREEN", 16384);
        this.addEntry("KBD", 24576);
        this.addEntry("R0", 0);
        this.addEntry("R1", 1);
        this.addEntry("R2", 2);
        this.addEntry("R3", 3);
        this.addEntry("R4", 4);
        this.addEntry("R5", 5);
        this.addEntry("R6", 6);
        this.addEntry("R7", 7);
        this.addEntry("R8", 8);
        this.addEntry("R9", 9);
        this.addEntry("R10", 10);
        this.addEntry("R11", 11);
        this.addEntry("R12", 12);
        this.addEntry("R13", 13);
        this.addEntry("R14", 14);
        this.addEntry("R15", 15);
    }

    @Override
    public void addEntry(String symbol, int address) {
        if (!this.contains(symbol)) {
            this.table.put(symbol, address);
        }
    }

    @Override
    public boolean contains(String symbol) {
        return this.table.containsKey(symbol);
    }

    @Override
    public int getAddress(String symbol) {
        if (this.isNumber(symbol)) {
            try {
                return Integer.parseInt(symbol);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (this.contains(symbol)) {
            return this.table.get(symbol);
        } else {
            int address = this.assignNewAddress();
            this.addEntry(symbol, address);
            return address;
        }
    }

    private int assignNewAddress() {
        varAddress++;
        return varAddress;
    }

    private boolean isNumber(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
