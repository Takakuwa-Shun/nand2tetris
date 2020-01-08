package com.company.model;

public enum KeywordType {
    CLASS("class"),
    METHOD("method"),
    FUNCTION("function"),
    CONSTRUCTOR("constructor"),
    INT("int"),
    BOOLEAN("boolean"),
    CHAR("char"),
    VOID("void"),
    VAR("var"),
    STATIC("static"),
    FIELD("field"),
    LET("let"),
    DO("do"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    RETURN("return"),
    TRUE("true"),
    FALSE("false"),
    NULL("null"),
    THIS("this"),
    ;

    private final String name;

    KeywordType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static KeywordType getKeywordType(final String name) {
        for (KeywordType type : KeywordType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new RuntimeException("該当するタイプがない : " + name);
    };

    public Kind toKind() {
        switch (this) {
            case FIELD:
                return Kind.FIELD;
            case STATIC:
                return Kind.STATIC;
            case VAR:
                return Kind.VAR;
            default:
                return Kind.NONE;
        }
    }
}
