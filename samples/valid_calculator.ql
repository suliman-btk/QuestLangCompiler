HERO Aisha
BADGE 220011

STAT aa1, bb1, sm1, dv1
TALE ms$

ASK "Enter the first number:", aa1
ASK "Enter the second number:", bb1

sm1 = aa1 + bb1

DECIDE bb1 <> 0
    dv1 = aa1 / bb1
    SAY "The quotient is " & dv1
OTHERWISE
    ms$ = "Cannot divide by zero"
    SAY ms$
ENDDECIDE

SAY "The sum is " & sm1
THEEND
