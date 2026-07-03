HERO AhmedBalfaqih
BADGE 1221304386
NOTE Student: BALFAQIH AHMED KHALED AHMED
NOTE Student ID: 1221304386

NOTE Advanced multiplication table with even/odd tagging and sum
STAT nn1, lm1, st1, ii1, rs1, sm1, hv1
TALE tg$

DEFINE TRICK header
    SAY "======= MULTIPLICATION QUEST ======="
    DRAW STAR 28
ENDTRICK

PERFORM header

ASK "Enter table number:", nn1
ASK "Enter start value:", st1
ASK "Enter table limit:", lm1

sm1 = 0
ii1 = st1

JOURNEY FROM st1 TO lm1
    rs1 = nn1 * ii1
    sm1 = sm1 + rs1
    hv1 = ROUND(rs1 / 2) * 2
    DECIDE hv1 == rs1
        tg$ = "even"
    OTHERWISE
        tg$ = "odd"
    ENDDECIDE
    SAY nn1 & " x " & ii1 & " = " & rs1 & "  (" & tg$ & ")"
    ii1 = ii1 + 1
ENDJOURNEY

SAY "Sum of all products = " & sm1
SAY "Average product = " & sm1 / (lm1 - st1 + 1)

DECIDE nn1 > 12
    SAY "That is a large multiplication table!"
OTHERWISE
    SAY "Standard multiplication table."
ENDDECIDE

DRAW TRIANGLE 5
THEEND
