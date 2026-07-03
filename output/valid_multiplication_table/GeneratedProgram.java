import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double nn1;
    private static double lm1;
    private static double st1;
    private static double ii1;
    private static double rs1;
    private static double sm1;
    private static double hv1;
    private static String tg_text = "";

    private static void trick_header() {
        System.out.println("======= MULTIPLICATION QUEST =======");
        drawStar(28);
    }

    public static void main(String[] args) {
        trick_header();
        System.out.print("Enter table number:");
        nn1 = readNumber();
        System.out.print("Enter start value:");
        st1 = readNumber();
        System.out.print("Enter table limit:");
        lm1 = readNumber();
        sm1 = 0;
        ii1 = st1;
        {
            double journeyStart0 = st1;
            double journeyEnd0 = lm1;
            if (journeyStart0 <= journeyEnd0) {
                for (double journeyCounter0 = journeyStart0; journeyCounter0 <= journeyEnd0; journeyCounter0++) {
                    rs1 = (nn1 * ii1);
                    sm1 = (sm1 + rs1);
                    hv1 = (Math.round((rs1 / 2)) * 2);
                    if (hv1 == rs1) {
                        tg_text = "even";
                    } else {
                        tg_text = "odd";
                    }
                    System.out.println(nn1 + " x " + ii1 + " = " + rs1 + "  (" + tg_text + ")");
                    ii1 = (ii1 + 1);
                }
            } else {
                for (double journeyCounter0 = journeyStart0; journeyCounter0 >= journeyEnd0; journeyCounter0--) {
                    rs1 = (nn1 * ii1);
                    sm1 = (sm1 + rs1);
                    hv1 = (Math.round((rs1 / 2)) * 2);
                    if (hv1 == rs1) {
                        tg_text = "even";
                    } else {
                        tg_text = "odd";
                    }
                    System.out.println(nn1 + " x " + ii1 + " = " + rs1 + "  (" + tg_text + ")");
                    ii1 = (ii1 + 1);
                }
            }
        }
        System.out.println("Sum of all products = " + sm1);
        System.out.println("Average product = " + (sm1 / (((lm1 - st1) + 1))));
        if (nn1 > 12) {
            System.out.println("That is a large multiplication table!");
        } else {
            System.out.println("Standard multiplication table.");
        }
        drawTriangle(5);
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
