@256
D = A
@SP
M = D
@Sys.init-return0
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
@Sys.init
0;JMP
(Sys.init-return0)
(Main.fibonacci)
@ARG
A = M
D = M
@SP
A = M
M = D
@SP
M = M + 1
@2
D = A
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
D = M - D
@FibonacciElement.Arithmetic0
D; JLT
@SP
A = M
M = 0
@FibonacciElement.Arithmetic1
0;JMP
(FibonacciElement.Arithmetic0)
@SP
A = M
M = -1
(FibonacciElement.Arithmetic1)
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@IF_TRU
D;JNE
@IF_FALS
0;JMP
(IF_TRU)
@ARG
A = M
D = M
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
(IF_FALS)
@ARG
A = M
D = M
@SP
A = M
M = D
@SP
M = M + 1
@2
D = A
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
@Main.fibonacci-return1
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
@ARG
M = D
@SP
D = M
@LCL
M = D
@Main.fibonacci
0;JMP
(Main.fibonacci-return1)
@ARG
A = M
D = M
@SP
A = M
M = D
@SP
M = M + 1
@1
D = A
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
@Main.fibonacci-return2
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
@ARG
M = D
@SP
D = M
@LCL
M = D
@Main.fibonacci
0;JMP
(Main.fibonacci-return2)
@SP
M = M - 1
A = M
D = M
@SP
M = M - 1
A = M
M = M + D
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
@4
D = A
@SP
A = M
M = D
@SP
M = M + 1
@Main.fibonacci-return3
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
@ARG
M = D
@SP
D = M
@LCL
M = D
@Main.fibonacci
0;JMP
(Main.fibonacci-return3)
(WHIL)
@WHIL
0;JMP
