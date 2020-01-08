package com.company;

public class CodeImpl implements Code {
    @Override
    public byte dest(String mnemonic) {
        switch (mnemonic) {
            case "null":
                return 0; // 000
            case "M":
                return 1; // 001
            case "D":
                return 2; //010
            case "MD":
                return 3; //011
            case "A":
                return 4; //100
            case "AM":
                return 5; //101
            case "AD":
                return 6; //110
            case "AMD":
                return 7; //111
            default:
                throw new RuntimeException("destのニーモニックがどこにも該当しません: " + mnemonic);
        }
    }

    @Override
    public byte comp(String mnemonic) {
        switch (mnemonic) {
            case "0":
                return 42; //0101010
            case "1":
                return 63; //0111111
            case "-1":
                return 58; //0111010
            case "D":
                return 12; //0001100
            case "A":
                return 48; //0110000
            case "!D":
                return 13; //0001101
            case "!A":
                return 49; //0110001
            case "-D":
                return 15; //0001111
            case "-A":
                return 51; //0110011
            case "D+1":
                return 31; //0011111
            case "A+1":
                return 55; //0110111
            case "D-1":
                return 14; //0001110
            case "A-1":
                return 50; //0110010
            case "D+A":
                return 2; //0000010
            case "D-A":
                return 19; //0010011
            case "A-D":
                return 7; //0000111
            case "D&A":
                return 0; //0000000
            case "D|A":
                return 21; //0010101
            case "M":
                return 112; //1110000
            case "!M":
                return 113; //1110001
            case "-M":
                return 115; //1110011
            case "M+1":
                return 119; //1110111
            case "M-1":
                return 114; //1110010
            case "D+M":
                return 66; //1000010
            case "D-M":
                return 83; //1010011
            case "M-D":
                return 71; //1000111
            case "D&M":
                return 64; //1000000
            case "D|M":
                return 85; //1010101
            default:
                throw new RuntimeException("compのニーモニックがどこにも該当しません: " + mnemonic);
        }
    }

    @Override
    public byte jump(String mnemonic) {
        switch (mnemonic) {
            case "null":
                return 0; // 000
            case "JGT":
                return 1; // 001
            case "JEQ":
                return 2; //010
            case "JGE":
                return 3; //011
            case "JLT":
                return 4; //100
            case "JNE":
                return 5; //101
            case "JLE":
                return 6; //110
            case "JMP":
                return 7; //111
            default:
                throw new RuntimeException("jumpのニーモニックがどこにも該当しません: " + mnemonic);
        }
    }
}
