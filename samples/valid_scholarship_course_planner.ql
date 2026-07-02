HERO ScholarQuest
BADGE 1211305566

STAT cs1, cr1, mk1, tc1, ws1, av1, fe1, sp1, rm1, st1, hr1, wk1, ii1, rv1, mx1, mn1, gp1, id1, bo1
TALE nm$, cn$, el$

DEFINE TRICK cover
    SAY "===== QUEST SCHOLARSHIP COURSE PLANNER ====="
    DRAW BOX 3
ENDTRICK

DEFINE TRICK markline
    DRAW STAR 24
ENDTRICK

PERFORM cover

ASK "Enter student name:", nm$
ASK "Enter total semester fee:", fe1
ASK "How many courses:", cs1
ASK "Scholarship type 1 Merit 2 Need 3 Sports:", st1
ASK "Weeks left before exam:", wk1

id1 = LUCK(1000, 9999)
tc1 = 0
ws1 = 0
mx1 = 0
mn1 = 999999

GRIND cs1 TIMES
    ASK "Enter course name:", cn$
    ASK "Enter course credit:", cr1
    ASK "Enter expected mark:", mk1

    tc1 = tc1 + cr1
    ws1 = ws1 + (cr1 * mk1)
    mx1 = BIGGER(mx1, mk1)
    mn1 = SMALLER(mn1, mk1)

    SAY "Course: " & cn$
    SAY "Weighted points: " & cr1 * mk1
    PERFORM markline
ENDGRIND

DECIDE tc1 > 0
    av1 = ROUND(ws1 / tc1)
OTHERWISE
    av1 = 0
ENDDECIDE

gp1 = ROUND((av1 / 100) * 4)
bo1 = ROUND(ROOT(ABS(100 - av1)) * PI)

CHOOSE st1
    OPTION 1:
        DECIDE av1 >= 85
            sp1 = fe1 * 0.60
        OTHERWISE
            DECIDE av1 >= 75
                sp1 = fe1 * 0.35
            OTHERWISE
                sp1 = fe1 * 0.10
            ENDDECIDE
        ENDDECIDE
    OPTION 2:
        DECIDE av1 >= 70
            sp1 = fe1 * 0.50
        OTHERWISE
            sp1 = fe1 * 0.25
        ENDDECIDE
    OPTION 3:
        DECIDE av1 >= 60
            sp1 = fe1 * 0.40
        OTHERWISE
            sp1 = fe1 * 0.15
        ENDDECIDE
    OTHERWISE
        sp1 = 0
ENDCHOOSE

rm1 = fe1 - sp1
hr1 = ROUND(bo1 + tc1)
rv1 = hr1

DECIDE av1 >= 85 AND tc1 >= 12
    el$ = "Strong scholarship candidate"
OTHERWISE
    DECIDE av1 >= 70 OR gp1 >= 3
        el$ = "Possible scholarship candidate"
    OTHERWISE
        el$ = "Needs stronger academic record"
    ENDDECIDE
ENDDECIDE

PERFORM cover
SAY "Planner ID: " & id1
SAY "Student: " & nm$
SAY "Total credits: " & tc1
SAY "Average mark: " & av1
SAY "Estimated GPA: " & gp1
SAY "Highest course mark: " & mx1
SAY "Lowest course mark: " & mn1
SAY "Scholarship amount: " & sp1
SAY "Remaining fee: " & rm1
SAY "Eligibility: " & el$
SAY "Base study hours per week: " & hr1

SAY "Weekly revision plan"
ii1 = 1
JOURNEY FROM 1 TO wk1
    rv1 = rv1 + LUCK(1, 2)
    SAY "Week " & ii1 & " study hours: " & rv1
    ii1 = ii1 + 1
ENDJOURNEY

GRIND 1 TIMES
    SAY "Reminder: attend class and complete practice tasks"
ENDGRIND

DECIDE NOT (rm1 > fe1) AND sp1 > 0
    SAY "Finance status: scholarship applied"
OTHERWISE
    SAY "Finance status: full payment needed"
ENDDECIDE

SAY "===== PLANNER COMPLETE ====="
THEEND
