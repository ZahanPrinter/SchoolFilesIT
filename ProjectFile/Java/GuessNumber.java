package Java;
import java.util.*;

public class GuessNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int number = (int) (Math.random() * 100 + 1);
        int guess = 0, tries = 0;
        while (guess != number) {
            System.out.print("Guess a number (1-100): ");
            guess = sc.nextInt();
            tries++;
            if (guess < number) System.out.println("Too low!");
            else if (guess > number) System.out.println("Too high!");
            else System.out.println("Correct! It took you " + tries + " tries.");
        }
        sc.close();
    }
}
