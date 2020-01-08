package com.company.model;

public class SymbolTableElement {
    private String name;
    private String type;
    private int index;
    private Kind kind;

    public SymbolTableElement(String name, String type, int index, Kind kind) {
        this.name = name;
        this.type = type;
        this.index = index;
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public Kind getKind() {
        return kind;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "{ name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", index=" + index +
                ", kind=" + kind +
                '}';
    }
}
