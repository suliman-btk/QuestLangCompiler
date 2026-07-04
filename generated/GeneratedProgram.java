import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double ct1;
    private static double sc1;
    private static double tt1;
    private static double av1;
    private static double mx1;
    private static double mn1;
    private static double df1;
    private static double rp1;
    private static double id1;
    private static String nm_text = "";
    private static String gd_text = "";

    private static void trick_banner() {
        System.out.println("===== QUEST ACADEMIC ADVISOR =====");
        drawBox(4);
    }

    public static void main(String[] args) {
        trick_banner();
        System.out.print("Enter student name: ");
        nm_text = readText();
        System.out.print("How many course scores: ");
        ct1 = readNumber();
        if (ct1 <= 0) {
            System.out.println("Invalid course count. Using one course.");
            ct1 = 1;
        } else {
            System.out.println("Course count accepted.");
        }
        tt1 = 0;
        mx1 = 0;
        mn1 = 100;
        id1 = luck(1000, 9999);
        for (int grindCounter0 = 0; grindCounter0 < (int) Math.round(ct1); grindCounter0++) {
            System.out.print("Enter course score: ");
            sc1 = readNumber();
            if (sc1 < 0) {
                System.out.println("Score below zero adjusted to zero.");
                sc1 = 0;
            } else {
                if (sc1 > 100) {
                    System.out.println("Score above 100 adjusted to 100.");
                    sc1 = 100;
                } else {
                    System.out.println("Score accepted.");
                }
            }
            tt1 = (tt1 + sc1);
            mx1 = Math.max(mx1, sc1);
            mn1 = Math.min(mn1, sc1);
        }
        av1 = (tt1 / ct1);
        df1 = Math.abs((mx1 - mn1));
        if (av1 >= 85) {
            gd_text = "Excellent";
        } else {
            if (av1 >= 70) {
                gd_text = "Good";
            } else {
                if (av1 >= 50) {
                    gd_text = "Pass";
                } else {
                    gd_text = "At Risk";
                }
            }
        }
        System.out.print("Choose report 1 Summary, 2 Advice, 3 Full: ");
        rp1 = readNumber();
        switch ((int) Math.round(rp1)) {
            case 1:
                System.out.println("Summary report selected.");
                break;
            case 2:
                System.out.println("Advice report selected.");
                if (av1 >= 70) {
                    System.out.println("Advice: keep your current study plan.");
                } else {
                    System.out.println("Advice: increase weekly revision time.");
                }
                break;
            case 3:
                System.out.println("Full report selected.");
                System.out.println("Highest score: " + mx1);
                System.out.println("Lowest score: " + mn1);
                System.out.println("Score range: " + df1);
                break;
            default:
                System.out.println("Unknown report type. Showing summary.");
                break;
        }
        System.out.println("Report ID: " + id1);
        System.out.println("Student: " + nm_text);
        System.out.println("Average score: " + av1);
        System.out.println("Performance level: " + gd_text);
        if (av1 >= 50) {
            System.out.println("Academic standing: Passed");
        } else {
            System.out.println("Academic standing: Needs support");
        }
        System.out.println("===== END OF REPORT =====");
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
