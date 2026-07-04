import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double dy1;
    private static double bd1;
    private static double ex1;
    private static double tt1;
    private static double av1;
    private static double mx1;
    private static double mn1;
    private static double rm1;
    private static double md1;
    private static double id1;
    private static double sf1;
    private static double ds1;
    private static String nm_text = "";
    private static String lv_text = "";

    private static void trick_title() {
        System.out.println("========== QUEST TRAVEL BUDGET ==========");
        drawStar(34);
    }

    private static void trick_line() {
        drawStar(18);
    }

    public static void main(String[] args) {
        trick_title();
        System.out.print("Enter traveler name:");
        nm_text = readText();
        System.out.print("Enter total travel budget:");
        bd1 = readNumber();
        System.out.print("How many expense entries:");
        dy1 = readNumber();
        System.out.print("Choose travel mode 1 Economy 2 Standard 3 Premium:");
        md1 = readNumber();
        if (dy1 <= 0) {
            System.out.println("Invalid entry count. Using one expense entry.");
            dy1 = 1;
        } else {
            System.out.println("Expense entry count accepted.");
        }
        tt1 = 0;
        mx1 = 0;
        mn1 = 999999;
        id1 = luck(1000, 9999);
        for (int grindCounter0 = 0; grindCounter0 < (int) Math.round(dy1); grindCounter0++) {
            System.out.print("Enter expense amount:");
            ex1 = readNumber();
            if (ex1 < 0) {
                System.out.println("Negative expense changed to zero.");
                ex1 = 0;
            } else {
                System.out.println("Expense accepted.");
            }
            tt1 = (tt1 + ex1);
            mx1 = Math.max(mx1, ex1);
            mn1 = Math.min(mn1, ex1);
            trick_line();
        }
        switch ((int) Math.round(md1)) {
            case 1:
                ds1 = (tt1 * 0.05);
                lv_text = "Economy";
                break;
            case 2:
                ds1 = 0;
                lv_text = "Standard";
                break;
            case 3:
                ds1 = (tt1 * 0.08);
                lv_text = "Premium";
                break;
            default:
                ds1 = 0;
                lv_text = "Unknown";
                break;
        }
        tt1 = (tt1 - ds1);
        av1 = (tt1 / dy1);
        rm1 = (bd1 - tt1);
        sf1 = Math.abs(rm1);
        trick_title();
        System.out.println("Trip report ID: " + id1);
        System.out.println("Traveler: " + nm_text);
        System.out.println("Travel mode: " + lv_text);
        System.out.println("Budget: " + bd1);
        System.out.println("Mode adjustment: " + ds1);
        System.out.println("Total expenses: " + tt1);
        System.out.println("Average expense: " + av1);
        System.out.println("Highest expense: " + mx1);
        System.out.println("Lowest expense: " + mn1);
        System.out.println("Remaining budget: " + rm1);
        if (rm1 >= 0) {
            System.out.println("Budget status: Safe");
        } else {
            System.out.println("Budget status: Over budget by " + sf1);
        }
        if (av1 <= ((bd1 / dy1))) {
            System.out.println("Daily spending level: Controlled");
        } else {
            System.out.println("Daily spending level: Needs review");
        }
        drawBox(3);
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
