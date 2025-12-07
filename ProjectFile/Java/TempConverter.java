package Java;
import java.util.Scanner;

public class TempConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Celsius temperature: ");
        double c = sc.nextDouble();
        double f = (c * 9 / 5) + 32;
        System.out.println("Fahrenheit: " + f);
        sc.close();
    }
}
