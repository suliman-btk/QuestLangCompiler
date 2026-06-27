HERO CalculatorHero
BADGE 1211305566

STAT aa1, bb1, op1, rs1

ASK "Enter first number:", aa1
ASK "Enter second number:", bb1
ASK "Choose operation 1 add 2 subtract 3 multiply 4 divide:", op1

CHOOSE op1
    OPTION 1 :
        rs1 = aa1 + bb1
        SAY "Addition result is " & rs1
    OPTION 2 :
        rs1 = aa1 - bb1
        SAY "Subtraction result is " & rs1
    OPTION 3 :
        rs1 = aa1 * bb1
        SAY "Multiplication result is " & rs1
    OPTION 4 :
        DECIDE bb1 <> 0
            rs1 = aa1 / bb1
            SAY "Division result is " & rs1
        OTHERWISE
            SAY "Cannot divide by zero."
        ENDDECIDE
    OTHERWISE
        SAY "Unknown calculator operation."
ENDCHOOSE

THEEND
