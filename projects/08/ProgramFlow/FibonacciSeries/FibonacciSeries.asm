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
@THAT
M = D
@0
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
@THAT
A = M
M = D
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
@THAT
A = M
A = A + 1
M = D
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
@SP
M = M - 1
A = M
D = M
@ARG
A = M
M = D
(MAIN_LOOP_STAR)
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
@COMPUTE_ELEMEN
D;JNE
@END_PROGRA
0;JMP
(COMPUTE_ELEMEN)
@THAT
A = M
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THAT
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
@SP
M = M - 1
A = M
M = M + D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@THAT
A = M
A = A + 1
A = A + 1
M = D
@THAT
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
M = M + D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@THAT
M = D
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
@SP
M = M - 1
A = M
D = M
@ARG
A = M
M = D
@MAIN_LOOP_STAR
0;JMP
(END_PROGRA)
