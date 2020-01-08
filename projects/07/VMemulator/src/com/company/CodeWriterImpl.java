package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CodeWriterImpl implements CodeWriter {

    private Path writePath;
    private int arithmeticIdx;
    private String fileName;
    private List<String> assemblyCommands;
    private final boolean doComment;

    public CodeWriterImpl(boolean doComment) {
        this.doComment = doComment;
    }

    @Override
    public void setFileName(final Path writePath) {
        this.assemblyCommands = new ArrayList<>();
        this.arithmeticIdx = 0;
        this.writePath = writePath;
        final String[] paths = writePath.toString().split("/");
        final String fileNameWithExtension = paths[paths.length-1];
        this.fileName = fileNameWithExtension.substring(0, fileNameWithExtension.length()-4);
    }

    @Override
    public void writeArithmetic(String command) {

        if (command.equals("neg") || command.equals("not")) {
            // 初期状態時、例として、SP = 257であり、スタックにはM[256]があるとする。
            this.getSPVal(false);

            // この状態の時、例は、SP = 256, M = M[256]
            // Mに算術結果を代入すれば良い。
            if (command.equals("neg")) {
                this.assemblyCommands.add("M = -M");
            } else {
                this.assemblyCommands.add("M = !M");
            }

            // SPを一つ進める。
            this.incrementSPAddress();
            return;
        }

        // この状態の時、例として、SP = 258であり、スタックにはM[257],M[256]があるとする。
        this.getSPVal(false);
        this.assemblyCommands.add("D = M");
        this.getSPVal(false);

        // この状態の時、例は、SP = 256, D = M[257], M = M[256], A = 256
        // Mに算術結果を代入すれば良い。
        if (command.equals("eq") || command.equals("gt") || command.equals("lt")) {

            final String destTrue = this.fileName + ".Arithmetic" + this.arithmeticIdx;
            this.arithmeticIdx++;
            final String destEnd = this.fileName + ".Arithmetic" + this.arithmeticIdx;
            this.arithmeticIdx++;
            this.assemblyCommands.add("D = M - D");
            this.assemblyCommands.add("@" + destTrue);

            switch (command) {
                case "eq":
                    this.assemblyCommands.add("D; JEQ");
                    break;
                case "gt":
                    this.assemblyCommands.add("D; JGT");
                    break;
                case "lt":
                    this.assemblyCommands.add("D; JLT");
                    break;
            }
            // falseの場合
            this.getSPVal(true);
            this.assemblyCommands.add("M = 0");
            this.assemblyCommands.add("@" + destEnd);
            this.assemblyCommands.add("0;JMP");

            // trueの場合
            this.assemblyCommands.add('(' + destTrue + ')');
            this.getSPVal(true);
            this.assemblyCommands.add("M = -1");

            // 終了処理
            this.assemblyCommands.add('(' + destEnd + ')');
        } else {
            switch (command) {
                case "add":
                    this.assemblyCommands.add("M = M + D");
                    break;
                case "sub":
                    this.assemblyCommands.add("M = M - D");
                    break;
                case "and":
                    this.assemblyCommands.add("M = M & D");
                    break;
                case "or":
                    this.assemblyCommands.add("M = M | D");
                    break;
            }
        }

        // SPを一つ進める。
        this.incrementSPAddress();
    }

    @Override
    public void writePushPop(CommandType command, String segment, int index) {
        if (command == CommandType.C_PUSH) {
            if (segment.equals("constant")) {
                this.assemblyCommands.add("@" + index);
                if (this.doComment) {
                    this.assemblyCommands.add("D = A 　　　// Dに" + index + "を代入");
                } else {
                    this.assemblyCommands.add("D = A");
                }
            } else {
                this.getSegmentVal(segment, index);
                this.assemblyCommands.add("D = M");
            }
            this.getSPVal(true);
            this.assemblyCommands.add("M = D");
            this.incrementSPAddress();
        } else {
            // 初期状態時、例として、SP = 257であり、スタックにはM[256]があるとする。
            this.getSPVal(false);
            this.assemblyCommands.add("D = M");

            // この状態の時、例は、SP = 256, M = M[256], D = M[256]
            // Mに算術結果を代入すれば良い。
            this.getSegmentVal(segment, index);
            this.assemblyCommands.add("M = D");
        }
    }

    @Override
    public void close() {
        System.out.println(String.join("\n", this.assemblyCommands));

        // ファイルが存在するかの確認、存在する場合は削除
        if (Files.exists(this.writePath)) {
            try {
                Files.deleteIfExists(writePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.createFile(this.writePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.write(this.writePath, assemblyCommands, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * スタックの最上位の値を取得する。メソッド終了時には、
     * A = スタックの最上位、　M = その値
     */
    private void getSPVal(final boolean isTop) {
        if (isTop) {
            this.assemblyCommands.add("@SP");
        } else {
            this.decrementSPAddress();
        }
        if (this.doComment) {
            this.assemblyCommands.add("A = M  　　// 参照先をスタックの最上位に変更");
        } else {
            this.assemblyCommands.add("A = M");
        }
    }

    /**
     * SPの値をインクリメントする
     */
    private void incrementSPAddress() {
        this.assemblyCommands.add("@SP");
        if (this.doComment) {
            this.assemblyCommands.add("M = M + 1  　　// スタック最上位のアドレスをインクリメント");
        } else {
            this.assemblyCommands.add("M = M + 1");
        }
    }

    /**
     * SPの値をデクリメントする
     */
    private void decrementSPAddress() {
        this.assemblyCommands.add("@SP");
        if (this.doComment) {
            this.assemblyCommands.add("M = M - 1  　　// スタック最上位のアドレスをデクリメント");
        } else {
            this.assemblyCommands.add("M = M - 1");
        }
    }

    /**
     * Arg/Lcl/This/Thatの示すメモリ位置を取得
     * A = Arg/Lcl/This/ThatのBASE + idx、　M = その値
     */
    private void getArgLclThisThatVal(final String address, final int idx) {
        this.assemblyCommands.add("@" + address);
        if (this.doComment) {
            this.assemblyCommands.add("A = M  　　// 参照先をArg/Lcl/This/ThatのBASEの場所に変更");
        } else {
            this.assemblyCommands.add("A = M");
        }
        for (int i=0; i<idx; i++) {
            this.assemblyCommands.add("A = A + 1");
        }
    }

    /**
     * segmentの示すメモリ位置を取得
     * A = segmentを参照するアドレス、　M = その値
     */
    private void getSegmentVal(final String segment, final int index) {
        switch (segment) {
            case "argument":
                this.getArgLclThisThatVal("ARG", index);
                break;
            case "local":
                this.getArgLclThisThatVal("LCL", index);
                break;
            case "this":
                this.getArgLclThisThatVal("THIS", index);
                break;
            case "that":
                this.getArgLclThisThatVal("THAT", index);
                break;
            case "pointer":
                if (index == 0) {
                    if (this.doComment) {
                        this.assemblyCommands.add("@THIS 　　　//THISポインターを取得");
                    } else {
                        this.assemblyCommands.add("@THIS");
                    }
                } else {
                    if (this.doComment) {
                        this.assemblyCommands.add("@THAT 　　　//THATポインターを取得");
                    } else {
                        this.assemblyCommands.add("@THAT");
                    }
                }
                break;
            case "temp":
                if (this.doComment) {
                    this.assemblyCommands.add("@R" + (5+index) + " 　　　  // R" + (5+index) +"ポインターを取得");
                } else {
                    this.assemblyCommands.add("@R" + (5+index));
                }
                break;
            case "static":
                if (this.doComment) {
                    this.assemblyCommands.add("@" + this.fileName + "." + index + " 　　　  // " + this.fileName + "." + index +"シンボルを取得");
                } else {
                    this.assemblyCommands.add("@" + this.fileName + "." + index);
                }
                break;
            default:
                throw new RuntimeException("不明なsegment : " + segment);
        }
    }
}
