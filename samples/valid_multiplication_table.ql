HERO TableHero
BADGE 1211305566

STAT nn1, lm1, ii1, rs1

ASK "Enter table number:", nn1
ASK "Enter table limit:", lm1

ii1 = 1
JOURNEY FROM 1 TO lm1
    rs1 = nn1 * ii1
    SAY nn1 & " x " & ii1 & " = " & rs1
    ii1 = ii1 + 1
ENDJOURNEY

THEEND
