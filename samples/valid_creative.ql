HERO Omar
STAT hp1, pa1, rl1, lv1
TALE nm$

NOTE define a reusable greeting trick
DEFINE TRICK greet
    SAY "A new hero enters the dungeon!"
ENDTRICK

PERFORM greet
ASK "Enter your hero name:", nm$

hp1 = 100
lv1 = 1
SAY "Welcome, " & nm$ & ", your journey begins!"

ASK "Choose a path - 1) Forest 2) Cave 3) River:", pa1
CHOOSE pa1
    OPTION 1 :
        SAY "You enter the forest."
        DRAW TRIANGLE 4
    OPTION 2 :
        SAY "You enter the cave."
        DRAW BOX 3
    OPTION 3 :
        SAY "You follow the river."
        DRAW STAR 5
    OTHERWISE
        SAY "You stay put and rest."
ENDCHOOSE

rl1 = LUCK(1, 20)
SAY "You rolled " & rl1 & " for the encounter!"

GRIND
    hp1 = hp1 - LUCK(1, 10)
    SAY "Remaining HP: " & hp1
UNTIL hp1 <= 0

SAY "The quest has ended for " & nm$
THEEND
