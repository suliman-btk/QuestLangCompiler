HERO GradeMaster
BADGE 1211305566

NOTE MMU-graded checker with credit hours and credit-weighted GPA
STAT ns1, sc1, cr1, qp1, tw1, tc1, gp1, rg1, tt1, av1, mx1, mn1, ps1
TALE nm$, gd$, st$

DEFINE TRICK banner
    SAY "======== STUDENT GRADE QUEST ========"
    DRAW STAR 30
ENDTRICK

PERFORM banner

ASK "Enter student name:", nm$
ASK "How many subjects:", ns1

tt1 = 0
tw1 = 0
tc1 = 0
mx1 = 0
mn1 = 100
ps1 = 0

GRIND ns1 TIMES
    ASK "Enter subject score:", sc1
    ASK "Enter subject credit hours:", cr1

    CHOOSE sc1
        OPTION 79:
            gd$ = "A-"
            qp1 = 3.93
        OPTION 78:
            gd$ = "A-"
            qp1 = 3.87
        OPTION 77:
            gd$ = "A-"
            qp1 = 3.8
        OPTION 76:
            gd$ = "A-"
            qp1 = 3.73
        OPTION 75:
            gd$ = "A-"
            qp1 = 3.67
        OPTION 74:
            gd$ = "B+"
            qp1 = 3.6
        OPTION 73:
            gd$ = "B+"
            qp1 = 3.53
        OPTION 72:
            gd$ = "B+"
            qp1 = 3.47
        OPTION 71:
            gd$ = "B+"
            qp1 = 3.4
        OPTION 70:
            gd$ = "B+"
            qp1 = 3.33
        OPTION 69:
            gd$ = "B"
            qp1 = 3.27
        OPTION 68:
            gd$ = "B"
            qp1 = 3.2
        OPTION 67:
            gd$ = "B"
            qp1 = 3.13
        OPTION 66:
            gd$ = "B"
            qp1 = 3.07
        OPTION 65:
            gd$ = "B"
            qp1 = 3.0
        OPTION 64:
            gd$ = "B-"
            qp1 = 2.93
        OPTION 63:
            gd$ = "B-"
            qp1 = 2.87
        OPTION 62:
            gd$ = "B-"
            qp1 = 2.8
        OPTION 61:
            gd$ = "B-"
            qp1 = 2.73
        OPTION 60:
            gd$ = "B-"
            qp1 = 2.67
        OPTION 59:
            gd$ = "C+"
            qp1 = 2.59
        OPTION 58:
            gd$ = "C+"
            qp1 = 2.53
        OPTION 57:
            gd$ = "C+"
            qp1 = 2.46
        OPTION 56:
            gd$ = "C+"
            qp1 = 2.4
        OPTION 55:
            gd$ = "C+"
            qp1 = 2.33
        OPTION 54:
            gd$ = "C"
            qp1 = 2.26
        OPTION 53:
            gd$ = "C"
            qp1 = 2.2
        OPTION 52:
            gd$ = "C"
            qp1 = 2.13
        OPTION 51:
            gd$ = "C"
            qp1 = 2.07
        OPTION 50:
            gd$ = "C"
            qp1 = 2.0
        OTHERWISE
            DECIDE sc1 >= 90
                gd$ = "A+"
                qp1 = 4.00
            OTHERWISE
                DECIDE sc1 >= 80
                    gd$ = "A"
                    qp1 = 4.00
                OTHERWISE
                    DECIDE sc1 >= 47
                        gd$ = "C-"
                        qp1 = 1.67
                    OTHERWISE
                        DECIDE sc1 >= 44
                            gd$ = "D+"
                            qp1 = 1.33
                        OTHERWISE
                            DECIDE sc1 >= 40
                                gd$ = "D"
                                qp1 = 1.00
                            OTHERWISE
                                gd$ = "F"
                                qp1 = 0
                            ENDDECIDE
                        ENDDECIDE
                    ENDDECIDE
                ENDDECIDE
            ENDDECIDE
    ENDCHOOSE

    tt1 = tt1 + sc1
    mx1 = BIGGER(mx1, sc1)
    mn1 = SMALLER(mn1, sc1)
    tw1 = tw1 + qp1 * cr1
    tc1 = tc1 + cr1

    DECIDE qp1 > 0
        ps1 = ps1 + 1
    OTHERWISE
        SAY "Subject failed (F) with score " & sc1
    ENDDECIDE

    SAY "Score " & sc1 & " (" & cr1 & " cr) -> grade " & gd$ & ", QP " & qp1
ENDGRIND

DECIDE ns1 > 0
    av1 = ROUND(tt1 / ns1)
OTHERWISE
    av1 = 0
ENDDECIDE

DECIDE tc1 > 0
    gp1 = tw1 / tc1
OTHERWISE
    gp1 = 0
ENDDECIDE
rg1 = ROUND(gp1 * 100) / 100.0

DECIDE gp1 >= 3.67
    st$ = "Dean's List"
OTHERWISE
    DECIDE gp1 >= 2.0
        st$ = "Good standing"
    OTHERWISE
        st$ = "Academic probation"
    ENDDECIDE
ENDDECIDE

PERFORM banner
SAY "Student: " & nm$
SAY "Total credit hours: " & tc1
SAY "Average score: " & av1
SAY "Highest: " & mx1 & "  Lowest: " & mn1
SAY "Subjects passed: " & ps1 & " of " & ns1
SAY "GPA (credit-weighted): " & rg1
SAY "Standing: " & st$
DRAW BOX 3
THEEND
