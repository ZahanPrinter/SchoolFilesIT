package Java;
import java.util.Scanner;

public class EvenOdd {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt();
        System.out.println(n + " is " + (n % 2 == 0 ? "Even" : "Odd"));
        sc.close();
    }
}
