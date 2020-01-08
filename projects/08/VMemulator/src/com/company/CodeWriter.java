package com.company;

import java.nio.file.Path;

public interface CodeWriter {
    void setFileName(final Path writePath);
    void writeArithmetic(final String command);
    void writePushPop(final CommandType command, final String segment, final int index);
    void close();

    void writeInit();
    void writeLabel(final String label);
    void writeGoto(final String label);
    void writeIf(final String label);
    void writeCall(final String functionName, final int numArgs);
    void writeReturn();
    void writeFunction(final String functionName, final int numLocals);
}
