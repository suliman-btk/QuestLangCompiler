HERO GradeMaster
BADGE 1211305566

NOTE Advanced grade checker over multiple subjects
STAT ns1, sc1, tt1, av1, mx1, mn1, gp1, ps1
TALE nm$, gd$, st$

DEFINE TRICK banner
    SAY "======== STUDENT GRADE QUEST ========"
    DRAW STAR 30
ENDTRICK

PERFORM banner

ASK "Enter student name:", nm$
ASK "How many subjects:", ns1

tt1 = 0
mx1 = 0
mn1 = 100
ps1 = 0

GRIND ns1 TIMES
    ASK "Enter subject score:", sc1
    tt1 = tt1 + sc1
    mx1 = BIGGER(mx1, sc1)
    mn1 = SMALLER(mn1, sc1)

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

    DECIDE sc1 >= 60
        ps1 = ps1 + 1
    OTHERWISE
        SAY "Subject failed with score " & sc1
    ENDDECIDE

    SAY "Score " & sc1 & " -> grade " & gd$
ENDGRIND

DECIDE ns1 > 0
    av1 = ROUND(tt1 / ns1)
OTHERWISE
    av1 = 0
ENDDECIDE

gp1 = ROUND((av1 / 100) * 4)

DECIDE av1 >= 85 AND mn1 >= 60
    st$ = "Excellent standing"
OTHERWISE
    DECIDE av1 >= 60 OR ps1 >= ns1
        st$ = "Good standing"
    OTHERWISE
        st$ = "Needs improvement"
    ENDDECIDE
ENDDECIDE

PERFORM banner
SAY "Student: " & nm$
SAY "Average score: " & av1
SAY "Highest: " & mx1 & "  Lowest: " & mn1
SAY "Estimated GPA: " & gp1
SAY "Subjects passed: " & ps1 & " of " & ns1
SAY "Status: " & st$
DRAW BOX 3
THEEND
