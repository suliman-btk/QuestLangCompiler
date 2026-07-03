HERO SulimanAlkatheri
BADGE 1211305566
NOTE Student: ALKATHERI SULAIMAN ALI MAHDI
NOTE Student ID: 1211305566

STAT ct1, sc1, tt1, av1, mx1, mn1, df1, rp1, id1
TALE nm$, gd$

DEFINE TRICK banner
    SAY "===== QUEST ACADEMIC ADVISOR ====="
    DRAW BOX 4
ENDTRICK

PERFORM banner

ASK "Enter student name: ", nm$
ASK "How many course scores: ", ct1

DECIDE ct1 <= 0
    SAY "Invalid course count. Using one course."
    ct1 = 1
OTHERWISE
    SAY "Course count accepted."
ENDDECIDE

tt1 = 0
mx1 = 0
mn1 = 100
id1 = LUCK(1000, 9999)

GRIND ct1 TIMES
    ASK "Enter course score: ", sc1

    DECIDE sc1 < 0
        SAY "Score below zero adjusted to zero."
        sc1 = 0
    OTHERWISE
        DECIDE sc1 > 100
            SAY "Score above 100 adjusted to 100."
            sc1 = 100
        OTHERWISE
            SAY "Score accepted."
        ENDDECIDE
    ENDDECIDE

    tt1 = tt1 + sc1
    mx1 = BIGGER(mx1, sc1)
    mn1 = SMALLER(mn1, sc1)
ENDGRIND

av1 = tt1 / ct1
df1 = ABS(mx1 - mn1)

DECIDE av1 >= 85
    gd$ = "Excellent"
OTHERWISE
    DECIDE av1 >= 70
        gd$ = "Good"
    OTHERWISE
        DECIDE av1 >= 50
            gd$ = "Pass"
        OTHERWISE
            gd$ = "At Risk"
        ENDDECIDE
    ENDDECIDE
ENDDECIDE

ASK "Choose report 1 Summary, 2 Advice, 3 Full: ", rp1

CHOOSE rp1
    OPTION 1:
        SAY "Summary report selected."
    OPTION 2:
        SAY "Advice report selected."
        DECIDE av1 >= 70
            SAY "Advice: keep your current study plan."
        OTHERWISE
            SAY "Advice: increase weekly revision time."
        ENDDECIDE
    OPTION 3:
        SAY "Full report selected."
        SAY "Highest score: " & mx1
        SAY "Lowest score: " & mn1
        SAY "Score range: " & df1
    OTHERWISE
        SAY "Unknown report type. Showing summary."
ENDCHOOSE

SAY "Report ID: " & id1
SAY "Student: " & nm$
SAY "Average score: " & av1
SAY "Performance level: " & gd$

DECIDE av1 >= 50
    SAY "Academic standing: Passed"
OTHERWISE
    SAY "Academic standing: Needs support"
ENDDECIDE

SAY "===== END OF REPORT ====="

THEEND
