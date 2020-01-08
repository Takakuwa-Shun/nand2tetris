package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class JackAnalyzer {
    private final String dirPath;
    private final boolean onlyTokenizer;

    public JackAnalyzer(final String dirPath, final boolean onlyTokenizer) {
        this.dirPath = dirPath;
        this.onlyTokenizer = onlyTokenizer;
    }

    public void compile() {
        File dir = new File(this.dirPath);
        for (File file : dir.listFiles()) {
            final String name = file.getName();
            if (name.contains(".jack")) {
                Path readPath = file.toPath();
                JackTokenizer tokenizer = new JackTokenizerImpl(readPath);
                List<String> outputLines;
                System.out.println("---------------------------- " + name + " ----------------------------");
                if (onlyTokenizer) {
                    outputLines = this.outputTokenizerResult(tokenizer);
                } else {
                    CompilationEngine compilationEngine = new CompilationEngineImpl(tokenizer);
                    try {
                        compilationEngine.compileClass();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    outputLines = compilationEngine.getOutput();
                }
                Path writePath = this.createFile(name.substring(0, name.length() - 5));
                this.writeFile(outputLines, writePath);
            }
        }
    }

    private List<String> outputTokenizerResult(final JackTokenizer tokenizer) {
        List<String> output = new ArrayList<>();
        output.add("<tokens>");
        StringBuilder sb = new StringBuilder();
        while (tokenizer.hasMoreTokens()) {
            sb.setLength(0);
            tokenizer.advance();
            TokenType tokenType = tokenizer.tokenType();
            switch (tokenType) {
                case SYMBOL:
                    sb.append("<symbol> ");
                    String symbol = tokenizer.symbol();
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
                    break;
                case KEYWORD:
                    sb.append("<keyword> ");
                    sb.append(tokenizer.keyword().getName());
                    sb.append(" </keyword>");
                    break;
                case INT_CONST:
                    sb.append("<integerConstant> ");
                    sb.append(tokenizer.intVal());
                    sb.append(" </integerConstant>");
                    break;
                case IDENTIFIER:
                    sb.append("<identifier> ");
                    sb.append(tokenizer.identifier());
                    sb.append(" </identifier>");
                    break;
                case STRING_CONST:
                    sb.append("<stringConstant> ");
                    sb.append(tokenizer.stringVal());
                    sb.append(" </stringConstant>");
                    break;
                default:
                    throw new RuntimeException("おかしなトークンタイプ：　" + tokenType);
            }
            output.add(sb.toString());
        }
        output.add("</tokens>");
        return output;
    }

    private Path createFile(final String fileName) {
        final Path writePath;
        if (this.onlyTokenizer) {
            writePath = Paths.get(this.dirPath + fileName + "_ansT.xml");
        } else {
            writePath = Paths.get(this.dirPath + fileName + "_ans.xml");
        }

        // ファイルが存在するかの確認、存在する場合は削除
        if (Files.exists(writePath)) {
            try {
                Files.deleteIfExists(writePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.createFile(writePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writePath;
    }

    private void writeFile(final List<String> outputLines, final Path writePath) {
        for (int i=0; i<outputLines.size(); i++) {
            System.out.println((i+1) + ") " + outputLines.get(i));
        }
        try {
            Files.write(writePath, outputLines, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
