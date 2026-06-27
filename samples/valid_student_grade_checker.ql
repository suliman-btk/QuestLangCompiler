HERO GradeHero
BADGE 1211305566

STAT sc1
TALE nm$, gd$

ASK "Enter student name:", nm$
ASK "Enter score:", sc1

DECIDE sc1 >= 90
    gd$ = "A"
OTHERWISE
    DECIDE sc1 >= 80
        gd$ = "B"
    OTHERWISE
        DECIDE sc1 >= 70
            gd$ = "C"
        OTHERWISE
            DECIDE sc1 >= 60
                gd$ = "D"
            OTHERWISE
                gd$ = "F"
            ENDDECIDE
        ENDDECIDE
    ENDDECIDE
ENDDECIDE

SAY nm$ & " scored " & sc1 & " and received grade " & gd$

DECIDE sc1 >= 60
    SAY "Status: Pass"
OTHERWISE
    SAY "Status: Fail"
ENDDECIDE

THEEND
