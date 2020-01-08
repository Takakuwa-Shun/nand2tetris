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
    private int returnIdx;
    private String fileName;
    private List<String> assemblyCommands;

    public CodeWriterImpl() {
    }


    @Override
    public void setFileName(final Path writePath) {
        this.assemblyCommands = new ArrayList<>();
        this.arithmeticIdx = 0;
        this.returnIdx = 0;
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
                // Dにindexを代入"
                this.assemblyCommands.add("D = A");
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
        for (int i=0; i<this.assemblyCommands.size(); i++) {
            System.out.println(i + " : " + this.assemblyCommands.get(i) + "\n");
        }

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

    @Override
    public void writeInit() {
        this.assemblyCommands.add("@256");
        this.assemblyCommands.add("D = A");
        this.assemblyCommands.add("@SP");
        this.assemblyCommands.add("M = D");
//        this.writeCall("Sys.init", 0);
        this.assemblyCommands.add("@Sys.init");
        this.assemblyCommands.add("0;JMP");
    }

    @Override
    public void writeLabel(String label) {
        this.assemblyCommands.add("(" + label + ")");
    }

    @Override
    public void writeGoto(String label) {
        this.assemblyCommands.add('@' + label);
        this.assemblyCommands.add("0;JMP");
    }

    @Override
    public void writeIf(String label) {
        this.getSPVal(false);
        this.assemblyCommands.add("D = M");
        this.assemblyCommands.add('@' + label);
        this.assemblyCommands.add("D;JNE");
    }

    @Override
    public void writeCall(String functionName, int numArgs) {
        final String returnAddress = functionName + "-return" + this.returnIdx;
        this.returnIdx++;

        // return addressを格納
        this.assemblyCommands.add('@' + returnAddress);
        this.assemblyCommands.add("D = A");
        this.getSPVal(true);
        this.assemblyCommands.add("M = D");
        this.incrementSPAddress();

        // LCLを格納
        this.assemblyCommands.add("@LCL");
        this.assemblyCommands.add("D = M");
        this.getSPVal(true);
        this.assemblyCommands.add("M = D");
        this.incrementSPAddress();

        // ARGを格納
        this.assemblyCommands.add("@ARG");
        this.assemblyCommands.add("D = M");
        this.getSPVal(true);
        this.assemblyCommands.add("M = D");
        this.incrementSPAddress();

        // THISを格納
        this.assemblyCommands.add("@THIS");
        this.assemblyCommands.add("D = M");
        this.getSPVal(true);
        this.assemblyCommands.add("M = D");
        this.incrementSPAddress();

        // THATを格納
        this.assemblyCommands.add("@THAT");
        this.assemblyCommands.add("D = M");
        this.getSPVal(true);
        this.assemblyCommands.add("M = D");
        this.incrementSPAddress();

        // ARGを移す (6 + numArgs)
        this.assemblyCommands.add("@5");
        this.assemblyCommands.add("D = A");
        this.assemblyCommands.add("@SP");
        this.assemblyCommands.add("D = M - D");
        for (int i=0; i<numArgs; i++) {
            this.assemblyCommands.add("D = D - 1");
        }
        this.assemblyCommands.add("@ARG");
        this.assemblyCommands.add("M = D");

        // LCLを移す
        this.assemblyCommands.add("@SP");
        this.assemblyCommands.add("D = M");
        this.assemblyCommands.add("@LCL");
        this.assemblyCommands.add("M = D");

        // 関数処理に移る。
        this.assemblyCommands.add('@' + functionName);
        this.assemblyCommands.add("0;JMP");

        // リターンアドレスのためのラベルを宣言
        this.assemblyCommands.add('(' + returnAddress + ')');
    }

    @Override
    public void writeReturn() {
        this.assemblyCommands.add("@LCL");
        this.assemblyCommands.add("D = M");

        // R13にLCLのアドレスを一時保存
        this.assemblyCommands.add("@R13");
        this.assemblyCommands.add("M = D");

        this.assemblyCommands.add("@5");
        this.assemblyCommands.add("D = D - A");

        // R14にRETのアドレスを一時保存
        this.assemblyCommands.add("@R14");
        this.assemblyCommands.add("M = D");

        // 戻り値をARG0 == 呼び出し側のSPに移動
        this.writePushPop(CommandType.C_POP, "argument", 0);

        // 呼び出し側のSPを戻す。
        this.assemblyCommands.add("@ARG");
        this.assemblyCommands.add("D = M + 1");
        this.assemblyCommands.add("@SP");
        this.assemblyCommands.add("M = D");

        // THATを戻す
        this.assemblyCommands.add("@R13");
        this.assemblyCommands.add("A = M - 1");
        this.assemblyCommands.add("D = M");
        this.assemblyCommands.add("@THAT");
        this.assemblyCommands.add("M = D");

        // THISを戻す
        this.assemblyCommands.add("@R13");
        this.assemblyCommands.add("D = M - 1");
        this.assemblyCommands.add("A = D - 1");
        this.assemblyCommands.add("D = M");
        this.assemblyCommands.add("@THIS");
        this.assemblyCommands.add("M = D");

        // ARGを戻す
        this.assemblyCommands.add("@R13");
        this.assemblyCommands.add("D = M - 1");
        this.assemblyCommands.add("D = D - 1");
        this.assemblyCommands.add("A = D - 1");
        this.assemblyCommands.add("D = M");
        this.assemblyCommands.add("@ARG");
        this.assemblyCommands.add("M = D");

        // LCLを戻す
        this.assemblyCommands.add("@R13");
        this.assemblyCommands.add("D = M - 1");
        this.assemblyCommands.add("D = D - 1");
        this.assemblyCommands.add("D = D - 1");
        this.assemblyCommands.add("A = D - 1");
        this.assemblyCommands.add("D = M");
        this.assemblyCommands.add("@LCL");
        this.assemblyCommands.add("M = D");

        // 呼び出し側に戻る。
        this.assemblyCommands.add("@R14");
        this.assemblyCommands.add("A = M");
        this.assemblyCommands.add("0;JMP");
    }

    @Override
    public void writeFunction(String functionName, int numLocals) {
        this.writeLabel(functionName);
        for (int i=0; i<numLocals; i++) {
            this.writePushPop(CommandType.C_PUSH, "constant", 0);
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
        this.assemblyCommands.add("A = M");
    }

    /**
     * SPの値をインクリメントする
     */
    private void incrementSPAddress() {
        this.assemblyCommands.add("@SP");
        this.assemblyCommands.add("M = M + 1");
    }

    /**
     * SPの値をデクリメントする
     */
    private void decrementSPAddress() {
        this.assemblyCommands.add("@SP");
        this.assemblyCommands.add("M = M - 1");
    }

    /**
     * Arg/Lcl/This/Thatの示すメモリ位置を取得
     * A = Arg/Lcl/This/ThatのBASE + idx、　M = その値
     */
    private void getArgLclThisThatVal(final String address, final int idx) {
        this.assemblyCommands.add("@" + address);
        // 参照先をArg/Lcl/This/ThatのBASEの場所に変更
        this.assemblyCommands.add("A = M");
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
                    //THISポインターを取得
                    this.assemblyCommands.add("@THIS");
                } else {
                    //THATポインターを取得
                    this.assemblyCommands.add("@THAT");
                }
                break;
            case "temp":
                // [R{5+index}]ポインターを取得"
                this.assemblyCommands.add("@R" + (5+index));
                break;
            case "static":
                // [{this.fileName}.{index}]シンボルを取得"
                this.assemblyCommands.add("@" + this.fileName + "." + index);
                break;
            default:
                throw new RuntimeException("不明なsegment : " + segment);
        }
    }
}
