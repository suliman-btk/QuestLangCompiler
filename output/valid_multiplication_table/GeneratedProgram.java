import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double nn1;
    private static double lm1;
    private static double ii1;
    private static double rs1;

    public static void main(String[] args) {
        System.out.print("Enter table number:");
        nn1 = readNumber();
        System.out.print("Enter table limit:");
        lm1 = readNumber();
        ii1 = 1;
        {
            double journeyStart0 = 1;
            double journeyEnd0 = lm1;
            if (journeyStart0 <= journeyEnd0) {
                for (double journeyCounter0 = journeyStart0; journeyCounter0 <= journeyEnd0; journeyCounter0++) {
                    rs1 = (nn1 * ii1);
                    System.out.println(nn1 + " x " + ii1 + " = " + rs1);
                    ii1 = (ii1 + 1);
                }
            } else {
                for (double journeyCounter0 = journeyStart0; journeyCounter0 >= journeyEnd0; journeyCounter0--) {
                    rs1 = (nn1 * ii1);
                    System.out.println(nn1 + " x " + ii1 + " = " + rs1);
                    ii1 = (ii1 + 1);
                }
            }
        }
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
