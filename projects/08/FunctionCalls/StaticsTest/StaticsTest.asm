@256
D = A
@SP
M = D
@Sys.init
0;JMP
(Class1.set)
@ARG
A = M
D = M
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@StaticsTest.0
M = D
@ARG
A = M
A = A + 1
D = M
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@StaticsTest.1
M = D
@0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@LCL
D = M
@R13
M = D
@5
D = D - A
@R14
M = D
@SP
M = M - 1
A = M
D = M
@ARG
A = M
M = D
@ARG
D = M + 1
@SP
M = D
@R13
A = M - 1
D = M
@THAT
M = D
@R13
D = M - 1
A = D - 1
D = M
@THIS
M = D
@R13
D = M - 1
D = D - 1
A = D - 1
D = M
@ARG
M = D
@R13
D = M - 1
D = D - 1
D = D - 1
A = D - 1
D = M
@LCL
M = D
@R14
A = M
0;JMP
(Class1.get)
@StaticsTest.0
D = M
@SP
A = M
M = D
@SP
M = M + 1
@StaticsTest.1
D = M
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@SP
M = M - 1
A = M
M = M - D
@SP
M = M + 1
@LCL
D = M
@R13
M = D
@5
D = D - A
@R14
M = D
@SP
M = M - 1
A = M
D = M
@ARG
A = M
M = D
@ARG
D = M + 1
@SP
M = D
@R13
A = M - 1
D = M
@THAT
M = D
@R13
D = M - 1
A = D - 1
D = M
@THIS
M = D
@R13
D = M - 1
D = D - 1
A = D - 1
D = M
@ARG
M = D
@R13
D = M - 1
D = D - 1
D = D - 1
A = D - 1
D = M
@LCL
M = D
@R14
A = M
0;JMP
(Sys.init)
@6
D = A
@SP
A = M
M = D
@SP
M = M + 1
@8
D = A
@SP
A = M
M = D
@SP
M = M + 1
@Class1.set-return0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@LCL
D = M
@SP
A = M
M = D
@SP
M = M + 1
@ARG
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THIS
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THAT
D = M
@SP
A = M
M = D
@SP
M = M + 1
@5
D = A
@SP
D = M - D
D = D - 1
D = D - 1
@ARG
M = D
@SP
D = M
@LCL
M = D
@Class1.set
0;JMP
(Class1.set-return0)
@SP
M = M - 1
A = M
D = M
@R5
M = D
@23
D = A
@SP
A = M
M = D
@SP
M = M + 1
@15
D = A
@SP
A = M
M = D
@SP
M = M + 1
@Class2.set-return1
D = A
@SP
A = M
M = D
@SP
M = M + 1
@LCL
D = M
@SP
A = M
M = D
@SP
M = M + 1
@ARG
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THIS
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THAT
D = M
@SP
A = M
M = D
@SP
M = M + 1
@5
D = A
@SP
D = M - D
D = D - 1
D = D - 1
@ARG
M = D
@SP
D = M
@LCL
M = D
@Class2.set
0;JMP
(Class2.set-return1)
@SP
M = M - 1
A = M
D = M
@R5
M = D
@Class1.get-return2
D = A
@SP
A = M
M = D
@SP
M = M + 1
@LCL
D = M
@SP
A = M
M = D
@SP
M = M + 1
@ARG
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THIS
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THAT
D = M
@SP
A = M
M = D
@SP
M = M + 1
@5
D = A
@SP
D = M - D
@ARG
M = D
@SP
D = M
@LCL
M = D
@Class1.get
0;JMP
(Class1.get-return2)
@Class2.get-return3
D = A
@SP
A = M
M = D
@SP
M = M + 1
@LCL
D = M
@SP
A = M
M = D
@SP
M = M + 1
@ARG
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THIS
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THAT
D = M
@SP
A = M
M = D
@SP
M = M + 1
@5
D = A
@SP
D = M - D
@ARG
M = D
@SP
D = M
@LCL
M = D
@Class2.get
0;JMP
(Class2.get-return3)
(WHIL)
@WHIL
0;JMP
(Class2.set)
@ARG
A = M
D = M
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@StaticsTest.0
M = D
@ARG
A = M
A = A + 1
D = M
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@StaticsTest.1
M = D
@0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@LCL
D = M
@R13
M = D
@5
D = D - A
@R14
M = D
@SP
M = M - 1
A = M
D = M
@ARG
A = M
M = D
@ARG
D = M + 1
@SP
M = D
@R13
A = M - 1
D = M
@THAT
M = D
@R13
D = M - 1
A = D - 1
D = M
@THIS
M = D
@R13
D = M - 1
D = D - 1
A = D - 1
D = M
@ARG
M = D
@R13
D = M - 1
D = D - 1
D = D - 1
A = D - 1
D = M
@LCL
M = D
@R14
A = M
0;JMP
(Class2.get)
@StaticsTest.0
D = M
@SP
A = M
M = D
@SP
M = M + 1
@StaticsTest.1
D = M
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@SP
M = M - 1
A = M
M = M - D
@SP
M = M + 1
@LCL
D = M
@R13
M = D
@5
D = D - A
@R14
M = D
@SP
M = M - 1
A = M
D = M
@ARG
A = M
M = D
@ARG
D = M + 1
@SP
M = D
@R13
A = M - 1
D = M
@THAT
M = D
@R13
D = M - 1
A = D - 1
D = M
@THIS
M = D
@R13
D = M - 1
D = D - 1
A = D - 1
D = M
@ARG
M = D
@R13
D = M - 1
D = D - 1
D = D - 1
A = D - 1
D = M
@LCL
M = D
@R14
A = M
0;JMP
