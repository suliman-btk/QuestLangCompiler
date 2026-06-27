import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double hp1;
    private static double pa1;
    private static double rl1;
    private static double lv1;
    private static String nm_text = "";

    private static void trick_greet() {
        System.out.println("A new hero enters the dungeon!");
    }

    public static void main(String[] args) {
        trick_greet();
        System.out.print("Enter your hero name:");
        nm_text = readText();
        hp1 = 100;
        lv1 = 1;
        System.out.println("Welcome, " + nm_text + ", your journey begins!");
        System.out.print("Choose a path - 1) Forest 2) Cave 3) River:");
        pa1 = readNumber();
        switch ((int) Math.round(pa1)) {
            case 1:
                System.out.println("You enter the forest.");
                drawTriangle(4);
                break;
            case 2:
                System.out.println("You enter the cave.");
                drawBox(3);
                break;
            case 3:
                System.out.println("You follow the river.");
                drawStar(5);
                break;
            default:
                System.out.println("You stay put and rest.");
                break;
        }
        rl1 = luck(1, 20);
        System.out.println("You rolled " + rl1 + " for the encounter!");
        do {
            hp1 = (hp1 - luck(1, 10));
            System.out.println("Remaining HP: " + hp1);
        } while (!(hp1 <= 0));
        System.out.println("The quest has ended for " + nm_text);
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
