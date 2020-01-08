package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
        if (!this.hasMoreCommands()) {
            throw new RuntimeException("コマンドはもうない  nextRow : " + this.nextRow);
        }
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

    @Override
    public CommandType commandType() {
        if (this.command.startsWith("push")) {
            return CommandType.C_PUSH;
        }

        if (this.command.startsWith("pop")) {
            return CommandType.C_POP;
        }

        if (this.command.startsWith("goto")) {
            return CommandType.C_GOTO;
        }

        if (this.command.startsWith("if-goto")) {
            return CommandType.C_IF;
        }

        if (this.command.startsWith("label")) {
            return CommandType.C_LABEL;
        }

        if (this.command.startsWith("function")) {
            return CommandType.C_FUNCTION;
        }

        if (this.command.startsWith("call")) {
            return CommandType.C_CALL;
        }

        if (this.command.equals("return")) {
            return CommandType.C_RETURN;
        }

        if (this.command.equals("add") ||
            this.command.equals("sub") ||
            this.command.equals("neg") ||
            this.command.equals("eq") ||
            this.command.equals("gt") ||
            this.command.equals("lt") ||
            this.command.equals("and") ||
            this.command.equals("or") ||
            this.command.equals("not")) {
            return CommandType.C_ARITHMETIC;
        }

        throw new RuntimeException("謎のコマンドタイプ : " + this.command);
    }

    @Override
    public String arg1() {
        CommandType type = this.commandType();
        switch (type) {
            case C_RETURN:
                throw new RuntimeException("C_RETURNの時、arg1メソッドを呼んではいけません");
            case C_ARITHMETIC:
                return this.command;
            default:
                final int firstBlankIdx = this.command.indexOf(' ');
                int arg1StartIdx = firstBlankIdx + 1;
                while (arg1StartIdx < this.command.length()-1 && this.command.charAt(arg1StartIdx) == ' ') {
                    arg1StartIdx++;
                }
                int arg1EndIdx = arg1StartIdx + 1;
                while (arg1EndIdx < this.command.length()-1 && this.command.charAt(arg1EndIdx) != ' ') {
                    arg1EndIdx++;
                }
                if (arg1StartIdx >= this.command.length() || arg1EndIdx > this.command.length()) {
                    throw new RuntimeException("arg1が見つかりませんでした : " + this.command + ", s=" + arg1StartIdx + ", e=" +arg1EndIdx);
                }
                return this.command.substring(arg1StartIdx, arg1EndIdx);
        }
    }

    @Override
    public int arg2() {
        CommandType type = this.commandType();
        switch (type) {
            case C_PUSH:
            case C_POP:
            case C_FUNCTION:
            case C_CALL:
                final String arg1 = this.arg1();
                final int arg1EndIdx = this.command.indexOf(arg1) + arg1.length();
                int arg2StartIdx = arg1EndIdx + 1;
                while (arg2StartIdx < this.command.length()-1 && this.command.charAt(arg2StartIdx) == ' ') {
                    arg2StartIdx++;
                }
                final String arg2 = this.command.substring(arg2StartIdx);
                return Integer.parseInt(arg2);
            default:
                throw new RuntimeException("arg2メソッドを呼んではいけません : " + type);
        }
    }
}
