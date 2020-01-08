@17
D = A
@SP
A = M
M = D
@SP
M = M + 1
@17
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
@StackTest.Arithmetic0
D; JEQ
@SP
A = M
M = 0
@StackTest.Arithmetic1
0;JMP
(StackTest.Arithmetic0)
@SP
A = M
M = -1
(StackTest.Arithmetic1)
@SP
M = M + 1
@17
D = A
@SP
A = M
M = D
@SP
M = M + 1
@16
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
@StackTest.Arithmetic2
D; JEQ
@SP
A = M
M = 0
@StackTest.Arithmetic3
0;JMP
(StackTest.Arithmetic2)
@SP
A = M
M = -1
(StackTest.Arithmetic3)
@SP
M = M + 1
@16
D = A
@SP
A = M
M = D
@SP
M = M + 1
@17
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
@StackTest.Arithmetic4
D; JEQ
@SP
A = M
M = 0
@StackTest.Arithmetic5
0;JMP
(StackTest.Arithmetic4)
@SP
A = M
M = -1
(StackTest.Arithmetic5)
@SP
M = M + 1
@892
D = A
@SP
A = M
M = D
@SP
M = M + 1
@891
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
@StackTest.Arithmetic6
D; JLT
@SP
A = M
M = 0
@StackTest.Arithmetic7
0;JMP
(StackTest.Arithmetic6)
@SP
A = M
M = -1
(StackTest.Arithmetic7)
@SP
M = M + 1
@891
D = A
@SP
A = M
M = D
@SP
M = M + 1
@892
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
@StackTest.Arithmetic8
D; JLT
@SP
A = M
M = 0
@StackTest.Arithmetic9
0;JMP
(StackTest.Arithmetic8)
@SP
A = M
M = -1
(StackTest.Arithmetic9)
@SP
M = M + 1
@891
D = A
@SP
A = M
M = D
@SP
M = M + 1
@891
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
@StackTest.Arithmetic10
D; JLT
@SP
A = M
M = 0
@StackTest.Arithmetic11
0;JMP
(StackTest.Arithmetic10)
@SP
A = M
M = -1
(StackTest.Arithmetic11)
@SP
M = M + 1
@32767
D = A
@SP
A = M
M = D
@SP
M = M + 1
@32766
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
@StackTest.Arithmetic12
D; JGT
@SP
A = M
M = 0
@StackTest.Arithmetic13
0;JMP
(StackTest.Arithmetic12)
@SP
A = M
M = -1
(StackTest.Arithmetic13)
@SP
M = M + 1
@32766
D = A
@SP
A = M
M = D
@SP
M = M + 1
@32767
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
@StackTest.Arithmetic14
D; JGT
@SP
A = M
M = 0
@StackTest.Arithmetic15
0;JMP
(StackTest.Arithmetic14)
@SP
A = M
M = -1
(StackTest.Arithmetic15)
@SP
M = M + 1
@32766
D = A
@SP
A = M
M = D
@SP
M = M + 1
@32766
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
@StackTest.Arithmetic16
D; JGT
@SP
A = M
M = 0
@StackTest.Arithmetic17
0;JMP
(StackTest.Arithmetic16)
@SP
A = M
M = -1
(StackTest.Arithmetic17)
@SP
M = M + 1
@57
D = A
@SP
A = M
M = D
@SP
M = M + 1
@31
D = A
@SP
A = M
M = D
@SP
M = M + 1
@53
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
@112
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
M = -M
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@SP
M = M - 1
A = M
M = M & D
@SP
M = M + 1
@82
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
M = M | D
@SP
M = M + 1
@SP
M = M - 1
A = M
M = !M
@SP
M = M + 1
