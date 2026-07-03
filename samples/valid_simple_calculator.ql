HERO CalcMaster
BADGE 1211305566

NOTE Calculator with a DIFFERENT operator chosen between each number (left to right)
STAT nn1, rm1, xx1, op1, ac1, gc1, mm1, cn1

DEFINE TRICK banner
    SAY "===================================="
    SAY "     QUESTLANG EXPRESSION CALC       "
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
    ASK "How many numbers:", nn1
    ASK "Enter number 1:", ac1

    rm1 = nn1 - 1
    GRIND rm1 TIMES
        ASK "Operator 1+ 2- 3* 4/ 5max 6min:", op1
        ASK "Enter next number:", xx1
        CHOOSE op1
            OPTION 1:
                ac1 = ac1 + xx1
            OPTION 2:
                ac1 = ac1 - xx1
            OPTION 3:
                ac1 = ac1 * xx1
            OPTION 4:
                DECIDE xx1 <> 0
                    ac1 = ac1 / xx1
                OTHERWISE
                    SAY "Skipped a division by zero."
                ENDDECIDE
            OPTION 5:
                ac1 = BIGGER(ac1, xx1)
            OPTION 6:
                ac1 = SMALLER(ac1, xx1)
            OTHERWISE
                SAY "Unknown operator, keeping current value."
        ENDCHOOSE
        SAY "Running value = " & ac1
    ENDGRIND

    SAY "Final result = " & ac1
    mm1 = mm1 + ac1
    cn1 = cn1 + 1
    PERFORM line
    SAY "Running memory total = " & mm1
    ASK "Compute another? 1 yes 0 no:", gc1
UNTIL gc1 == 0

DECIDE cn1 > 0
    SAY "Number of calculations = " & cn1
    SAY "Average of results = " & mm1 / cn1
OTHERWISE
    SAY "No calculations performed."
ENDDECIDE
THEEND
