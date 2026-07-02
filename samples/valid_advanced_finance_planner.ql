HERO AhmedBalfaqih

NOTE =====================================================
NOTE  MoneyQuest : Advanced Personal Finance Planner
NOTE  Showcases every QuestLang keyword and math builtin
NOTE =====================================================

STAT id1, in1, ex1, am1, te1, mx1, mn1, av1, rg1
STAT nt1, sv1, ms1, ef1, gl1, bl1, mo1, ip1, pj1
STAT tx1, dc1, fn1, df1, pm1, aa1, bb1
STAT rd1, pc1, rs1, yr1
TALE nm$, rk$, tr$

DEFINE TRICK banner
    SAY "==================================="
    SAY "        MONEYQUEST PLANNER         "
    SAY "==================================="
    DRAW STAR 35
ENDTRICK

DEFINE TRICK divider
    SAY "-----------------------------------"
ENDTRICK

PERFORM banner

NOTE ---- Account registration with a random id (LUCK) ----
ASK "Enter your name: ", nm$
id1 = LUCK(100000, 999999)
SAY "Welcome " & nm$ & ". Your account id is " & id1

ASK "Enter your monthly income: ", in1

NOTE ---- Expense collection loop (GRIND TIMES) ----
ASK "How many expense items: ", ex1

te1 = 0
mx1 = 0
mn1 = 1000000

GRIND ex1 TIMES
    ASK "Enter expense amount: ", am1
    te1 = te1 + am1
    mx1 = BIGGER(mx1, am1)
    mn1 = SMALLER(mn1, am1)
    SAY "Recorded expense: " & am1
ENDGRIND

NOTE ---- Averages and range (guard divide, ABS) ----
DECIDE ex1 > 0
    av1 = ROUND(te1 / ex1)
    rg1 = ABS(mx1 - mn1)
OTHERWISE
    av1 = 0
    mn1 = 0
    rg1 = 0
ENDDECIDE

nt1 = in1 - te1

PERFORM divider
SAY "Total expenses: " & te1
SAY "Average expense: " & av1
SAY "Largest expense: " & mx1
SAY "Smallest expense: " & mn1
SAY "Expense range: " & rg1
SAY "Net disposable income: " & nt1

NOTE ---- Warn if spending exceeds income (AND / OR / NOT) ----
DECIDE nt1 < 0 OR te1 > in1
    SAY "Warning: you are spending more than you earn."
OTHERWISE
    DECIDE NOT (nt1 < 100) AND ex1 > 0
        SAY "Great: you have healthy leftover funds."
    OTHERWISE
        SAY "Caution: your leftover funds are tight."
    ENDDECIDE
ENDDECIDE

NOTE ---- Monthly savings plan ----
ASK "Enter savings rate percent: ", sv1
ms1 = ROUND(nt1 * sv1 / 100)
ef1 = BIGGER(ms1, 1)
SAY "Planned monthly savings: " & ms1

ASK "Enter your savings goal: ", gl1

NOTE ---- Grow balance until goal reached (GRIND UNTIL) ----
bl1 = 0
mo1 = 0

GRIND
    ip1 = LUCK(0, 5)
    bl1 = bl1 + ef1 + ip1
    mo1 = mo1 + 1
    SAY "Month " & mo1 & " balance: " & bl1
UNTIL bl1 >= gl1

df1 = ABS(gl1 - bl1)
SAY "Goal reached in months: " & mo1
SAY "Final balance: " & bl1
SAY "Distance from goal: " & df1

NOTE ---- Three year outlook (JOURNEY range loop) ----
yr1 = 3
pj1 = bl1
JOURNEY FROM 1 TO yr1
    pj1 = pj1 + ef1 * 12
    SAY "Projected yearly balance: " & pj1
ENDJOURNEY

NOTE ---- Swap the largest and smallest expense (TRADE) ----
aa1 = mx1
bb1 = mn1
SAY "Before swap -> largest " & aa1 & " smallest " & bb1
TRADE aa1, bb1
SAY "After swap  -> largest " & aa1 & " smallest " & bb1

NOTE ---- Tax and loyalty discount on total spend ----
tx1 = ROUND(te1 * 0.06)
DECIDE te1 >= 1000
    dc1 = ROUND(te1 * 0.10)
OTHERWISE
    DECIDE te1 >= 500
        dc1 = ROUND(te1 * 0.05)
    OTHERWISE
        dc1 = 0
    ENDDECIDE
ENDDECIDE
fn1 = te1 + tx1 - dc1
SAY "Tax on spending: " & tx1
SAY "Loyalty discount: " & dc1
SAY "Adjusted spend: " & fn1

NOTE ---- Account tier menu (CHOOSE) ----
ASK "Choose account 1 Basic, 2 Gold, 3 Platinum: ", pm1
CHOOSE pm1
    OPTION 1:
        tr$ = "Basic"
        SAY "Basic account selected."
        DRAW BOX 3
    OPTION 2:
        tr$ = "Gold"
        SAY "Gold account selected."
        DRAW TRIANGLE 5
    OPTION 3:
        tr$ = "Platinum"
        SAY "Platinum account selected."
        DRAW STAR 7
    OTHERWISE
        tr$ = "Unranked"
        SAY "Unknown account type."
ENDCHOOSE
SAY "Account tier: " & tr$

NOTE ---- Financial health rating (nested DECIDE + AND / OR) ----
DECIDE nt1 >= 500 AND mo1 <= 12
    rk$ = "Excellent"
OTHERWISE
    DECIDE nt1 >= 100 OR bl1 >= gl1
        rk$ = "Stable"
    OTHERWISE
        rk$ = "At Risk"
    ENDDECIDE
ENDDECIDE
SAY "Financial health: " & rk$

NOTE ---- Bonus math showcase: PI pie chart and ROOT risk ----
rd1 = SMALLER(bl1 / 100, 6)
pc1 = ROUND(PI * rd1 * rd1)
rs1 = ROUND(ROOT(te1 + rg1))
SAY "Savings pie chart size: " & pc1
SAY "Budget risk score: " & rs1
DRAW STAR SMALLER(pc1, 40)

PERFORM divider
SAY "Summary for " & nm$
SAY "Account id: " & id1
SAY "Account tier: " & tr$
SAY "Financial health: " & rk$
SAY "Thank you for using MoneyQuest!"

THEEND
