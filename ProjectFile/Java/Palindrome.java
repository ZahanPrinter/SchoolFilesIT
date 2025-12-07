package Java;
import java.util.Scanner;

public class Palindrome {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String word = sc.nextLine();
        String rev = new StringBuilder(word).reverse().toString();

        if (word.equalsIgnoreCase(rev))
            System.out.println(word + " is a palindrome.");
        else
            System.out.println(word + " is not a palindrome.");
        sc.close();
    }
}
