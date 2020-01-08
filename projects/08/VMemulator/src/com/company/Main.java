package com.company;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map.Entry<Path, Path> entry = Setting.getPath(Setting.NESTED_CALL);
        vmEmulate(entry.getKey().toString(), entry.getValue());
    }

    private static final void vmEmulate(final Path readPath, final Path writePath) {
        CodeWriter codeWriter = new CodeWriterImpl();
        codeWriter.setFileName(writePath);
        Parser parser = new ParserImpl(readPath);
        writeCode(codeWriter, parser);
        codeWriter.close();
    }

    private static final void vmEmulate(final String readPath, final Path writePath) {
        CodeWriter codeWriter = new CodeWriterImpl();
        codeWriter.setFileName(writePath);
        codeWriter.writeInit();
        File dir = new File(readPath);
        for (File file : dir.listFiles()) {
            final String name = file.getName();
            if (name.contains(".vm")) {
                Path path = file.toPath();
                Parser parser = new ParserImpl(path);
                writeCode(codeWriter, parser);
            }
        }
        codeWriter.close();
    }


    private static final void writeCode(final CodeWriter codeWriter, final Parser parser) {
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
                case C_LABEL:
                    codeWriter.writeLabel(parser.arg1());
                    break;
                case C_GOTO:
                    codeWriter.writeGoto(parser.arg1());
                    break;
                case C_IF:
                    codeWriter.writeIf(parser.arg1());
                    break;
                case C_CALL:
                    codeWriter.writeCall(parser.arg1(), parser.arg2());
                    break;
                case C_RETURN:
                    codeWriter.writeReturn();
                    break;
                case C_FUNCTION:
                    codeWriter.writeFunction(parser.arg1(), parser.arg2());
                    break;
                default:
                    throw new RuntimeException("おかしなコマンドタイプ：　" + type);
            }
        }
    }
}
