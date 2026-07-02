import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double cs1;
    private static double cr1;
    private static double mk1;
    private static double tc1;
    private static double ws1;
    private static double av1;
    private static double fe1;
    private static double sp1;
    private static double rm1;
    private static double st1;
    private static double hr1;
    private static double wk1;
    private static double ii1;
    private static double rv1;
    private static double mx1;
    private static double mn1;
    private static double gp1;
    private static double id1;
    private static double bo1;
    private static String nm_text = "";
    private static String cn_text = "";
    private static String el_text = "";

    private static void trick_cover() {
        System.out.println("===== QUEST SCHOLARSHIP COURSE PLANNER =====");
        drawBox(3);
    }

    private static void trick_markline() {
        drawStar(24);
    }

    public static void main(String[] args) {
        trick_cover();
        System.out.print("Enter student name:");
        nm_text = readText();
        System.out.print("Enter total semester fee:");
        fe1 = readNumber();
        System.out.print("How many courses:");
        cs1 = readNumber();
        System.out.print("Scholarship type 1 Merit 2 Need 3 Sports:");
        st1 = readNumber();
        System.out.print("Weeks left before exam:");
        wk1 = readNumber();
        id1 = luck(1000, 9999);
        tc1 = 0;
        ws1 = 0;
        mx1 = 0;
        mn1 = 999999;
        for (int grindCounter0 = 0; grindCounter0 < (int) Math.round(cs1); grindCounter0++) {
            System.out.print("Enter course name:");
            cn_text = readText();
            System.out.print("Enter course credit:");
            cr1 = readNumber();
            System.out.print("Enter expected mark:");
            mk1 = readNumber();
            tc1 = (tc1 + cr1);
            ws1 = (ws1 + ((cr1 * mk1)));
            mx1 = Math.max(mx1, mk1);
            mn1 = Math.min(mn1, mk1);
            System.out.println("Course: " + cn_text);
            System.out.println("Weighted points: " + (cr1 * mk1));
            trick_markline();
        }
        if (tc1 > 0) {
            av1 = Math.round((ws1 / tc1));
        } else {
            av1 = 0;
        }
        gp1 = Math.round((((av1 / 100)) * 4));
        bo1 = Math.round((Math.sqrt(Math.abs((100 - av1))) * Math.PI));
        switch ((int) Math.round(st1)) {
            case 1:
                if (av1 >= 85) {
                    sp1 = (fe1 * 0.60);
                } else {
                    if (av1 >= 75) {
                        sp1 = (fe1 * 0.35);
                    } else {
                        sp1 = (fe1 * 0.10);
                    }
                }
                break;
            case 2:
                if (av1 >= 70) {
                    sp1 = (fe1 * 0.50);
                } else {
                    sp1 = (fe1 * 0.25);
                }
                break;
            case 3:
                if (av1 >= 60) {
                    sp1 = (fe1 * 0.40);
                } else {
                    sp1 = (fe1 * 0.15);
                }
                break;
            default:
                sp1 = 0;
                break;
        }
        rm1 = (fe1 - sp1);
        hr1 = Math.round((bo1 + tc1));
        rv1 = hr1;
        if ((av1 >= 85 && tc1 >= 12)) {
            el_text = "Strong scholarship candidate";
        } else {
            if ((av1 >= 70 || gp1 >= 3)) {
                el_text = "Possible scholarship candidate";
            } else {
                el_text = "Needs stronger academic record";
            }
        }
        trick_cover();
        System.out.println("Planner ID: " + id1);
        System.out.println("Student: " + nm_text);
        System.out.println("Total credits: " + tc1);
        System.out.println("Average mark: " + av1);
        System.out.println("Estimated GPA: " + gp1);
        System.out.println("Highest course mark: " + mx1);
        System.out.println("Lowest course mark: " + mn1);
        System.out.println("Scholarship amount: " + sp1);
        System.out.println("Remaining fee: " + rm1);
        System.out.println("Eligibility: " + el_text);
        System.out.println("Base study hours per week: " + hr1);
        System.out.println("Weekly revision plan");
        ii1 = 1;
        {
            double journeyStart1 = 1;
            double journeyEnd1 = wk1;
            if (journeyStart1 <= journeyEnd1) {
                for (double journeyCounter1 = journeyStart1; journeyCounter1 <= journeyEnd1; journeyCounter1++) {
                    rv1 = (rv1 + luck(1, 2));
                    System.out.println("Week " + ii1 + " study hours: " + rv1);
                    ii1 = (ii1 + 1);
                }
            } else {
                for (double journeyCounter1 = journeyStart1; journeyCounter1 >= journeyEnd1; journeyCounter1--) {
                    rv1 = (rv1 + luck(1, 2));
                    System.out.println("Week " + ii1 + " study hours: " + rv1);
                    ii1 = (ii1 + 1);
                }
            }
        }
        for (int grindCounter2 = 0; grindCounter2 < (int) Math.round(1); grindCounter2++) {
            System.out.println("Reminder: attend class and complete practice tasks");
        }
        if ((!((rm1 > fe1)) && sp1 > 0)) {
            System.out.println("Finance status: scholarship applied");
        } else {
            System.out.println("Finance status: full payment needed");
        }
        System.out.println("===== PLANNER COMPLETE =====");
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
