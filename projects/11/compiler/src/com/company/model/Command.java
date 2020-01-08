package com.company.model;

public enum Command {
    ADD("add"),
    SUB("sub"),
    NEG("neg"),
    EQ("eq"),
    GT("gt"),
    LT("lt"),
    AND("and"),
    OR("or"),
    NOT("not"),
    ;

    private String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Command getOPCommandBySymbol(final String symbol) {
        switch (symbol) {
            case "+":
                return ADD;
            case "-":
                return SUB;
            case "":
                return NEG;
            case "=":
                return EQ;
            case ">":
                return GT;
            case "<":
                return LT;
            case "&":
                return AND;
            case "|":
                return OR;
            default:
                throw new RuntimeException("不明なシンボル : " + symbol);
        }
    }

    public static Command getUnaryOPCommandBySymbol(final String symbol) {
        switch (symbol) {
            case "-":
                return NEG;
            case "~":
                return NOT;
            default:
                throw new RuntimeException("不明なシンボル : " + symbol);
        }
    }
}
