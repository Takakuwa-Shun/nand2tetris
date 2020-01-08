package com.company.api;

import com.company.model.Command;
import com.company.model.Segment;

import java.util.List;

public interface VMWriter {
    void writePush(final Segment segment, final int index);
    void writePop(final Segment segment, final int index);
    void writeArithmetic(final Command command);
    void writeLabel(final String label);
    void writeGoto(final String label);
    void writeIf(final String label);
    void writeCall(final String name, final int nArgs);
    void writeFunction(final String name, final int nLocals);
    void writeReturn();
    void setXMLList(final List<String> xmlOutput);
    void close();
}
