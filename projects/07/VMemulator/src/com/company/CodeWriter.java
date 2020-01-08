package com.company;

import java.nio.file.Path;

public interface CodeWriter {
    void setFileName(final Path writePath);
    void writeArithmetic(final String command);
    void writePushPop(final CommandType command, final String segment, final int index);
    void close();
}
