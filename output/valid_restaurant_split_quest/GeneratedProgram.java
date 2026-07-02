import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double gs1;
    private static double ml1;
    private static double pr1;
    private static double qt1;
    private static double ln1;
    private static double sb1;
    private static double tp1;
    private static double fn1;
    private static double ea1;
    private static double rc1;
    private static double mx1;
    private static double mn1;
    private static double tm1;
    private static double qs1;
    private static double cp1;
    private static double ch1;
    private static String nm_text = "";
    private static String fd_text = "";

    private static void trick_start() {
        System.out.println("===== QUEST FEAST BILL PLANNER =====");
        drawStar(32);
    }

    private static void trick_cut() {
        drawStar(18);
    }

    public static void main(String[] args) {
        trick_start();
        System.out.print("Enter table number:");
        nm_text = readText();
        System.out.print("Enter number of guests:");
        gs1 = readNumber();
        System.out.print("How many meal types:");
        ml1 = readNumber();
        sb1 = 0;
        mx1 = 0;
        mn1 = 999999;
        rc1 = luck(1000, 9999);
        for (int grindCounter0 = 0; grindCounter0 < (int) Math.round(ml1); grindCounter0++) {
            System.out.print("Enter meal name:");
            fd_text = readText();
            System.out.print("Enter meal price:");
            pr1 = readNumber();
            System.out.print("Enter quantity:");
            qt1 = readNumber();
            ln1 = (pr1 * qt1);
            sb1 = (sb1 + ln1);
            mx1 = Math.max(mx1, ln1);
            mn1 = Math.min(mn1, ln1);
            System.out.println("Meal: " + fd_text);
            System.out.println("Meal line total: " + ln1);
            trick_cut();
        }
        System.out.print("Tip option 1 None 2 Normal 3 Generous:");
        tm1 = readNumber();
        switch ((int) Math.round(tm1)) {
            case 1:
                tp1 = 0;
                System.out.println("Tip selected: No tip");
                break;
            case 2:
                tp1 = (sb1 * 0.08);
                System.out.println("Tip selected: Normal tip");
                break;
            case 3:
                tp1 = (sb1 * 0.15);
                System.out.println("Tip selected: Generous tip");
                break;
            default:
                tp1 = 0;
                System.out.println("Tip selected: Unknown");
                break;
        }
        fn1 = (sb1 + tp1);
        qs1 = Math.round((Math.sqrt(fn1) * Math.PI));
        if (gs1 > 0) {
            ea1 = Math.round((fn1 / gs1));
        } else {
            ea1 = fn1;
        }
        System.out.print("Cash received:");
        cp1 = readNumber();
        ch1 = (cp1 - fn1);
        trick_start();
        System.out.println("Table receipt number: " + rc1);
        System.out.println("Table Number: " + nm_text);
        System.out.println("Subtotal: " + sb1);
        System.out.println("Tip: " + tp1);
        System.out.println("Final bill: " + fn1);
        System.out.println("Each guest pays: " + ea1);
        System.out.println("Feast score: " + qs1);
        System.out.println("Largest meal line: " + mx1);
        System.out.println("Smallest meal line: " + mn1);
        System.out.println("Change: " + ch1);
        if ((ch1 >= 0 && fn1 >= 300)) {
            System.out.println("Status: VIP feast completed");
        } else {
            if (ch1 >= 0) {
                System.out.println("Status: Payment completed");
            } else {
                System.out.println("Status: Not enough payment");
            }
        }
        if ((!((tm1 == 1)) && qs1 >= 40)) {
            System.out.println("Message: Strong service rating expected");
        } else {
            System.out.println("Message: Standard restaurant visit");
        }
        System.out.println("===== ENJOY YOUR MEAL =====");
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
