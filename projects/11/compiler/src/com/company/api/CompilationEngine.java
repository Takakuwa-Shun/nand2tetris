package com.company.api;

import java.util.List;

public interface CompilationEngine {
    void compileClass();
    void compileClassVarDec();
    void compileSubroutine();
    void compileParameterList();
    int compileVarDec();
    void compileStatements();
    void compileDo();
    void compileLet();
    void compileWhile();
    void compileReturn();
    void compileIf();
    void compileExpression();
    void compileTerm();
    int compileExpressionList();
    List<String> getOutput();
}
