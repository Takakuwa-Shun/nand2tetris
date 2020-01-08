package com.company.impl;

import com.company.api.CompilationEngine;
import com.company.api.JackTokenizer;
import com.company.api.SymbolTable;
import com.company.api.VMWriter;
import com.company.model.*;

import java.util.ArrayList;
import java.util.List;

public class CompilationEngineImpl implements CompilationEngine {

    private String className;
    private int loopIdx;
    private int ifIdx;
    private final JackTokenizer tokenizer;
    private final VMWriter writer;
    private final SymbolTable table;
    private final List<String> output;

    public CompilationEngineImpl(final JackTokenizer tokenizer, final VMWriter writer, final SymbolTable table) {
        this.tokenizer = tokenizer;
        this.writer = writer;
        this.table = table;
        this.output = new ArrayList<>();
        this.loopIdx = 0;
        this.ifIdx = 0;
    }

    @Override
    public List<String> getOutput() {
        return this.output;
    }

    @Override
    public void compileClass() {
        // 始まりのクラスを出力
        output.add("<class>");

        // class を出力
        this.outputKeyword(true);

        // classNameを出力
        this.className = this.outputClassIdentifier(true, true);

        // { を出力
        this.outputSymbol(true);

        // クラスの中
        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            final TokenType tokenType = tokenizer.tokenType();
            if (tokenType == TokenType.SYMBOL && tokenizer.symbol().equals("}")) {
                // } を出力
                this.outputSymbol(false);
                break;
            }

            if (tokenType == TokenType.KEYWORD) {
                final KeywordType keyword = tokenizer.keyword();
                switch (keyword) {
                    case STATIC:
                    case FIELD:
                        this.compileClassVarDec();
                        break;
                    case CONSTRUCTOR:
                    case FUNCTION:
                    case METHOD:
                        this.compileSubroutine();
                        break;
                    default:
                        throw new RuntimeException("class宣言のあとでおかしいキーワード : " + keyword);
                }
            } else {
                throw new RuntimeException("class宣言のあとでおかしいトークンタイプ : " +tokenType);
            }
        }

