package com.company;

import com.company.api.CompilationEngine;
import com.company.api.JackTokenizer;
import com.company.api.SymbolTable;
import com.company.api.VMWriter;
import com.company.impl.CompilationEngineImpl;
import com.company.impl.JackTokenizerImpl;
import com.company.impl.SymbolTableImpl;
import com.company.impl.VMWriterImpl;

import java.io.File;

public class JackCompiler {
    private final String dirPath;

    public JackCompiler(final String dirPath) {
        this.dirPath = dirPath;
    }

    public void compile() {
        File dir = new File(this.dirPath);
        for (File file : dir.listFiles()) {
            final String name = file.getName();
            if (name.contains(".jack")) {
                JackTokenizer tokenizer = new JackTokenizerImpl(file.toPath());
                VMWriter writer = new VMWriterImpl(this.dirPath, name.substring(0, name.length() - 5), false);
                SymbolTable table = new SymbolTableImpl();
                CompilationEngine compilationEngine = new CompilationEngineImpl(tokenizer, writer, table);
                try {
                    compilationEngine.compileClass();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                writer.close();
            }
        }
    }
}
