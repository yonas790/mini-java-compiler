PUSH 5
STORE 1      ; assume STORE implemented later, for now use workaround or implement STORE now
; alternative simple loop that uses only stack:
PUSH 5
loop:
PUSH 1
SUB
DUP
JNZ loop
PRINT
HALT
