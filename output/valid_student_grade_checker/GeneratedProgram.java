import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double sc1;
    private static String nm_text = "";
    private static String gd_text = "";

    public static void main(String[] args) {
        System.out.print("Enter student name:");
        nm_text = readText();
        System.out.print("Enter score:");
        sc1 = readNumber();
        if (sc1 >= 90) {
            gd_text = "A";
        } else {
            if (sc1 >= 80) {
                gd_text = "B";
            } else {
                if (sc1 >= 70) {
                    gd_text = "C";
                } else {
                    if (sc1 >= 60) {
                        gd_text = "D";
                    } else {
                        gd_text = "F";
                    }
                }
            }
        }
        System.out.println(nm_text + " scored " + sc1 + " and received grade " + gd_text);
        if (sc1 >= 60) {
            System.out.println("Status: Pass");
        } else {
            System.out.println("Status: Fail");
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
