import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double ni1;
    private static double pr1;
    private static double qt1;
    private static double ln1;
    private static double sb1;
    private static double ds1;
    private static double tx1;
    private static double fn1;
    private static double mx1;
    private static double mn1;
    private static double mt1;
    private static double cp1;
    private static double ch1;
    private static String it_text = "";
    private static String mb_text = "";

    private static void trick_receipt() {
        System.out.println("========== QUEST MART RECEIPT ==========");
        drawStar(34);
    }

    private static void trick_sep() {
        drawStar(20);
    }

    public static void main(String[] args) {
        trick_receipt();
        System.out.print("Membership? 1 Silver 2 Gold 3 None:");
        mt1 = readNumber();
        System.out.print("How many items:");
        ni1 = readNumber();
        sb1 = 0;
        mx1 = 0;
        mn1 = 999999;
        for (int grindCounter0 = 0; grindCounter0 < (int) Math.round(ni1); grindCounter0++) {
            System.out.print("Enter item name:");
            it_text = readText();
            System.out.print("Enter unit price:");
            pr1 = readNumber();
            System.out.print("Enter quantity:");
            qt1 = readNumber();
            ln1 = (pr1 * qt1);
            sb1 = (sb1 + ln1);
            mx1 = Math.max(mx1, ln1);
            mn1 = Math.min(mn1, ln1);
            System.out.println("Item: " + it_text + "  line total: " + ln1);
            trick_sep();
        }
        switch ((int) Math.round(mt1)) {
            case 1:
                ds1 = (sb1 * 0.05);
                mb_text = "Silver";
                break;
            case 2:
                ds1 = (sb1 * 0.12);
                mb_text = "Gold";
                break;
            default:
                ds1 = 0;
                mb_text = "Guest";
                break;
        }
        if (sb1 >= 200) {
            ds1 = (ds1 + (sb1 * 0.05));
        } else {
            ds1 = (ds1 + 0);
        }
        tx1 = (((sb1 - ds1)) * 0.06);
        fn1 = ((sb1 - ds1) + tx1);
        System.out.print("Cash received:");
        cp1 = readNumber();
        ch1 = (cp1 - fn1);
        trick_receipt();
        System.out.println("Member: " + mb_text);
        System.out.println("Subtotal: " + sb1);
        System.out.println("Discount: " + ds1);
        System.out.println("Tax (6%): " + tx1);
        System.out.println("Final bill: " + fn1);
        System.out.println("Most expensive line: " + mx1);
        System.out.println("Cheapest line: " + mn1);
        System.out.println("Change: " + ch1);
        if (ch1 >= 0) {
            System.out.println("Payment complete. Thank you!");
        } else {
            System.out.println("Insufficient payment. Short by " + Math.abs(ch1));
        }
        drawTriangle(4);
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
