import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double id1;
    private static double in1;
    private static double ex1;
    private static double am1;
    private static double te1;
    private static double mx1;
    private static double mn1;
    private static double av1;
    private static double rg1;
    private static double nt1;
    private static double sv1;
    private static double ms1;
    private static double ef1;
    private static double gl1;
    private static double bl1;
    private static double mo1;
    private static double ip1;
    private static double pj1;
    private static double tx1;
    private static double dc1;
    private static double fn1;
    private static double df1;
    private static double pm1;
    private static double aa1;
    private static double bb1;
    private static double rd1;
    private static double pc1;
    private static double rs1;
    private static double yr1;
    private static String nm_text = "";
    private static String rk_text = "";
    private static String tr_text = "";

    private static void trick_banner() {
        System.out.println("===================================");
        System.out.println("        MONEYQUEST PLANNER         ");
        System.out.println("===================================");
        drawStar(35);
    }

    private static void trick_divider() {
        System.out.println("-----------------------------------");
    }

    public static void main(String[] args) {
        trick_banner();
        System.out.print("Enter your name: ");
        nm_text = readText();
        id1 = luck(100000, 999999);
        System.out.println("Welcome " + nm_text + ". Your account id is " + id1);
        System.out.print("Enter your monthly income: ");
        in1 = readNumber();
        System.out.print("How many expense items: ");
        ex1 = readNumber();
        te1 = 0;
        mx1 = 0;
        mn1 = 1000000;
        for (int grindCounter0 = 0; grindCounter0 < (int) Math.round(ex1); grindCounter0++) {
            System.out.print("Enter expense amount: ");
            am1 = readNumber();
            te1 = (te1 + am1);
            mx1 = Math.max(mx1, am1);
            mn1 = Math.min(mn1, am1);
            System.out.println("Recorded expense: " + am1);
        }
        if (ex1 > 0) {
            av1 = Math.round((te1 / ex1));
            rg1 = Math.abs((mx1 - mn1));
        } else {
            av1 = 0;
            mn1 = 0;
            rg1 = 0;
        }
        nt1 = (in1 - te1);
        trick_divider();
        System.out.println("Total expenses: " + te1);
        System.out.println("Average expense: " + av1);
        System.out.println("Largest expense: " + mx1);
        System.out.println("Smallest expense: " + mn1);
        System.out.println("Expense range: " + rg1);
        System.out.println("Net disposable income: " + nt1);
        if ((nt1 < 0 || te1 > in1)) {
            System.out.println("Warning: you are spending more than you earn.");
        } else {
            if ((!((nt1 < 100)) && ex1 > 0)) {
                System.out.println("Great: you have healthy leftover funds.");
            } else {
                System.out.println("Caution: your leftover funds are tight.");
            }
        }
        System.out.print("Enter savings rate percent: ");
        sv1 = readNumber();
        ms1 = Math.round(((nt1 * sv1) / 100));
        ef1 = Math.max(ms1, 1);
        System.out.println("Planned monthly savings: " + ms1);
        System.out.print("Enter your savings goal: ");
        gl1 = readNumber();
        bl1 = 0;
        mo1 = 0;
        do {
            ip1 = luck(0, 5);
            bl1 = ((bl1 + ef1) + ip1);
            mo1 = (mo1 + 1);
            System.out.println("Month " + mo1 + " balance: " + bl1);
        } while (!(bl1 >= gl1));
        df1 = Math.abs((gl1 - bl1));
        System.out.println("Goal reached in months: " + mo1);
        System.out.println("Final balance: " + bl1);
        System.out.println("Distance from goal: " + df1);
        yr1 = 3;
        pj1 = bl1;
        {
            double journeyStart1 = 1;
            double journeyEnd1 = yr1;
            if (journeyStart1 <= journeyEnd1) {
                for (double journeyCounter1 = journeyStart1; journeyCounter1 <= journeyEnd1; journeyCounter1++) {
                    pj1 = (pj1 + (ef1 * 12));
                    System.out.println("Projected yearly balance: " + pj1);
                }
            } else {
                for (double journeyCounter1 = journeyStart1; journeyCounter1 >= journeyEnd1; journeyCounter1--) {
                    pj1 = (pj1 + (ef1 * 12));
                    System.out.println("Projected yearly balance: " + pj1);
                }
            }
        }
        aa1 = mx1;
        bb1 = mn1;
        System.out.println("Before swap -> largest " + aa1 + " smallest " + bb1);
        double tradeTemp0 = aa1;
        aa1 = bb1;
        bb1 = tradeTemp0;
        System.out.println("After swap  -> largest " + aa1 + " smallest " + bb1);
        tx1 = Math.round((te1 * 0.06));
        if (te1 >= 1000) {
            dc1 = Math.round((te1 * 0.10));
        } else {
            if (te1 >= 500) {
                dc1 = Math.round((te1 * 0.05));
            } else {
                dc1 = 0;
            }
        }
        fn1 = ((te1 + tx1) - dc1);
        System.out.println("Tax on spending: " + tx1);
        System.out.println("Loyalty discount: " + dc1);
        System.out.println("Adjusted spend: " + fn1);
        System.out.print("Choose account 1 Basic, 2 Gold, 3 Platinum: ");
        pm1 = readNumber();
        switch ((int) Math.round(pm1)) {
            case 1:
                tr_text = "Basic";
                System.out.println("Basic account selected.");
                drawBox(3);
                break;
            case 2:
                tr_text = "Gold";
                System.out.println("Gold account selected.");
                drawTriangle(5);
                break;
            case 3:
                tr_text = "Platinum";
                System.out.println("Platinum account selected.");
                drawStar(7);
                break;
            default:
                tr_text = "Unranked";
                System.out.println("Unknown account type.");
                break;
        }
        System.out.println("Account tier: " + tr_text);
        if ((nt1 >= 500 && mo1 <= 12)) {
            rk_text = "Excellent";
        } else {
            if ((nt1 >= 100 || bl1 >= gl1)) {
                rk_text = "Stable";
            } else {
                rk_text = "At Risk";
            }
        }
        System.out.println("Financial health: " + rk_text);
        rd1 = Math.min((bl1 / 100), 6);
        pc1 = Math.round(((Math.PI * rd1) * rd1));
        rs1 = Math.round(Math.sqrt((te1 + rg1)));
        System.out.println("Savings pie chart size: " + pc1);
        System.out.println("Budget risk score: " + rs1);
        drawStar(Math.min(pc1, 40));
        trick_divider();
        System.out.println("Summary for " + nm_text);
        System.out.println("Account id: " + id1);
        System.out.println("Account tier: " + tr_text);
        System.out.println("Financial health: " + rk_text);
        System.out.println("Thank you for using MoneyQuest!");
    }

    private static double readNumber() {
        while (true) {
            String line = input.nextLine().replace("\uFEFF", "").trim();
            if (!line.isEmpty()) {
                return Double.parseDouble(line);
            }
        }
    }

    private static String readText() {
        return input.nextLine().replace("\uFEFF", "");
    }

    private static double luck(double low, double high) {
        int min = (int) Math.round(Math.min(low, high));
        int max = (int) Math.round(Math.max(low, high));
        return random.nextInt(max - min + 1) + min;
    }

    private static int normalizeSize(double size) {
        return Math.max(0, (int) Math.round(size));
    }

    private static void drawStar(double size) {
        int n = normalizeSize(size);
        System.out.println("*".repeat(n));
    }

    private static void drawBox(double size) {
        int n = normalizeSize(size);
        for (int row = 0; row < n; row++) {
            System.out.println("*".repeat(n));
        }
    }

    private static void drawTriangle(double size) {
        int n = normalizeSize(size);
        for (int row = 1; row <= n; row++) {
            System.out.println("*".repeat(row));
        }
    }
}
