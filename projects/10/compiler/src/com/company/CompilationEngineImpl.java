package com.company;

import java.util.ArrayList;
import java.util.List;

public class CompilationEngineImpl implements CompilationEngine {

    private final JackTokenizer tokenizer;
    private final List<String> output;

    public CompilationEngineImpl(final JackTokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.output = new ArrayList<>();
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
        this.outputIdentifier(true);

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
    }

    @Override
    public void compileClassVarDec() {
        output.add("<classVarDec>");

        // STATICまたはFIELDを出力
        this.outputKeyword(false);

        // typeを出力
        this.compileType(true, false);

        // varNameを出力
        this.outputIdentifier(true);

        LOOP: while (true) {
            tokenizer.advance();
            final TokenType tokenType = tokenizer.tokenType();
            switch (tokenType) {
                case IDENTIFIER:
                    this.outputIdentifier(false);
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
        output.add("<subroutineDec>");
        this.outputKeyword(false);

        // typeを出力
        this.compileType(true, true);

        // identifierを出力
        this.outputIdentifier(true);

        // ( を出力
        this.outputSymbol(true);

        // ParameterListを出力
        this.compileParameterList();

        // ) を出力
        this.outputSymbol(true);

        output.add("<subroutineBody>");

        // { を出力
        this.outputSymbol(true);


        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            if (tokenizer.tokenType() == TokenType.KEYWORD && tokenizer.keyword() == KeywordType.VAR) {
                this.compileVarDec();
            } else {
                tokenizer.retreat();
                break;
            }
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
                this.compileType(true, false);

                // varNameを出力
                this.outputIdentifier(true);

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
    public void compileVarDec() {
        output.add("<varDec>");
        // var を出力
        this.outputKeyword(false);

        // typeを出力
        this.compileType(true, false);

        // ivarNameを出力
        this.outputIdentifier(true);

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
                    this.outputIdentifier(false);
                    break;
                default:
                    throw new RuntimeException("おかしトークン : " + tokenType);
            }
        }
        output.add("</varDec>");
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
        this.outputIdentifier(true);

        tokenizer.advance();
        final String symbol = tokenizer.symbol();

        if (symbol.equals("[")) {
            // [ を出力
            output.add("<symbol> " + symbol + " </symbol>");

            this.compileExpression();

            // ] を出力
            this.outputSymbol(true);
            tokenizer.advance();
        }

        // = を出力
        this.outputSymbol(false);

        this.compileExpression();

        // ; を出力
        this.outputSymbol(true);

        output.add("</letStatement>");
    }

    @Override
    public void compileWhile() {
        output.add("<whileStatement>");

        // while を出力
        this.outputKeyword(false);

        // ( を出力
        this.outputSymbol(true);

        this.compileExpression();

        // ) を出力
        this.outputSymbol(true);

        // { を出力
        this.outputSymbol(true);

        this.compileStatements();

        // } を出力
        this.outputSymbol(true);

        output.add("</whileStatement>");
    }

    @Override
    public void compileReturn() {
        output.add("<returnStatement>");

        // return を出力
        this.outputKeyword(false);

        tokenizer.advance();
        if (tokenizer.tokenType() == TokenType.SYMBOL && tokenizer.symbol().equals(";")) {
            // ; を出力
            this.outputSymbol(false);
        } else {
            tokenizer.retreat();
            this.compileExpression();

            // ; を出力
            this.outputSymbol(true);
        }

        output.add("</returnStatement>");
    }

    @Override
    public void compileIf() {
        output.add("<ifStatement>");

        // if を出力
        this.outputKeyword(false);

        // ( を出力
        this.outputSymbol(true);

        this.compileExpression();

        // ) を出力
        this.outputSymbol(true);

        // { を出力
        this.outputSymbol(true);

        this.compileStatements();

        // } を出力
        this.outputSymbol(true);

        tokenizer.advance();
        if (tokenizer.keyword() == KeywordType.ELSE) {

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
                symbol.equals("*") ||
                symbol.equals("/") ||
                symbol.equals("&") ||
                symbol.equals("|") ||
                symbol.equals("<") ||
                symbol.equals(">") ||
                symbol.equals("=")) {
                this.outputSymbol(false);
                this.compileTerm();
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
                if (keyword == KeywordType.TRUE ||
                    keyword == KeywordType.FALSE ||
                    keyword == KeywordType.NULL ||
                    keyword == KeywordType.THIS) {
                    this.outputKeyword(false);
                } else {
                    throw new RuntimeException("term : おかしなキーワード : " + keyword);
                }
                break;
            case SYMBOL:
                final String s = tokenizer.symbol();
                if (s.equals("-") || s.equals("~")) {
                    this.outputSymbol(false);
                    this.compileTerm();
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
                        this.outputIdentifier(false);

                        // [ を出力
                        this.outputSymbol(true);

                        this.compileExpression();

                        // ] を出力
                        this.outputSymbol(true);
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
                this.outputIdentifier(false);
                break;
        }
        output.add("</term>");
    }

    @Override
    public void compileExpressionList() {
        output.add("<expressionList>");
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
                    continue;
                }
            }
            tokenizer.retreat();
            this.compileExpression();
        }
        output.add("</expressionList>");
    }

    private void compileSubroutineCall() {
        // subRoutineName | className | varNameを出力
        this.outputIdentifier(true);

        tokenizer.advance();
        final String symbol = tokenizer.symbol();

        // class | var
        if (symbol.equals(".")) {
            // . を出力
            this.outputSymbol(false);

            // subRoutineNameを出力
            this.outputIdentifier(true);

            tokenizer.advance();
        }
        // ( を出力
        this.outputSymbol(false);

        this.compileExpressionList();

        // ）を出力
        this.outputSymbol(true);
    }

    private void compileType(boolean doAdvance, boolean addVoid) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final TokenType tokenType = tokenizer.tokenType();
        if (tokenType == TokenType.KEYWORD) {
            final KeywordType keyword = tokenizer.keyword();
            if (keyword == KeywordType.INT || keyword == KeywordType.BOOLEAN || keyword == KeywordType.CHAR) {
                this.outputKeyword(false);
            } else if (addVoid && keyword == KeywordType.VOID) {
                this.outputKeyword(false);
            } else {
                throw new RuntimeException("おかしい型宣言 : " + keyword);
            }
        } else if (tokenType == TokenType.IDENTIFIER){
            this.outputIdentifier(false);
        } else {
            throw new RuntimeException("おかしい型宣言 : " + tokenType);
        }
    }

    private void outputKeyword(boolean doAdvance) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final KeywordType keyword = tokenizer.keyword();
        output.add("<keyword> " + keyword.getName() + " </keyword>");
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

    private void outputIdentifier(boolean doAdvance) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final String identifier = tokenizer.identifier();
        output.add("<identifier> " + identifier + " </identifier>");
    }

    private void outputStringVal(boolean doAdvance) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final String stringVal = tokenizer.stringVal();
        output.add("<stringConstant> " + stringVal + " </stringConstant>");
    }

    private void outputIntVal(boolean doAdvance) {
        if (doAdvance) {
            tokenizer.advance();
        }
        final int intVal = tokenizer.intVal();
        output.add("<integerConstant> " + intVal + " </integerConstant>");
    }
}
