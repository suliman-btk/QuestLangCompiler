HERO CalcMaster
BADGE 1211305566

NOTE Advanced QuestLang Calculator with menu, loop, memory
STAT aa1, bb1, op1, rs1, mm1, cn1, pw1, gc1

DEFINE TRICK banner
    SAY "===================================="
    SAY "      QUESTLANG SUPER CALCULATOR     "
    SAY "===================================="
    DRAW STAR 30
ENDTRICK

DEFINE TRICK line
    DRAW STAR 20
ENDTRICK

PERFORM banner

mm1 = 0
cn1 = 0

GRIND
    ASK "Enter first number:", aa1
    ASK "Enter second number:", bb1
    ASK "Op 1+ 2- 3* 4/ 5power 6root 7max 8min 9absdiff:", op1

    CHOOSE op1
        OPTION 1:
            rs1 = aa1 + bb1
            SAY "Sum = " & rs1
        OPTION 2:
            rs1 = aa1 - bb1
            SAY "Difference = " & rs1
        OPTION 3:
            rs1 = aa1 * bb1
            SAY "Product = " & rs1
        OPTION 4:
            DECIDE bb1 <> 0
                rs1 = aa1 / bb1
                SAY "Quotient = " & rs1
            OTHERWISE
                rs1 = 0
                SAY "Cannot divide by zero."
            ENDDECIDE
        OPTION 5:
            rs1 = 1
            pw1 = ROUND(ABS(bb1))
            GRIND pw1 TIMES
                rs1 = rs1 * aa1
            ENDGRIND
            SAY "Power = " & rs1
        OPTION 6:
            rs1 = ROOT(ABS(aa1))
            SAY "Square root of first = " & rs1
        OPTION 7:
            rs1 = BIGGER(aa1, bb1)
            SAY "Maximum = " & rs1
        OPTION 8:
            rs1 = SMALLER(aa1, bb1)
            SAY "Minimum = " & rs1
        OPTION 9:
            rs1 = ABS(aa1 - bb1)
            SAY "Absolute difference = " & rs1
        OTHERWISE
            rs1 = 0
            SAY "Unknown operation."
    ENDCHOOSE

    mm1 = mm1 + rs1
    cn1 = cn1 + 1
    PERFORM line
    SAY "Running memory total = " & mm1
    ASK "Compute another? 1 yes 0 no:", gc1
UNTIL gc1 == 0

DECIDE cn1 > 0
    SAY "Average of results = " & mm1 / cn1
OTHERWISE
    SAY "No calculations performed."
ENDDECIDE

SAY "Total calculations = " & cn1
SAY "PI reference value = " & PI
PERFORM banner
THEEND
