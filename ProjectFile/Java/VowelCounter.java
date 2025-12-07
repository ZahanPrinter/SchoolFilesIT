package Java;
import java.util.Scanner;

public class VowelCounter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a sentence: ");
        String text = sc.nextLine().toLowerCase();
        int count = 0;

        for (char c : text.toCharArray())
            if ("aeiou".indexOf(c) != -1) count++;

        System.out.println("Vowels: " + count);
        sc.close();
    }
}
