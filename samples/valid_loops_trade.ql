HERO Lina
STAT aa1, bb1
TALE ms$

aa1 = 1
bb1 = 3
ms$ = "Loop"

TRADE aa1, bb1

GRIND 2 TIMES
    SAY "After trade aa1 is " & aa1
ENDGRIND

JOURNEY FROM 1 TO 3
    SAY ms$
ENDJOURNEY

JOURNEY FROM 3 TO 1
    SAY "Back"
ENDJOURNEY

THEEND
