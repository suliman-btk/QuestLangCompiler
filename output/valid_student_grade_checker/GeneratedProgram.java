import java.util.*;

public class GeneratedProgram {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();

    private static double ns1;
    private static double sc1;
    private static double cr1;
    private static double qp1;
    private static double tw1;
    private static double tc1;
    private static double gp1;
    private static double rg1;
    private static double tt1;
    private static double av1;
    private static double mx1;
    private static double mn1;
    private static double ps1;
    private static String nm_text = "";
    private static String gd_text = "";
    private static String st_text = "";

    private static void trick_banner() {
        System.out.println("======== STUDENT GRADE QUEST ========");
        drawStar(30);
    }

    public static void main(String[] args) {
        trick_banner();
        System.out.print("Enter student name:");
        nm_text = readText();
        System.out.print("How many subjects:");
        ns1 = readNumber();
        tt1 = 0;
        tw1 = 0;
        tc1 = 0;
        mx1 = 0;
        mn1 = 100;
        ps1 = 0;
        for (int grindCounter0 = 0; grindCounter0 < (int) Math.round(ns1); grindCounter0++) {
            System.out.print("Enter subject score:");
            sc1 = readNumber();
            System.out.print("Enter subject credit hours:");
            cr1 = readNumber();
            switch ((int) Math.round(sc1)) {
                case 79:
                    gd_text = "A-";
                    qp1 = 3.93;
                    break;
                case 78:
                    gd_text = "A-";
                    qp1 = 3.87;
                    break;
                case 77:
                    gd_text = "A-";
                    qp1 = 3.8;
                    break;
                case 76:
                    gd_text = "A-";
                    qp1 = 3.73;
                    break;
                case 75:
                    gd_text = "A-";
                    qp1 = 3.67;
                    break;
                case 74:
                    gd_text = "B+";
                    qp1 = 3.6;
                    break;
                case 73:
                    gd_text = "B+";
                    qp1 = 3.53;
                    break;
                case 72:
                    gd_text = "B+";
                    qp1 = 3.47;
                    break;
                case 71:
                    gd_text = "B+";
                    qp1 = 3.4;
                    break;
                case 70:
                    gd_text = "B+";
                    qp1 = 3.33;
                    break;
                case 69:
                    gd_text = "B";
                    qp1 = 3.27;
                    break;
                case 68:
                    gd_text = "B";
                    qp1 = 3.2;
                    break;
                case 67:
                    gd_text = "B";
                    qp1 = 3.13;
                    break;
                case 66:
                    gd_text = "B";
                    qp1 = 3.07;
                    break;
                case 65:
                    gd_text = "B";
                    qp1 = 3.0;
                    break;
                case 64:
                    gd_text = "B-";
                    qp1 = 2.93;
                    break;
                case 63:
                    gd_text = "B-";
                    qp1 = 2.87;
                    break;
                case 62:
                    gd_text = "B-";
                    qp1 = 2.8;
                    break;
                case 61:
                    gd_text = "B-";
                    qp1 = 2.73;
                    break;
                case 60:
                    gd_text = "B-";
                    qp1 = 2.67;
                    break;
                case 59:
                    gd_text = "C+";
                    qp1 = 2.59;
                    break;
                case 58:
                    gd_text = "C+";
                    qp1 = 2.53;
                    break;
                case 57:
                    gd_text = "C+";
                    qp1 = 2.46;
                    break;
                case 56:
                    gd_text = "C+";
                    qp1 = 2.4;
                    break;
                case 55:
                    gd_text = "C+";
                    qp1 = 2.33;
                    break;
                case 54:
                    gd_text = "C";
                    qp1 = 2.26;
                    break;
                case 53:
                    gd_text = "C";
                    qp1 = 2.2;
                    break;
                case 52:
                    gd_text = "C";
                    qp1 = 2.13;
                    break;
                case 51:
                    gd_text = "C";
                    qp1 = 2.07;
                    break;
                case 50:
                    gd_text = "C";
                    qp1 = 2.0;
                    break;
                default:
                    if (sc1 >= 90) {
                        gd_text = "A+";
                        qp1 = 4.00;
                    } else {
                        if (sc1 >= 80) {
                            gd_text = "A";
                            qp1 = 4.00;
                        } else {
                            if (sc1 >= 47) {
                                gd_text = "C-";
                                qp1 = 1.67;
                            } else {
                                if (sc1 >= 44) {
                                    gd_text = "D+";
                                    qp1 = 1.33;
                                } else {
                                    if (sc1 >= 40) {
                                        gd_text = "D";
                                        qp1 = 1.00;
                                    } else {
                                        gd_text = "F";
                                        qp1 = 0;
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
            tt1 = (tt1 + sc1);
            mx1 = Math.max(mx1, sc1);
            mn1 = Math.min(mn1, sc1);
            tw1 = (tw1 + (qp1 * cr1));
            tc1 = (tc1 + cr1);
            if (qp1 > 0) {
                ps1 = (ps1 + 1);
            } else {
                System.out.println("Subject failed (F) with score " + sc1);
            }
            System.out.println("Score " + sc1 + " (" + cr1 + " cr) -> grade " + gd_text + ", QP " + qp1);
        }
        if (ns1 > 0) {
            av1 = Math.round((tt1 / ns1));
        } else {
            av1 = 0;
        }
        if (tc1 > 0) {
            gp1 = (tw1 / tc1);
        } else {
            gp1 = 0;
        }
        rg1 = (Math.round((gp1 * 100)) / 100.0);
        if (gp1 >= 3.67) {
            st_text = "Dean's List";
        } else {
            if (gp1 >= 2.0) {
                st_text = "Good standing";
            } else {
                st_text = "Academic probation";
            }
        }
        trick_banner();
        System.out.println("Student: " + nm_text);
        System.out.println("Total credit hours: " + tc1);
        System.out.println("Average score: " + av1);
        System.out.println("Highest: " + mx1 + "  Lowest: " + mn1);
        System.out.println("Subjects passed: " + ps1 + " of " + ns1);
        System.out.println("GPA (credit-weighted): " + rg1);
        System.out.println("Standing: " + st_text);
        drawBox(3);
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
