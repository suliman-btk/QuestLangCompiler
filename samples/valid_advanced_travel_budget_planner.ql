HERO SulimanAlkatheri
BADGE 1211305566
NOTE Student: ALKATHERI SULAIMAN ALI MAHDI
NOTE Student ID: 1211305566

NOTE Advanced travel budget planner with expense loop, travel mode, and budget analysis
STAT dy1, bd1, ex1, tt1, av1, mx1, mn1, rm1, md1, id1, sf1, ds1
TALE nm$, lv$

DEFINE TRICK title
    SAY "========== QUEST TRAVEL BUDGET =========="
    DRAW STAR 34
ENDTRICK

DEFINE TRICK line
    DRAW STAR 18
ENDTRICK

PERFORM title

ASK "Enter traveler name:", nm$
ASK "Enter total travel budget:", bd1
ASK "How many expense entries:", dy1
ASK "Choose travel mode 1 Economy 2 Standard 3 Premium:", md1

DECIDE dy1 <= 0
    SAY "Invalid entry count. Using one expense entry."
    dy1 = 1
OTHERWISE
    SAY "Expense entry count accepted."
ENDDECIDE

tt1 = 0
mx1 = 0
mn1 = 999999
id1 = LUCK(1000, 9999)

GRIND dy1 TIMES
    ASK "Enter expense amount:", ex1

    DECIDE ex1 < 0
        SAY "Negative expense changed to zero."
        ex1 = 0
    OTHERWISE
        SAY "Expense accepted."
    ENDDECIDE

    tt1 = tt1 + ex1
    mx1 = BIGGER(mx1, ex1)
    mn1 = SMALLER(mn1, ex1)
    PERFORM line
ENDGRIND

CHOOSE md1
    OPTION 1:
        ds1 = tt1 * 0.05
        lv$ = "Economy"
    OPTION 2:
        ds1 = 0
        lv$ = "Standard"
    OPTION 3:
        ds1 = tt1 * 0.08
        lv$ = "Premium"
    OTHERWISE
        ds1 = 0
        lv$ = "Unknown"
ENDCHOOSE

tt1 = tt1 - ds1
av1 = tt1 / dy1
rm1 = bd1 - tt1
sf1 = ABS(rm1)

PERFORM title
SAY "Trip report ID: " & id1
SAY "Traveler: " & nm$
SAY "Travel mode: " & lv$
SAY "Budget: " & bd1
SAY "Mode adjustment: " & ds1
SAY "Total expenses: " & tt1
SAY "Average expense: " & av1
SAY "Highest expense: " & mx1
SAY "Lowest expense: " & mn1
SAY "Remaining budget: " & rm1

DECIDE rm1 >= 0
    SAY "Budget status: Safe"
OTHERWISE
    SAY "Budget status: Over budget by " & sf1
ENDDECIDE

DECIDE av1 <= (bd1 / dy1)
    SAY "Daily spending level: Controlled"
OTHERWISE
    SAY "Daily spending level: Needs review"
ENDDECIDE

DRAW BOX 3
THEEND
