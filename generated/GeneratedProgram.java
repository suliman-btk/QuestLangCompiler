import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double nn1;
    private static double rm1;
    private static double xx1;
    private static double op1;
    private static double ac1;
    private static double gc1;
    private static double mm1;
    private static double cn1;

    private static void trick_banner() {
        System.out.println("====================================");
        System.out.println("     QUESTLANG EXPRESSION CALC       ");
        System.out.println("====================================");
        drawStar(30);
    }

    private static void trick_line() {
        drawStar(20);
    }

    public static void main(String[] args) {
        trick_banner();
        mm1 = 0;
        cn1 = 0;
        do {
            System.out.print("How many numbers:");
            nn1 = readNumber();
            System.out.print("Enter number 1:");
            ac1 = readNumber();
            rm1 = (nn1 - 1);
            for (int grindCounter0 = 0; grindCounter0 < (int) Math.round(rm1); grindCounter0++) {
                System.out.print("Operator 1+ 2- 3* 4/ 5max 6min:");
                op1 = readNumber();
                System.out.print("Enter next number:");
                xx1 = readNumber();
                switch ((int) Math.round(op1)) {
                    case 1:
                        ac1 = (ac1 + xx1);
                        break;
                    case 2:
                        ac1 = (ac1 - xx1);
                        break;
                    case 3:
                        ac1 = (ac1 * xx1);
                        break;
                    case 4:
                        if (xx1 != 0) {
                            ac1 = (ac1 / xx1);
                        } else {
                            System.out.println("Skipped a division by zero.");
                        }
                        break;
                    case 5:
                        ac1 = Math.max(ac1, xx1);
                        break;
                    case 6:
                        ac1 = Math.min(ac1, xx1);
                        break;
                    default:
                        System.out.println("Unknown operator, keeping current value.");
                        break;
                }
                System.out.println("Running value = " + ac1);
            }
            System.out.println("Final result = " + ac1);
            mm1 = (mm1 + ac1);
            cn1 = (cn1 + 1);
            trick_line();
            System.out.println("Running memory total = " + mm1);
            System.out.print("Compute another? 1 yes 0 no:");
            gc1 = readNumber();
        } while (!(gc1 == 0));
        if (cn1 > 0) {
            System.out.println("Number of calculations = " + cn1);
            System.out.println("Average of results = " + (mm1 / cn1));
        } else {
            System.out.println("No calculations performed.");
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
