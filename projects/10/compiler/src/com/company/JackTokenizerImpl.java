package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JackTokenizerImpl implements JackTokenizer {

    private List<String> tokens;
    private String token;
    private int nextTokenIdx;
    private Set<String> keywordSet;
    private Set<String> symbolSet;

    public JackTokenizerImpl(final Path path) {
        this.nextTokenIdx = 0;
        this.initTokenType();

        try {
            List<String> lines = Files.readAllLines(path);
            List<String> tokensWithComment = getTokensWithComment(lines);
            this.tokens = this.getToken(tokensWithComment);
//            System.out.println(String.join("\n", tokens));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTokenType() {
        this.keywordSet = new HashSet<>();
        for (KeywordType type : KeywordType.values()) {
            this.keywordSet.add(type.getName());
        }

        this.symbolSet = new HashSet<>();
        this.symbolSet.add("{");
        this.symbolSet.add("}");
        this.symbolSet.add("(");
        this.symbolSet.add(")");
        this.symbolSet.add("[");
        this.symbolSet.add("]");
        this.symbolSet.add(".");
        this.symbolSet.add(",");
        this.symbolSet.add(";");
        this.symbolSet.add("+");
        this.symbolSet.add("-");
        this.symbolSet.add("*");
        this.symbolSet.add("/");
        this.symbolSet.add("&");
        this.symbolSet.add("|");
        this.symbolSet.add("<");
        this.symbolSet.add(">");
        this.symbolSet.add("=");
        this.symbolSet.add("~");
    }

    /**
     * トークン候補
     * @param lines ファイルを1行ずつで区切ったリスト
     * @return
     */
    private List<String> getTokensWithComment(final List<String> lines) {
        List<String> tokenWithComment = new ArrayList<>();
        for (String line : lines) {
            String trimLine = line.trim();
            if (trimLine.startsWith("//") || trimLine.equals("")) continue;
            final int commentStartIdx = trimLine.indexOf("//");
            if (commentStartIdx != -1) {
                trimLine = trimLine.substring(0, commentStartIdx).trim();
            }
            tokenWithComment.addAll(this.splitLine(trimLine));
        }
        return tokenWithComment;
    }

    /**
     * /**のコメント及び空文字のトークンリストをフィルタリングする。
     * @param tokensWithComment
     * @return
     */
    private List<String> getToken(final List<String> tokensWithComment) {
        List<String> tokens = new ArrayList<>();
        boolean isComment = false;
        for (String token : tokensWithComment) {
            if (token.equals("")) continue;

            if (token.startsWith("/*")) {
                isComment = true;
                continue;
            }
            if (token.equals("*/")) {
                isComment = false;
                continue;
            }
            if (isComment) continue;
            tokens.add(token);
        }
        return tokens;
    }

    /**
     * 1行の文字列をトークンに切り分ける。
     * @param line
     * @return
     */
    private List<String> splitLine(final String line) {
        List<String> tokens = new ArrayList<>();
        int preIdx = 0;
        int i = 0;
        boolean isString = false;
        while (i < line.length()) {
            char c = line.charAt(i);
            if (isString) {
                if (c == '\"') {
                    isString = false;
                    tokens.add(line.substring(preIdx, i+1).trim());
                    preIdx = i+1;
                }
            } else {
                switch (c) {
                    case '\"':
                        isString = true;
                        if (preIdx != i) {
                            tokens.add(line.substring(preIdx, i).trim());
                        }
                        preIdx = i;
                        break;
                    case ' ':
                        if (preIdx != i) {
                            tokens.add(line.substring(preIdx, i).trim());
                        }
                        preIdx = i+1;
                        break;
                    case '{':
                    case '}':
                    case '(':
                    case ')':
                    case '[':
                    case ']':
                    case '.':
                    case ',':
                    case ';':
                    case '+':
                    case '-':
                    case '>':
                    case '<':
                    case '&':
                    case '|':
                    case '=':
                    case '~':
                        if (preIdx != i) {
                            tokens.add(line.substring(preIdx, i).trim());
                        }
                        tokens.add(line.substring(i, i+1).trim());
                        preIdx = i+1;
                        break;
                    case '/':
                        if (preIdx != i) {
                            tokens.add(line.substring(preIdx, i).trim());
                        }
                        if (i+2 < line.length() && line.charAt(i+1) == '*' && line.charAt(i+2) == '*') {
                            tokens.add(line.substring(i, i+3).trim());
                            preIdx = i+3;
                            i++;
                            i++;
                        } else if (i+1 < line.length() && line.charAt(i+1) == '*') {
                            tokens.add(line.substring(i, i+2).trim());
                            preIdx = i+2;
                            i++;
                        } else {
                            tokens.add(line.substring(i, i+1).trim());
                            preIdx = i+1;
                        }
                        break;
                    case '*':
                        if (preIdx != i) {
                            tokens.add(line.substring(preIdx, i).trim());
                        }
                        if (i+1 < line.length() && line.charAt(i+1) == '/') {
                            tokens.add(line.substring(i, i+2).trim());
                            preIdx = i+2;
                            i++;
                        } else {
                            tokens.add(line.substring(i, i+1).trim());
                            preIdx = i+1;
                        }
                        break;
                }
            }
            i++;
        }
        if (preIdx != line.length()) {
            tokens.add(line.substring(preIdx).trim());
        }
        return tokens;
    }

    @Override
    public boolean hasMoreTokens() {
        return this.nextTokenIdx < this.tokens.size();
    }

    @Override
    public void advance() {
        if (!this.hasMoreTokens()) {
            throw new RuntimeException("コマンドはもうない  nextTokenIdx : " + this.nextTokenIdx);
        }
        this.token = this.tokens.get(this.nextTokenIdx);
        this.nextTokenIdx++;
    }

    @Override
    public void retreat() {
        this.nextTokenIdx--;
        this.token = this.tokens.get(this.nextTokenIdx - 1);
    }

    @Override
    public TokenType tokenType() {
        if (this.keywordSet.contains(this.token)) {
            return TokenType.KEYWORD;
        }

        if (this.symbolSet.contains(this.token)) {
            return TokenType.SYMBOL;
        }

        if (this.token.startsWith("\"") && this.token.endsWith("\"")) {
            return TokenType.STRING_CONST;
        }

        try {
            Integer.parseInt(this.token);
            return TokenType.INT_CONST;
        } catch (NumberFormatException e) {
            return TokenType.IDENTIFIER;
        }
    }

    @Override
    public KeywordType keyword() {
        if (this.tokenType() != TokenType.KEYWORD) {
            throw new RuntimeException("KEYWORDのトークンではありません : " + this.token);
        }
        return KeywordType.getKeywordType(this.token);
    }

    @Override
    public String symbol() {
        if (this.tokenType() != TokenType.SYMBOL) {
            throw new RuntimeException("SYMBOLのトークンではありません : " + this.token);
        }
        return this.token;
    }

    @Override
    public String identifier() {
        if (this.tokenType() != TokenType.IDENTIFIER) {
            throw new RuntimeException("IDENTIFIERのトークンではありません : " + this.token);
        }
        return this.token;
    }

    @Override
    public int intVal() {
        if (this.tokenType() != TokenType.INT_CONST) {
            throw new RuntimeException("INT_CONSTのトークンではありません : " + this.token);
        }
        try {
            return Integer.parseInt(this.token);
        } catch (NumberFormatException e) {
            throw new RuntimeException("数値じゃねーの？？ : " + this.token);
        }
    }

    @Override
    public String stringVal() {
        if (this.tokenType() != TokenType.STRING_CONST) {
            throw new RuntimeException("STRING_CONSTのトークンではありません : " + this.token);
        }
        return this.token.substring(1, this.token.length()-1);
    }
}
