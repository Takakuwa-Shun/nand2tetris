package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParserImpl implements Parser {

    private List<String> programs;
    private String command;
    private int nextRow;

    public ParserImpl(final Path path) {
        this.nextRow = 0;
        try {
            this.programs = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        this.command = null;
        this.nextRow = 0;
    }

    @Override
    public boolean hasMoreCommands() {
        int row = nextRow;
        if (row >= programs.size()) return false;

        String line = programs.get(row).trim();
        while(row < programs.size() && (line.startsWith("//") || line.equals(""))) {
            row++;
            line = programs.get(row).trim();
        }
        return row < programs.size();
    }

    @Override
    public void advance() {
        if (this.hasMoreCommands()) {
            this.command = programs.get(nextRow).trim();
            while(nextRow < programs.size() && (this.command.startsWith("//") || this.command.equals(""))) {
                nextRow++;
                this.command = programs.get(nextRow).trim();
            }
            int commentIdx = this.command.indexOf("//");
            if (commentIdx != -1) {
                this.command = this.command.substring(0, commentIdx).trim();
            }
            nextRow++;
        }
    }

    @Override
    public CommandType commandType() {
        char start = this.command.charAt(0);
        switch (start) {
            case '@':
                return CommandType.A_COMMAND;
            case '(':
                return CommandType.L_COMMAND;
            default:
                return CommandType.C_COMMAND;
        }
    }

    @Override
    public String symbol() {
        CommandType type = this.commandType();
        switch (type) {
            case C_COMMAND:
                throw new RuntimeException("C_COMMANDです。Symbolはありません。");
            case L_COMMAND:
                return this.command.substring(1, this.command.length()-1);
            case A_COMMAND:
                return this.command.substring(1);
            default:
                throw new RuntimeException("CommandTypeがおかしな値になっています。 : " + type);
        }
    }

    @Override
    public String dest() {
        CommandType type = this.commandType();
        if (type != CommandType.C_COMMAND) {
            throw new RuntimeException("A_COMMANDまたはL_COMMANDです。");
        }
        int endIdx = this.command.indexOf("=");
        if (endIdx < 0) return "null";
        while (this.command.charAt(endIdx-1) == ' ') endIdx--;
        return this.command.substring(0, endIdx);
    }

    @Override
    public String comp() {
        CommandType type = this.commandType();
        if (type != CommandType.C_COMMAND) {
            throw new RuntimeException("A_COMMANDまたはL_COMMANDです。");
        }
        int startIdx = this.command.indexOf("=") + 1;
        if (startIdx == -1) startIdx = 0;
        while (this.command.charAt(startIdx) == ' ') startIdx++;
        int endIdx = this.command.indexOf(";");
        if (endIdx == -1) endIdx = this.command.length();
        while (this.command.charAt(endIdx-1) == ' ') endIdx--;
        return this.command.substring(startIdx, endIdx);
    }

    @Override
    public String jump() {
        CommandType type = this.commandType();
        if (type != CommandType.C_COMMAND) {
            throw new RuntimeException("A_COMMANDまたはL_COMMANDです。");
        }
        int idx = this.command.indexOf(";");
        if (idx == -1) return "null";

        String jump = this.command.substring(idx+1);
        if (jump.length() != 3) throw new RuntimeException("jumpの文字列の長さがおかしい : " + jump);
        return jump;
    }
}
