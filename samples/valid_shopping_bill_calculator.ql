HERO SulimanAlkatheri
BADGE 1211305566
NOTE Student: ALKATHERI SULAIMAN ALI MAHDI
NOTE Student ID: 1211305566

NOTE Advanced shopping bill with membership, tiers, tax, change
STAT ni1, pr1, qt1, ln1, sb1, ds1, tx1, fn1, mx1, mn1, mt1, cp1, ch1
TALE it$, mb$

DEFINE TRICK receipt
    SAY "========== QUEST MART RECEIPT =========="
    DRAW STAR 34
ENDTRICK

DEFINE TRICK sep
    DRAW STAR 20
ENDTRICK

PERFORM receipt

ASK "Membership? 1 Silver 2 Gold 3 None:", mt1
ASK "How many items:", ni1

sb1 = 0
mx1 = 0
mn1 = 999999

GRIND ni1 TIMES
    ASK "Enter item name:", it$
    ASK "Enter unit price:", pr1
    ASK "Enter quantity:", qt1
    ln1 = pr1 * qt1
    sb1 = sb1 + ln1
    mx1 = BIGGER(mx1, ln1)
    mn1 = SMALLER(mn1, ln1)
    SAY "Item: " & it$ & "  line total: " & ln1
    PERFORM sep
ENDGRIND

CHOOSE mt1
    OPTION 1:
        ds1 = sb1 * 0.05
        mb$ = "Silver"
    OPTION 2:
        ds1 = sb1 * 0.12
        mb$ = "Gold"
    OTHERWISE
        ds1 = 0
        mb$ = "Guest"
ENDCHOOSE

DECIDE sb1 >= 200
    ds1 = ds1 + sb1 * 0.05
OTHERWISE
    ds1 = ds1 + 0
ENDDECIDE

tx1 = (sb1 - ds1) * 0.06
fn1 = sb1 - ds1 + tx1

ASK "Cash received:", cp1
ch1 = cp1 - fn1

PERFORM receipt
SAY "Member: " & mb$
SAY "Subtotal: " & sb1
SAY "Discount: " & ds1
SAY "Tax (6%): " & tx1
SAY "Final bill: " & fn1
SAY "Most expensive line: " & mx1
SAY "Cheapest line: " & mn1
SAY "Change: " & ch1

DECIDE ch1 >= 0
    SAY "Payment complete. Thank you!"
OTHERWISE
    SAY "Insufficient payment. Short by " & ABS(ch1)
ENDDECIDE

DRAW TRIANGLE 4
THEEND
