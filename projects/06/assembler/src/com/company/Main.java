package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Path> map = Setting.getPath(Setting.RECT);
        List<String> binaryCommands = compile(map.get("read"));
        System.out.println(binaryCommands);

        Path path = map.get("write");
        // ファイルが存在するかの確認、存在しない場合は作成
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.write(path, binaryCommands, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final static List<String> compile(final Path path) {
        Parser parser = new ParserImpl(path);
        Code code = new CodeImpl();
        SymbolTable table = new SymbolTableImpl();
        int cntROM = -1;
        while (parser.hasMoreCommands()) {
            parser.advance();
            CommandType type = parser.commandType();
            switch (type) {
                case A_COMMAND:
                case C_COMMAND:
                    cntROM++;
                    break;
                case L_COMMAND:
                    String symbol = parser.symbol();
                    table.addEntry(symbol, cntROM+1);
                    break;
                default:
                    throw new RuntimeException("おかしなコマンドタイプ：　" + type);
            }
        }

        parser.clear();
        List<String> binaryCommands = new ArrayList<>();
        while (parser.hasMoreCommands()) {
            parser.advance();
            CommandType type = parser.commandType();
            String command;
            switch (type) {
                case A_COMMAND:
                    String symbol = parser.symbol();
                    int address = table.getAddress(symbol);
                    command = ConvertToBinaryCommand(address);
                    binaryCommands.add(command);
                    break;
                case C_COMMAND:
                    String destMnemonic = parser.dest();
                    String compMnemonic = parser.comp();
                    String jumpMnemonic = parser.jump();

                    byte dest =  code.dest(destMnemonic);
                    byte comp =  code.comp(compMnemonic);
                    byte jump =  code.jump(jumpMnemonic);
                    command = joinCommandC(dest, comp, jump);
                    binaryCommands.add(command);
                    break;
                case L_COMMAND:
                    break;
                default:
                    throw new RuntimeException("おかしなコマンドタイプ：　" + type);
            }
        }
        return binaryCommands;
    }

    private final static String joinCommandC(final byte dest, final byte comp, final byte jump) {
        String destStr = Integer.toBinaryString(dest);
        String compStr = Integer.toBinaryString(comp);
        String jumpStr = Integer.toBinaryString(jump);
        destStr = String.format("%3s", destStr).replace(' ', '0');
        compStr = String.format("%7s", compStr).replace(' ', '0');
        jumpStr = String.format("%3s", jumpStr).replace(' ', '0');

        return "111" + compStr + destStr + jumpStr;
    }

    private final static String ConvertToBinaryCommand(final int address) {
        String binary = Integer.toBinaryString(address);
        return String.format("%16s", binary).replace(' ', '0');
    }
}
