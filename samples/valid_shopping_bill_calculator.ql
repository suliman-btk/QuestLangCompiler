HERO ShopHero
BADGE 1211305566

STAT pr1, qt1, sb1, ds1, tx1, fn1
TALE it$

ASK "Enter item name:", it$
ASK "Enter item price:", pr1
ASK "Enter quantity:", qt1

sb1 = pr1 * qt1

DECIDE sb1 >= 100
    ds1 = sb1 * 0.10
OTHERWISE
    ds1 = 0
ENDDECIDE

tx1 = (sb1 - ds1) * 0.06
fn1 = sb1 - ds1 + tx1

SAY "Item: " & it$
SAY "Subtotal: " & sb1
SAY "Discount: " & ds1
SAY "Tax: " & tx1
SAY "Final bill: " & fn1

THEEND
