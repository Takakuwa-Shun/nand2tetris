@256
D = A
@SP
M = D
@Sys.init
0;JMP
(Sys.init)
@4000
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
@THIS
M = D
@5000
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
M = D
@Sys.main-return0
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
@Sys.main
0;JMP
(Sys.main-return0)
@SP
M = M - 1
A = M
D = M
@R6
M = D
(LOO)
@LOO
0;JMP
(Sys.main)
@0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@4001
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
@THIS
M = D
@5001
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
M = D
@200
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
@LCL
A = M
A = A + 1
M = D
@40
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
@LCL
A = M
A = A + 1
A = A + 1
M = D
@6
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
@LCL
A = M
A = A + 1
A = A + 1
A = A + 1
M = D
@123
D = A
@SP
A = M
M = D
@SP
M = M + 1
@Sys.add12-return1
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
@Sys.add12
0;JMP
(Sys.add12-return1)
@SP
M = M - 1
A = M
D = M
@R5
M = D
@LCL
A = M
D = M
@SP
A = M
M = D
@SP
M = M + 1
@LCL
A = M
A = A + 1
D = M
@SP
A = M
M = D
@SP
M = M + 1
@LCL
A = M
A = A + 1
A = A + 1
D = M
@SP
A = M
M = D
@SP
M = M + 1
@LCL
A = M
A = A + 1
A = A + 1
A = A + 1
D = M
@SP
A = M
M = D
@SP
M = M + 1
@LCL
A = M
A = A + 1
A = A + 1
A = A + 1
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
(Sys.add12)
@4002
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
@THIS
M = D
@5002
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
M = D
@ARG
A = M
D = M
@SP
A = M
M = D
@SP
M = M + 1
@12
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
