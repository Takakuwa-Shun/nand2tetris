package com.company;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            Map.Entry<Path, Path> entry = Setting.getPath(Setting.STACK_TEST);
            vmEmulate(entry.getKey(), entry.getValue());
        } else {
            final String source = args[0];
        }
    }

    private static final void vmEmulate(final Path readPath, final Path writePath) {
        Parser parser = new ParserImpl(readPath);
        CodeWriter codeWriter = new CodeWriterImpl(false);
        codeWriter.setFileName(writePath);
        while (parser.hasMoreCommands()) {
            parser.advance();
            CommandType type = parser.commandType();
            switch (type) {
                case C_ARITHMETIC:
                    codeWriter.writeArithmetic(parser.arg1());
                    break;
                case C_PUSH:
                    codeWriter.writePushPop(CommandType.C_PUSH, parser.arg1(), parser.arg2());
                    break;
                case C_POP:
                    codeWriter.writePushPop(CommandType.C_POP, parser.arg1(), parser.arg2());
                    break;
                default:
                    throw new RuntimeException("おかしなコマンドタイプ：　" + type);
            }
        }
        codeWriter.close();
    }
}
