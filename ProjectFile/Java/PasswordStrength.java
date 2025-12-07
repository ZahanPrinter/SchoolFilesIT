package Java;
import java.util.Scanner;

public class PasswordStrength {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSymbol = false;
        for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSymbol = true;
        }
        if (pass.length() >= 8 && hasUpper && hasLower && hasDigit && hasSymbol)
            System.out.println("Strong password.");
        else
            System.out.println("Weak password.");
        sc.close();
    }
}
