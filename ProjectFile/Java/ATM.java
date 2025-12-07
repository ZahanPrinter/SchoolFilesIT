package Java;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        double balance = 1000;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance\n2. Deposit\n3. Withdraw\n4. Exit");
            System.out.print("Choose: ");
            int ch = sc.nextInt();

            if (ch == 1) System.out.println("Balance: $" + balance);
            else if (ch == 2) {
                System.out.print("Amount to deposit: ");
                balance += sc.nextDouble();
            } else if (ch == 3) {
                System.out.print("Amount to withdraw: ");
                double amt = sc.nextDouble();
                if (amt <= balance) balance -= amt;
                else System.out.println("Insufficient funds.");
            } else if (ch == 4) break;
            else System.out.println("Invalid option.");
        }
        sc.close();
    }
}
