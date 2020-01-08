package com.company.impl;

import com.company.api.VMWriter;
import com.company.model.Command;
import com.company.model.Segment;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class VMWriterImpl implements VMWriter {

    private boolean outputXml;
    private final Path writeVMPath;
    private final Path writeXMLPath;
    private final List<String> vmOutputLines;
    private List<String> xmlOutputLines;

    public VMWriterImpl(final String dirPath, final String fileName, final boolean outputXml) {
        this.outputXml = outputXml;
        this.vmOutputLines = new ArrayList<>();
        this.xmlOutputLines = new ArrayList<>();
        this.writeVMPath = Paths.get(dirPath + "ans/" + fileName + ".vm");
        this.writeXMLPath = Paths.get(dirPath + "ans/" + fileName + ".xml");
    }

    @Override
    public void writePush(Segment segment, int index) {
        this.vmOutputLines.add("  push " + segment.getName() + ' ' + index);
    }

    @Override
    public void writePop(Segment segment, int index) {
        this.vmOutputLines.add("  pop " + segment.getName() + ' ' + index);
    }

    @Override
    public void writeArithmetic(Command command) {
        this.vmOutputLines.add("  " + command.getName());
    }

    @Override
    public void writeLabel(String label) {
        this.vmOutputLines.add("label " + label);
    }

    @Override
    public void writeGoto(String label) {
        this.vmOutputLines.add("  goto " + label);
    }

    @Override
    public void writeIf(String label) {
        this.vmOutputLines.add("  if-goto " + label);
    }

    @Override
    public void writeCall(String name, int nArgs) {
        this.vmOutputLines.add("  call " + name + ' ' + nArgs);
    }

    @Override
    public void writeFunction(String name, int nLocals) {
        this.vmOutputLines.add("function " + name + ' ' + nLocals);
    }

    @Override
    public void writeReturn() {
        this.vmOutputLines.add("  return");
    }

    @Override
    public void setXMLList(List<String> xmlOutput) {
        this.xmlOutputLines = xmlOutput;
    }

    @Override
    public void close() {
        if (this.outputXml) {
            this.createFile(this.writeXMLPath, this.xmlOutputLines);
        }
        this.createFile(this.writeVMPath, this.vmOutputLines);
    }

    private void createFile(final Path path, final List<String> outputLines) {
        // ファイルが存在するかの確認、存在する場合は削除
        if (Files.exists(path)) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------" + path.getFileName().toString() + "---------------------------");
        for (int i = 0; i< outputLines.size(); i++) {
            System.out.printf("%4d) ", i+1);
            System.out.println(outputLines.get(i));
        }
        try {
            Files.write(path, outputLines, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
