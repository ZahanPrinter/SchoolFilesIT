package Java;
import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double total = 0;
        System.out.print("Enter number of subjects: ");
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            System.out.print("Enter marks for subject " + i + ": ");
            total += sc.nextDouble();
        }
        double avg = total / n;
        System.out.println("Average: " + avg);
        if (avg >= 80) System.out.println("Grade: A");
        else if (avg >= 70) System.out.println("Grade: B");
        else if (avg >= 60) System.out.println("Grade: C");
        else if (avg >= 50) System.out.println("Grade: D");
        else System.out.println("Grade: F");
        sc.close();
    }
}
