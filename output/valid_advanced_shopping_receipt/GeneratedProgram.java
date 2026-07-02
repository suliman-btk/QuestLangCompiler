import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double ct1;
    private static double pr1;
    private static double qt1;
    private static double ln1;
    private static double sb1;
    private static double ds1;
    private static double tx1;
    private static double fn1;
    private static double pm1;
    private static double rc1;
    private static String nm_text = "";
    private static String pn_text = "";

    private static void trick_header() {
        System.out.println("===== QUEST SHOP RECEIPT =====");
        drawStar(25);
    }

    public static void main(String[] args) {
        trick_header();
        System.out.print("Enter customer name: ");
        nm_text = readText();
        System.out.print("How many products: ");
        ct1 = readNumber();
        sb1 = 0;
        rc1 = luck(1000, 9999);
        for (int grindCounter0 = 0; grindCounter0 < (int) Math.round(ct1); grindCounter0++) {
            System.out.print("Enter product name: ");
            pn_text = readText();
            System.out.print("Enter item price: ");
            pr1 = readNumber();
            System.out.print("Enter quantity: ");
            qt1 = readNumber();
            ln1 = (pr1 * qt1);
            sb1 = (sb1 + ln1);
            System.out.println("Product: " + pn_text);
            System.out.println("Line total: " + ln1);
        }
        if (sb1 >= 300) {
            ds1 = (sb1 * 0.15);
        } else {
            if (sb1 >= 100) {
                ds1 = (sb1 * 0.10);
            } else {
                ds1 = 0;
            }
        }
        tx1 = (((sb1 - ds1)) * 0.06);
        fn1 = ((sb1 - ds1) + tx1);
        System.out.print("Payment method 1 Cash, 2 Card, 3 E-wallet: ");
        pm1 = readNumber();
        switch ((int) Math.round(pm1)) {
            case 1:
                System.out.println("Payment method: Cash");
                break;
            case 2:
                System.out.println("Payment method: Card");
                break;
            case 3:
                System.out.println("Payment method: E-wallet");
                break;
            default:
                System.out.println("Payment method: Unknown");
                break;
        }
        System.out.println("Receipt number: " + rc1);
        System.out.println("Customer: " + nm_text);
        System.out.println("Subtotal: " + sb1);
        System.out.println("Discount: " + ds1);
        System.out.println("Tax: " + tx1);
        System.out.println("Final bill: " + fn1);
        if (fn1 >= 300) {
            System.out.println("Bill level: High value customer");
        } else {
            if (fn1 >= 100) {
                System.out.println("Bill level: Normal customer");
            } else {
                System.out.println("Bill level: Small purchase");
            }
        }
        System.out.println("===== THANK YOU =====");
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
