import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double aa1;
    private static double bb1;
    private static double op1;
    private static double rs1;

    public static void main(String[] args) {
        System.out.print("Enter first number:");
        aa1 = readNumber();
        System.out.print("Enter second number:");
        bb1 = readNumber();
        System.out.print("Choose operation 1 add 2 subtract 3 multiply 4 divide:");
        op1 = readNumber();
        switch ((int) Math.round(op1)) {
            case 1:
                rs1 = (aa1 + bb1);
                System.out.println("Addition result is " + rs1);
                break;
            case 2:
                rs1 = (aa1 - bb1);
                System.out.println("Subtraction result is " + rs1);
                break;
            case 3:
                rs1 = (aa1 * bb1);
                System.out.println("Multiplication result is " + rs1);
                break;
            case 4:
                if (bb1 != 0) {
                    rs1 = (aa1 / bb1);
                    System.out.println("Division result is " + rs1);
                } else {
                    System.out.println("Cannot divide by zero.");
                }
                break;
            default:
                System.out.println("Unknown calculator operation.");
                break;
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
