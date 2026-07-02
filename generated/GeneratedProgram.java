import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double id1;
    private static double bt1;
    private static double dm1;
    private static double tt1;
    private static double mx1;
    private static double mn1;
    private static double av1;
    private static double sp1;
    private static double rd1;
    private static double ar1;
    private static double cf1;
    private static double sa1;
    private static double sb1;
    private static double hy1;
    private static double xp1;
    private static double lv1;
    private static double gn1;
    private static double gl1;
    private static double rw1;
    private static double aa1;
    private static double bb1;
    private static String nm_text = "";
    private static String rk_text = "";
    private static String cl_text = "";

    private static void trick_banner() {
        System.out.println("*********************************");
        System.out.println("     QUESTFORGE HERO ANALYZER    ");
        System.out.println("*********************************");
        drawStar(33);
    }

    private static void trick_divider() {
        System.out.println("---------------------------------");
    }

    public static void main(String[] args) {
        trick_banner();
        System.out.print("Enter your hero name: ");
        nm_text = readText();
        id1 = luck(10000, 99999);
        System.out.println("Hero " + nm_text + " registered. Quest ID: " + id1);
        System.out.print("How many battles did you fight: ");
        bt1 = readNumber();
        tt1 = 0;
        mx1 = 0;
        mn1 = 999999;
        for (int grindCounter0 = 0; grindCounter0 < (int) Math.round(bt1); grindCounter0++) {
            System.out.print("Enter damage dealt this battle: ");
            dm1 = readNumber();
            tt1 = (tt1 + dm1);
            mx1 = Math.max(mx1, dm1);
            mn1 = Math.min(mn1, dm1);
            System.out.println("Logged damage: " + dm1);
        }
        if (bt1 > 0) {
            av1 = Math.round((tt1 / bt1));
            sp1 = Math.round(Math.sqrt(Math.abs((mx1 - mn1))));
        } else {
            av1 = 0;
            mn1 = 0;
            sp1 = 0;
        }
        trick_divider();
        System.out.println("Total damage: " + tt1);
        System.out.println("Average damage: " + av1);
        System.out.println("Highest hit: " + mx1);
        System.out.println("Lowest hit: " + mn1);
        System.out.println("Damage spread: " + sp1);
        System.out.print("Enter your shield radius: ");
        rd1 = readNumber();
        ar1 = Math.round(((Math.PI * rd1) * rd1));
        cf1 = Math.round(((2 * Math.PI) * rd1));
        System.out.println("Shield area: " + ar1);
        System.out.println("Shield circumference: " + cf1);
        System.out.print("Enter blade length A: ");
        sa1 = readNumber();
        System.out.print("Enter blade length B: ");
        sb1 = readNumber();
        hy1 = Math.round(Math.sqrt(((sa1 * sa1) + (sb1 * sb1))));
        System.out.println("Combined blade reach: " + hy1);
        xp1 = 0;
        lv1 = 1;
        gl1 = (tt1 + 50);
        do {
            gn1 = luck(20, 60);
            xp1 = (xp1 + gn1);
            lv1 = (lv1 + 1);
            System.out.println("Gained " + gn1 + " XP. Total XP: " + xp1);
        } while (!(xp1 >= gl1));
        System.out.println("Reached level: " + lv1);
        {
            double journeyStart1 = 1;
            double journeyEnd1 = 3;
            if (journeyStart1 <= journeyEnd1) {
                for (double journeyCounter1 = journeyStart1; journeyCounter1 <= journeyEnd1; journeyCounter1++) {
                    System.out.println("Preparing your reward...");
                }
            } else {
                for (double journeyCounter1 = journeyStart1; journeyCounter1 >= journeyEnd1; journeyCounter1--) {
                    System.out.println("Preparing your reward...");
                }
            }
        }
        aa1 = mx1;
        bb1 = mn1;
        System.out.println("Before trade -> high " + aa1 + " low " + bb1);
        double tradeTemp0 = aa1;
        aa1 = bb1;
        bb1 = tradeTemp0;
        System.out.println("After trade  -> high " + aa1 + " low " + bb1);
        if ((av1 >= 100 && lv1 >= 5)) {
            rk_text = "Legendary";
        } else {
            if ((av1 >= 50 || lv1 >= 3)) {
                rk_text = "Veteran";
            } else {
                if (!((av1 < 10))) {
                    rk_text = "Apprentice";
                } else {
                    rk_text = "Novice";
                }
            }
        }
        System.out.println("Hero rank: " + rk_text);
        System.out.print("Choose reward 1 Gold, 2 Gem, 3 Potion: ");
        rw1 = readNumber();
        switch ((int) Math.round(rw1)) {
            case 1:
                cl_text = "Gold Hoarder";
                System.out.println("You receive a chest of gold.");
                drawBox(4);
                break;
            case 2:
                cl_text = "Gem Seeker";
                System.out.println("You receive a shining gem.");
                drawTriangle(5);
                break;
            case 3:
                cl_text = "Potion Master";
                System.out.println("You receive a healing potion.");
                drawStar(6);
                break;
            default:
                cl_text = "Wanderer";
                System.out.println("No reward chosen.");
                break;
        }
        System.out.println("Hero title: " + cl_text);
        trick_divider();
        System.out.println("Summary for hero " + nm_text);
        System.out.println("Quest ID: " + id1);
        System.out.println("Final rank: " + rk_text);
        System.out.println("Final title: " + cl_text);
        System.out.println("Thanks for playing QuestForge!");
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
