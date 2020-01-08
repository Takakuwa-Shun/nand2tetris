package com.company.model;

public enum Segment {
    CONST("constant"),
    ARG("argument"),
    LOCAL("local"),
    STATIC("static"),
    THIS("this"),
    THAT("that"),
    POINTER("pointer"),
    TEMP("temp"),
    ;

    private String name;

    Segment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
