package com.company.model;

public enum Kind {
    STATIC("static"),
    FIELD("this"),
    ARG("argument"),
    VAR("local"),
    NONE(""),
    ;

    private String memory;

    Kind(String memory) {
        this.memory = memory;
    }

    public Segment toSegment() {
        switch (this) {
            case STATIC:
                return Segment.STATIC;
            case FIELD:
                return Segment.THIS;
            case ARG:
                return Segment.ARG;
            case VAR:
                return Segment.LOCAL;
            default:
                throw new RuntimeException("Kind.NONEに対応するSegmentはありません");
        }
    }
}
