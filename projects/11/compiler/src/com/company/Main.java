package com.company;

import com.company.model.Setting;

public class Main {

    public static void main(String[] args) {
        JackCompiler compiler = new JackCompiler(Setting.getPath(Setting.COMPLEX_ARRAYS));
        compiler.compile();
    }
}
