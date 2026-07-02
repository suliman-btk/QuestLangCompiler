HERO FeastPlanner
BADGE 1211305566

STAT gs1, ml1, pr1, qt1, ln1, sb1, tp1, fn1, ea1, rc1, mx1, mn1, tm1, qs1, cp1, ch1
TALE nm$, fd$

DEFINE TRICK start
    SAY "===== QUEST FEAST BILL PLANNER ====="
    DRAW STAR 32
ENDTRICK

DEFINE TRICK cut
    DRAW STAR 18
ENDTRICK

PERFORM start

ASK "Enter table number:", nm$
ASK "Enter number of guests:", gs1
ASK "How many meal types:", ml1

sb1 = 0
mx1 = 0
mn1 = 999999
rc1 = LUCK(1000, 9999)

GRIND ml1 TIMES
    ASK "Enter meal name:", fd$
    ASK "Enter meal price:", pr1
    ASK "Enter quantity:", qt1

    ln1 = pr1 * qt1
    sb1 = sb1 + ln1
    mx1 = BIGGER(mx1, ln1)
    mn1 = SMALLER(mn1, ln1)

    SAY "Meal: " & fd$
    SAY "Meal line total: " & ln1
    PERFORM cut
ENDGRIND



ASK "Tip option 1 None 2 Normal 3 Generous:", tm1

CHOOSE tm1
    OPTION 1:
        tp1 = 0
        SAY "Tip selected: No tip"
    OPTION 2:
        tp1 = sb1  * 0.08
        SAY "Tip selected: Normal tip"
    OPTION 3:
        tp1 = sb1 * 0.15
        SAY "Tip selected: Generous tip"
    OTHERWISE
        tp1 = 0
        SAY "Tip selected: Unknown"
ENDCHOOSE

fn1 = sb1 + tp1
qs1 = ROUND(ROOT(fn1) * PI)

DECIDE gs1 > 0
    ea1 = ROUND(fn1 / gs1)
OTHERWISE
    ea1 = fn1
ENDDECIDE

ASK "Cash received:", cp1
ch1 = cp1 - fn1

PERFORM start
SAY "Table receipt number: " & rc1
SAY "Table Number: " & nm$
SAY "Subtotal: " & sb1



SAY "Tip: " & tp1
SAY "Final bill: " & fn1
SAY "Each guest pays: " & ea1
SAY "Feast score: " & qs1
SAY "Largest meal line: " & mx1
SAY "Smallest meal line: " & mn1
SAY "Change: " & ch1

DECIDE ch1 >= 0 AND fn1 >= 300
    SAY "Status: VIP feast completed"
OTHERWISE
    DECIDE ch1 >= 0
        SAY "Status: Payment completed"
    OTHERWISE
        SAY "Status: Not enough payment"
    ENDDECIDE
ENDDECIDE

DECIDE NOT (tm1 == 1) AND qs1 >= 40
    SAY "Message: Strong service rating expected"
OTHERWISE
    SAY "Message: Standard restaurant visit"
ENDDECIDE

SAY "===== ENJOY YOUR MEAL ====="
THEEND
