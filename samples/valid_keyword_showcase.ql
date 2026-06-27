HERO Zara
BADGE 1211305566

STAT hp1, pa1, rl1, lv1, aa1, bb1, cc1, dd1, ee1, ff1, gg1
TALE nm$, ms$, qa$

NOTE showcase program for the creative QuestLang keywords
DEFINE TRICK greet
    SAY "A brave hero enters the quest!"
ENDTRICK

DEFINE TRICK banner
    PERFORM greet
    DRAW STAR 6
ENDTRICK

PERFORM banner

ASK "Enter your hero name:", nm$
ASK "Choose path 1) Forest 2) Cave 3) Tower:", pa1

hp1 = 100
lv1 = 1
aa1 = 4
bb1 = 9
cc1 = ROUND(PI)
dd1 = ROOT(bb1)
ee1 = ABS(aa1 - bb1)
ff1 = BIGGER(aa1, bb1)
gg1 = SMALLER(aa1, bb1)
rl1 = LUCK(1, 6)
ms$ = "Hero"
qa$ = "Quest"

SAY ms$ & " " & nm$ & " begins the " & qa$
SAY "Math tools: PI rounded " & cc1 & ", root " & dd1 & ", abs " & ee1
SAY "Bigger " & ff1 & ", smaller " & gg1 & ", luck roll " & rl1

TRADE aa1, bb1
SAY "After TRADE, aa1 is " & aa1

DECIDE NOT (pa1 == 2) AND hp1 > 0 OR lv1 == 1
    SAY "The path is safe."
OTHERWISE
    SAY "The cave is dangerous."
ENDDECIDE

CHOOSE pa1
    OPTION 1 :
        SAY "Forest route selected."
        DRAW TRIANGLE 4
    OPTION 2 :
        SAY "Cave route selected."
        DRAW BOX 3
    OPTION 3 :
        SAY "Tower route selected."
        DRAW STAR 5
    OTHERWISE
        SAY "Unknown path, resting at camp."
ENDCHOOSE

GRIND 2 TIMES
    SAY "Training level " & lv1
    lv1 = lv1 + 1
ENDGRIND

GRIND
    hp1 = hp1 - 25
    SAY "HP now " & hp1
UNTIL hp1 <= 50

JOURNEY FROM 1 TO 3
    SAY "Forward step"
ENDJOURNEY

JOURNEY FROM 3 TO 1
    SAY "Return step"
ENDJOURNEY

THEEND
