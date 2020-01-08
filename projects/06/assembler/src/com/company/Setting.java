package com.company;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public enum Setting {
    ADD,
    MAX,
    MAX_L,
    PONG,
    PONG_L,
    RECT,
    RECT_L,
    ;

    private static final String ADD_ASM = "/Users/takakuwashun/app/nand2tetris/projects/06/add/Add.asm";
    private static final String MAX_ASM = "/Users/takakuwashun/app/nand2tetris/projects/06/max/Max.asm";
    private static final String MAX_L_ASM = "/Users/takakuwashun/app/nand2tetris/projects/06/max/MaxL.asm";
    private static final String PONG_ASM = "/Users/takakuwashun/app/nand2tetris/projects/06/pong/Pong.asm";
    private static final String PONG_L_ASM = "/Users/takakuwashun/app/nand2tetris/projects/06/pong/PongL.asm";
    private static final String RECT_ASM = "/Users/takakuwashun/app/nand2tetris/projects/06/rect/Rect.asm";
    private static final String RECT_L_ASM = "/Users/takakuwashun/app/nand2tetris/projects/06/rect/RectL.asm";
    private static final String ADD_HACK = "/Users/takakuwashun/app/nand2tetris/projects/06/add/Add2.hack"; //ok
    private static final String MAX_HACK = "/Users/takakuwashun/app/nand2tetris/projects/06/max/Max2.hack";
    private static final String MAX_L_HACK = "/Users/takakuwashun/app/nand2tetris/projects/06/max/MaxL2.hack"; //ok
    private static final String PONG_HACK = "/Users/takakuwashun/app/nand2tetris/projects/06/pong/Pong2.hack";
    private static final String PONG_L_HACK = "/Users/takakuwashun/app/nand2tetris/projects/06/pong/PongL2.hack";
    private static final String RECT_HACK = "/Users/takakuwashun/app/nand2tetris/projects/06/rect/Rect2.hack";
    private static final String RECT_L_HACK = "/Users/takakuwashun/app/nand2tetris/projects/06/rect/RectL2.hack";

    public static final Map<String, Path> getPath(Setting setting) {
        Map<String, Path> map = new HashMap<>();
        switch (setting) {
            case ADD:
                map.put("read", Paths.get(ADD_ASM));
                map.put("write", Paths.get(ADD_HACK));
                break;
            case MAX:
                map.put("read", Paths.get(MAX_ASM));
                map.put("write", Paths.get(MAX_HACK));
                break;
            case MAX_L:
                map.put("read", Paths.get(MAX_L_ASM));
                map.put("write", Paths.get(MAX_L_HACK));
                break;
            case PONG:
                map.put("read", Paths.get(PONG_ASM));
                map.put("write", Paths.get(PONG_HACK));
                break;
            case PONG_L:
                map.put("read", Paths.get(PONG_L_ASM));
                map.put("write", Paths.get(PONG_L_HACK));
                break;
            case RECT:
                map.put("read", Paths.get(RECT_ASM));
                map.put("write", Paths.get(RECT_HACK));
                break;
            case RECT_L:
                map.put("read", Paths.get(RECT_L_ASM));
                map.put("write", Paths.get(RECT_L_HACK));
                break;
        }
        return map;
    }
};