        // 終わりのクラスを出力
        output.add("</class>");
        writer.setXMLList(output);
    }

    @Override
    public void compileClassVarDec() {
        output.add("<classVarDec>");

        // STATICまたはFIELDを出力
        KeywordType keywordType = this.outputKeyword(false);

        // typeを出力
        String type = this.compileType(true, false);

        // varNameを出力
        this.outputDefineIdentifier(true, type, keywordType.toKind());


        LOOP: while (true) {
            tokenizer.advance();
            final TokenType tokenType = tokenizer.tokenType();
            switch (tokenType) {
                case IDENTIFIER:
                    this.outputDefineIdentifier(false, type, keywordType.toKind());
                    break;
                case SYMBOL:
                    this.outputSymbol(false);
                    if (tokenizer.symbol().equals(";")) break LOOP;
                    break;
                default:
                    throw new RuntimeException("おかしなtype : " + tokenType);
            }
        }
        output.add("</classVarDec>");
    }

    @Override
    public void compileSubroutine() {
        this.table.startSubroutine();

        output.add("<subroutineDec>");
        final KeywordType keyword = this.outputKeyword(false);

        // typeを出力
        String type = this.compileType(true, true);

        // identifierを出力
        String subroutineName = this.outputSubroutineIdentifier(true, true, type);

        // ( を出力
        this.outputSymbol(true);

        // ParameterListを出力
        this.compileParameterList();

        // ) を出力
        this.outputSymbol(true);

        output.add("<subroutineBody>");

        // { を出力
        this.outputSymbol(true);

        int nLocals = 0;
        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            if (tokenizer.tokenType() == TokenType.KEYWORD && tokenizer.keyword() == KeywordType.VAR) {
                nLocals += this.compileVarDec();
            } else {
                tokenizer.retreat();
                break;
            }
        }
        this.writer.writeFunction(this.className + '.' + subroutineName, nLocals);

        switch (keyword) {
            case CONSTRUCTOR:
                final int fieldCnt = this.table.varCount(Kind.FIELD);
                this.writer.writePush(Segment.CONST, fieldCnt);
                this.writer.writeCall("Memory.alloc", 1);
                this.writer.writePop(Segment.POINTER, 0);
                break;
            case METHOD:
                this.writer.writePush(Segment.ARG, 0);
                this.writer.writePop(Segment.POINTER, 0);
                break;
            case FUNCTION:
                break;
            default:
                throw new RuntimeException("おかしなkeywordタイプ : " + keyword);
        }

        this.compileStatements();

        // } を出力
        this.outputSymbol(true);

        output.add("</subroutineBody>");
        output.add("</subroutineDec>");
    }

    @Override
    public void compileParameterList() {
        output.add("<parameterList>");
        while (true) {
            try {
                // typeを出力
                final String type = this.compileType(true, false);

                // varNameを出力
                this.outputArgIdentifier(true, type);

                tokenizer.advance();
                if (tokenizer.tokenType() == TokenType.SYMBOL && tokenizer.symbol().equals(",")) {
                    this.outputSymbol(false);
                } else {
                    tokenizer.retreat();
                    break;
                }
            } catch (Exception e) {
                tokenizer.retreat();
                break;
            }
        }
        output.add("</parameterList>");
    }

    @Override
    public int compileVarDec() {
        int nLocals = 0;
        output.add("<varDec>");
        // var を出力
        KeywordType keywordType = this.outputKeyword(false);

        // typeを出力
        String type = this.compileType(true, false);

        // varNameを出力
        this.outputDefineIdentifier(true, type, keywordType.toKind());
        nLocals++;

        LOOP: while (true) {
            tokenizer.advance();
            TokenType tokenType = tokenizer.tokenType();
            switch (tokenType) {
                case SYMBOL:
                    this.outputSymbol(false);
                    final String symbol = tokenizer.symbol();
                    if (symbol.equals(";")) break LOOP;
                    break;
                case IDENTIFIER:
                    this.outputDefineIdentifier(false, type, keywordType.toKind());
                    nLocals++;
                    break;
                default:
                    throw new RuntimeException("おかしトークン : " + tokenType);
            }
        }
        output.add("</varDec>");

        return nLocals;
    }

    @Override
    public void compileStatements() {
        output.add("<statements>");
        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            if (tokenizer.tokenType() != TokenType.KEYWORD) {
                tokenizer.retreat();
                break;
            }
            final KeywordType keyword = tokenizer.keyword();
            switch (keyword) {
                case LET:
                    this.compileLet();
                    break;
                case IF:
                    this.compileIf();
                    break;
                case WHILE:
                    this.compileWhile();
                    break;
                case DO:
                    this.compileDo();
                    break;
                case RETURN:
                    this.compileReturn();
                    break;
                default:
                    tokenizer.retreat();
                    break;
            }
        }
        output.add("</statements>");
    }

    @Override
    public void compileDo() {
        output.add("<doStatement>");

        // do を出力
        this.outputKeyword(false);

        this.compileSubroutineCall();
        this.writer.writePop(Segment.TEMP, 0);

        // ; を出力
        this.outputSymbol(true);

        output.add("</doStatement>");
    }

    @Override
    public void compileLet() {
        output.add("<letStatement>");

        // let を出力
        this.outputKeyword(false);

        // varNameを出力
        String varName = this.outputUsedIdentifier(true);

        tokenizer.advance();
        final String symbol = tokenizer.symbol();

        // 配列の場合
        if (symbol.equals("[")) {
            // [ を出力
            output.add("<symbol> " + symbol + " </symbol>");

            this.compileExpression();

            // ] を出力
            this.outputSymbol(true);
            tokenizer.advance();

            // = を出力
            this.outputSymbol(false);

            this.compileExpression();

            // = の左辺をtempに書き出す。
            this.writer.writePop(Segment.TEMP, 0);

            // thatのアドレスが、配列の指定位置を指すようにする。
            int idx = this.table.indexOf(varName);
            Kind kind = this.table.kindOf(varName);
            this.writer.writePush(kind.toSegment(), idx);
            this.writer.writeArithmetic(Command.ADD);
            this.writer.writePop(Segment.POINTER, 1);

            // = の左辺をtempに元に戻す
            this.writer.writePush(Segment.TEMP, 0);

            this.writer.writePop(Segment.THAT, 0);

        // 通常の変数の場合
        } else {
            // = を出力
            this.outputSymbol(false);

            this.compileExpression();

            int idx = this.table.indexOf(varName);
            Kind kind = this.table.kindOf(varName);
            this.writer.writePop(kind.toSegment(), idx);
        }

        // ; を出力
        this.outputSymbol(true);

        output.add("</letStatement>");
    }

    @Override
    public void compileWhile() {
        output.add("<whileStatement>");

        final String loopStartLabel = this.className + ".loop_start" + this.loopIdx;
        this.loopIdx++;
        final String loopBodyLabel = this.className + ".loop_body" + this.loopIdx;
        this.loopIdx++;
        final String loopEndLabel = this.className + ".loop_end" + this.loopIdx;
        this.loopIdx++;

        // while を出力
        this.outputKeyword(false);
        this.writer.writeLabel(loopStartLabel);

        // ( を出力
        this.outputSymbol(true);

        this.compileExpression();

        this.writer.writeIf(loopBodyLabel);
        this.writer.writeGoto(loopEndLabel);

        // ) を出力
        this.outputSymbol(true);

        // { を出力
        this.outputSymbol(true);

        this.writer.writeLabel(loopBodyLabel);

        this.compileStatements();

        // } を出力
        this.outputSymbol(true);

        this.writer.writeGoto(loopStartLabel);
        this.writer.writeLabel(loopEndLabel);

        output.add("</whileStatement>");
    }

    @Override
    public void compileReturn() {
        output.add("<returnStatement>");

        // return を出力
        this.outputKeyword(false);

        tokenizer.advance();
        if (tokenizer.tokenType() == TokenType.SYMBOL && tokenizer.symbol().equals(";")) {
            this.writer.writePush(Segment.CONST, 0);

            // ; を出力
            this.outputSymbol(false);
        } else {
            tokenizer.retreat();
            this.compileExpression();

            // ; を出力
            this.outputSymbol(true);
        }
        this.writer.writeReturn();
        output.add("</returnStatement>");
    }

    @Override
    public void compileIf() {
        output.add("<ifStatement>");

        final String ifLabel1 = this.className + ".if" + this.ifIdx;
        this.ifIdx++;
        final String elseLabel = this.className + ".else" + this.ifIdx;
        this.ifIdx++;
        final String ifEndLabel = this.className + ".if_end" + this.ifIdx;
        this.ifIdx++;

        // if を出力
        this.outputKeyword(false);

        // ( を出力
        this.outputSymbol(true);

        this.compileExpression();

        this.writer.writeIf(ifLabel1);
        this.writer.writeGoto(elseLabel);

        // ) を出力
        this.outputSymbol(true);

        // { を出力
        this.outputSymbol(true);

        this.writer.writeLabel(ifLabel1);

        this.compileStatements();

        this.writer.writeGoto(ifEndLabel);

        // } を出力
        this.outputSymbol(true);

        this.writer.writeLabel(elseLabel);

        tokenizer.advance();
        if (tokenizer.tokenType() == TokenType.KEYWORD && tokenizer.keyword() == KeywordType.ELSE) {

            // else を出力
            this.outputKeyword(false);

            // { を出力
            this.outputSymbol(true);

            this.compileStatements();

            // } を出力
            this.outputSymbol(true);
        } else {
            tokenizer.retreat();
        }
        this.writer.writeLabel(ifEndLabel);

        output.add("</ifStatement>");
    }

    @Override
    public void compileExpression() {
        output.add("<expression>");

        this.compileTerm();

        while (true) {
            tokenizer.advance();
            TokenType tokenType = tokenizer.tokenType();
            if (tokenType != TokenType.SYMBOL) {
                tokenizer.retreat();
                break;
            }
            final String symbol = tokenizer.symbol();
            if (symbol.equals("+") ||
                symbol.equals("-") ||
                symbol.equals("&") ||
                symbol.equals("|") ||
                symbol.equals("<") ||
                symbol.equals(">") ||
                symbol.equals("=")) {
                this.outputSymbol(false);
                this.compileTerm();
                this.writer.writeArithmetic(Command.getOPCommandBySymbol(symbol));
            } else if (symbol.equals("*") || symbol.equals("/")) {
                this.outputSymbol(false);
                this.compileTerm();
                if (symbol.equals("*")) {
                    this.writer.writeCall("Math.multiply", 2);
                } else {
                    this.writer.writeCall("Math.divide", 2);
                }
            } else {
                tokenizer.retreat();
                break;
            }
        }
        output.add("</expression>");
    }

    @Override
    public void compileTerm() {
        output.add("<term>");

        this.tokenizer.advance();
        final TokenType tokenType = tokenizer.tokenType();

        switch (tokenType) {
            case INT_CONST:
                this.outputIntVal(false);
                break;
            case STRING_CONST:
                this.outputStringVal(false);
                break;
            case KEYWORD:
                final KeywordType keyword = tokenizer.keyword();
                switch (keyword) {
                    case TRUE:
                        this.writer.writePush(Segment.CONST, 1);
                        this.writer.writeArithmetic(Command.NEG);
                        this.outputKeyword(false);
                        break;
                    case FALSE:
                    case NULL:
                        this.writer.writePush(Segment.CONST, 0);
                        this.outputKeyword(false);
                        break;
                    case THIS:
                        this.writer.writePush(Segment.POINTER, 0);
                        this.outputKeyword(false);
                        break;
                    default:
                        throw new RuntimeException("term : おかしなキーワード : " + keyword);
                }
                break;
            case SYMBOL:
                final String s = tokenizer.symbol();
                if (s.equals("-") || s.equals("~")) {
                    this.outputSymbol(false);
                    this.compileTerm();
                    this.writer.writeArithmetic(Command.getUnaryOPCommandBySymbol(s));
                } else if (s.equals("(")) {

                    // ( を出力
                    this.outputSymbol(false);

                    this.compileExpression();

                    // ) を出力
                    this.outputSymbol(true);
                } else {
                    throw new RuntimeException("term : おかしなsymbol : " + s);
                }
                break;
            case IDENTIFIER:
                tokenizer.advance();
                if (tokenizer.tokenType() == TokenType.SYMBOL) {
                    final String symbol = tokenizer.symbol();
                    if (symbol.equals("[")) {

                        // varNameを出力
                        tokenizer.retreat();
                        final String arrayName = this.outputUsedIdentifier(false);
                        int idx = this.table.indexOf(arrayName);
                        Segment segment = this.table.kindOf(arrayName).toSegment();


                        // [ を出力
                        this.outputSymbol(true);

                        this.compileExpression();

                        // ] を出力
                        this.outputSymbol(true);

                        // thatアドレスに配列の指定の位置を設定
                        this.writer.writePush(segment, idx);
                        this.writer.writeArithmetic(Command.ADD);
                        this.writer.writePop(Segment.POINTER, 1);

                        this.writer.writePush(Segment.THAT, 0);
                        break;
                    } else if (symbol.equals("(") || symbol.equals(".")) {
                        tokenizer.retreat();
                        tokenizer.retreat();
                        this.compileSubroutineCall();
                        break;
                    }
                }
                // varNameを出力
                tokenizer.retreat();
                String varName = this.outputUsedIdentifier(false);
                int index = this.table.indexOf(varName);
                Kind kind = this.table.kindOf(varName);
                writer.writePush(kind.toSegment(), index);
                break;
        }
        output.add("</term>");
    }

    @Override
    public int compileExpressionList() {
        output.add("<expressionList>");

        int nArgs = 0;
        while (true) {
            tokenizer.advance();
            if (tokenizer.tokenType() == TokenType.SYMBOL) {
                final String symbol = tokenizer.symbol();
                if (symbol.equals(")")) {
                    tokenizer.retreat();
                    break;
                } else if (symbol.equals(",")) {
                    this.outputSymbol(false);
                    this.compileExpression();
                    nArgs++;
                    continue;
                }
            }
            tokenizer.retreat();
            this.compileExpression();
            nArgs++;
        }
        output.add("</expressionList>");
        return nArgs;
    }

    private void compileSubroutineCall() {
        tokenizer.advance();

        // symbolを判定
        tokenizer.advance();
        final String symbol = tokenizer.symbol();

        // class | var
        if (symbol.equals(".")) {
            // className | varNameを出力
            tokenizer.retreat();
            String name = this.outputClassIdentifier(false, false);
            final Kind kind = this.table.kindOf(name);

            // . を出力
            this.outputSymbol(true);

            String subroutineName = this.outputSubroutineIdentifier(true, false, "");

            // ( を出力
            this.outputSymbol(true);

            // varNameの場合の、インスタンスのアドレスをpushする。
            if (kind != Kind.NONE) {
                int idx = this.table.indexOf(name);
                this.writer.writePush(kind.toSegment(), idx);
                name = this.table.typeOf(name);
            }
            int nArgs = this.compileExpressionList();

            // varNameの場合
            if (kind != Kind.NONE) nArgs++;

            this.writer.writeCall(name + '.' + subroutineName, nArgs);
        }
        // メソッドを出力
        else {
            tokenizer.retreat();
            String subroutineName = this.outputSubroutineIdentifier(false, false, "");

            // ( を出力
            this.outputSymbol(true);

            this.writer.writePush(Segment.POINTER, 0);
            int nArgs = this.compileExpressionList();
            this.writer.writeCall(this.className + '.' + subroutineName, nArgs+1);
        }

        // ）を出力
        this.outputSymbol(true);
    }

    private String compileType(boolean doAdvance, boolean addVoid) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final TokenType tokenType = tokenizer.tokenType();
        if (tokenType == TokenType.KEYWORD) {
            final KeywordType keyword = tokenizer.keyword();
            if (keyword == KeywordType.INT || keyword == KeywordType.BOOLEAN || keyword == KeywordType.CHAR) {
                return this.outputKeyword(false).getName();
            } else if (addVoid && keyword == KeywordType.VOID) {
                return this.outputKeyword(false).getName();
            } else {
                throw new RuntimeException("おかしい型宣言 : " + keyword);
            }
        } else if (tokenType == TokenType.IDENTIFIER){
            return this.outputClassIdentifier(false, false);
        } else {
            throw new RuntimeException("おかしい型宣言 : " + tokenType);
        }
    }

    private KeywordType outputKeyword(boolean doAdvance) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final KeywordType keyword = tokenizer.keyword();
        output.add("<keyword> " + keyword.getName() + " </keyword>");
        return keyword;
    }

    private void outputSymbol(boolean doAdvance) {
        if (doAdvance) {
            tokenizer.advance();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<symbol> ");
        final String symbol = tokenizer.symbol();
        switch (symbol) {
            case "<":
                sb.append("&lt;");
                break;
            case ">":
                sb.append("&gt;");
                break;
            case "&":
                sb.append("&amp;");
                break;
            default:
                sb.append(symbol);
        }
        sb.append(" </symbol>");
        output.add(sb.toString());
    }

    private String outputUsedIdentifier(boolean doAdvance) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final String identifier = tokenizer.identifier();

        final Kind kind = this.table.kindOf(identifier);
        if (kind == Kind.NONE) {
            throw new RuntimeException("未定義のidentifier : " + identifier);
        }
        final String type = this.table.typeOf(identifier);
        final int index = this.table.indexOf(identifier);
        StringBuilder sb = new StringBuilder();
        sb.append("<identifier> ");
        sb.append(identifier);
        sb.append(", used, kind = ");
        sb.append(kind);
        sb.append(", type = ");
        sb.append(type);
        sb.append(", idx = ");
        sb.append(index);
        sb.append(" </identifier>");
        output.add(sb.toString());
        return identifier;
    }

    private String outputArgIdentifier(boolean doAdvance, String type) {
        return this.outputDefineIdentifier(doAdvance, type, Kind.ARG);
    }

    private String outputDefineIdentifier(boolean doAdvance, String type, Kind kind) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final String identifier = tokenizer.identifier();

        table.define(identifier, type, kind);
        final int index = this.table.indexOf(identifier);
        StringBuilder sb = new StringBuilder();
        sb.append("<identifier> ");
        sb.append(identifier);
        sb.append(", defined, kind = ");
        sb.append(kind);
        sb.append(", type = ");
        sb.append(type);
        sb.append(", idx = ");
        sb.append(index);
        sb.append(" </identifier>");
        output.add(sb.toString());
        return identifier;
    }

    private String outputClassIdentifier(boolean doAdvance, boolean define) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final String identifier = tokenizer.identifier();
        if (define) {
            output.add("<identifier> " + identifier + ", defined, kind = class </identifier>");
        } else {
            output.add("<identifier> " + identifier + ", used, kind = class </identifier>");
        }
        return identifier;
    }

    private String outputSubroutineIdentifier(boolean doAdvance, boolean define, String type) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final String identifier = tokenizer.identifier();
        if (define) {
            output.add("<identifier> " + identifier + ", defined, kind = subroutine, type = " + type + " </identifier>");
        } else {
            output.add("<identifier> " + identifier + ", used, kind = subroutine, type = " + type + " </identifier>");
        }
        return identifier;
    }

    private void outputStringVal(boolean doAdvance) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final String stringVal = tokenizer.stringVal();
        output.add("<stringConstant> " + stringVal + " </stringConstant>");

        this.writer.writePush(Segment.CONST, stringVal.length());
        this.writer.writeCall("String.new", 1);
        for (int i=0; i<stringVal.length(); i++) {
            int unicode = (int)stringVal.charAt(i);
            this.writer.writePush(Segment.CONST, unicode);
            this.writer.writeCall("String.appendChar", 2);
        }
    }

    private void outputIntVal(boolean doAdvance) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final int intVal = tokenizer.intVal();
        output.add("<integerConstant> " + intVal + " </integerConstant>");
        this.writer.writePush(Segment.CONST, intVal);
    }
}
