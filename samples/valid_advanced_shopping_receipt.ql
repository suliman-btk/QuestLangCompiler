HERO ShopMaster
BADGE 1211305566

STAT ct1, pr1, qt1, ln1, sb1, ds1, tx1, fn1, pm1, rc1
TALE nm$, pn$

DEFINE TRICK header
    SAY "===== QUEST SHOP RECEIPT ====="
    DRAW STAR 25
ENDTRICK

PERFORM header

ASK "Enter customer name: ", nm$
ASK "How many products: ", ct1

sb1 = 0
rc1 = LUCK(1000, 9999)

GRIND ct1 TIMES
    ASK "Enter product name: ", pn$
    ASK "Enter item price: ", pr1
    ASK "Enter quantity: ", qt1

    ln1 = pr1 * qt1
    sb1 = sb1 + ln1

    SAY "Product: " & pn$
    SAY "Line total: " & ln1
ENDGRIND

DECIDE sb1 >= 300
    ds1 = sb1 * 0.15
OTHERWISE
    DECIDE sb1 >= 100
        ds1 = sb1 * 0.10
    OTHERWISE
        ds1 = 0
    ENDDECIDE
ENDDECIDE

tx1 = (sb1 - ds1) * 0.06
fn1 = sb1 - ds1 + tx1

ASK "Payment method 1 Cash, 2 Card, 3 E-wallet: ", pm1

CHOOSE pm1
    OPTION 1:
        SAY "Payment method: Cash"
    OPTION 2:
        SAY "Payment method: Card"
    OPTION 3:
        SAY "Payment method: E-wallet"
    OTHERWISE
        SAY "Payment method: Unknown"
ENDCHOOSE

SAY "Receipt number: " & rc1
SAY "Customer: " & nm$
SAY "Subtotal: " & sb1
SAY "Discount: " & ds1
SAY "Tax: " & tx1
SAY "Final bill: " & fn1

DECIDE fn1 >= 300
    SAY "Bill level: High value customer"
OTHERWISE
    DECIDE fn1 >= 100
        SAY "Bill level: Normal customer"
    OTHERWISE
        SAY "Bill level: Small purchase"
    ENDDECIDE
ENDDECIDE

SAY "===== THANK YOU ====="

THEEND
