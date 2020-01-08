package com.company.impl;

import com.company.api.SymbolTable;
import com.company.model.Kind;
import com.company.model.SymbolTableElement;

import java.util.HashMap;
import java.util.Map;

public class SymbolTableImpl implements SymbolTable {

    private Map<String, SymbolTableElement> classMap;
    private Map<String, SymbolTableElement> subroutineMap;

    public SymbolTableImpl() {
        this.classMap = new HashMap<>();
        this.subroutineMap = new HashMap<>();
    }

    @Override
    public void startSubroutine() {
        this.subroutineMap.clear();
    }

    @Override
    public void define(String name, String type, Kind kind) {
        int index = this.varCount(kind);
        SymbolTableElement element = new SymbolTableElement(name, type, index, kind);

        if (kind == Kind.STATIC || kind == Kind.FIELD) {
            this.classMap.put(name, element);
        } else if (kind == Kind.ARG || kind == Kind.VAR) {
            this.subroutineMap.put(name, element);
        } else {
           throw new RuntimeException("[define] おかしなkind : " + kind);
        }
    }

    @Override
    public int varCount(Kind kind) {
        if (kind == Kind.STATIC || kind == Kind.FIELD) {
            return (int)this.classMap.values()
                    .stream()
                    .map(SymbolTableElement::getKind)
                    .filter(k -> k == kind)
                    .count();
        } else if (kind == Kind.ARG || kind == Kind.VAR) {
            return (int)this.subroutineMap.values()
                    .stream()
                    .map(SymbolTableElement::getKind)
                    .filter(k -> k == kind)
                    .count();
        } else {
            throw new RuntimeException("[define] おかしなkind : " + kind);
        }
    }

    @Override
    public Kind kindOf(String name) {
        if (this.subroutineMap.containsKey(name)) {
            return this.subroutineMap.get(name).getKind();
        } else if (this.classMap.containsKey(name)) {
            return this.classMap.get(name).getKind();
        } else {
            return Kind.NONE;
        }
    }

    @Override
    public String typeOf(String name) {
        if (this.subroutineMap.containsKey(name)) {
            return this.subroutineMap.get(name).getType();
        } else if (this.classMap.containsKey(name)) {
            return this.classMap.get(name).getType();
        } else {
            throw new RuntimeException("指定された識別子が見つかりません ; " + name);
        }
    }

    @Override
    public int indexOf(String name) {
        if (this.subroutineMap.containsKey(name)) {
            return this.subroutineMap.get(name).getIndex();
        } else if (this.classMap.containsKey(name)) {
            return this.classMap.get(name).getIndex();
        } else {
            throw new RuntimeException("指定された識別子が見つかりません ; " + name);
        }
    }

    @Override
    public String toString() {
        return "classMap=" + classMap +
                ", subroutineMap=" + subroutineMap;
    }
}
