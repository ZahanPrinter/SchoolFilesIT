package Java;
import java.util.Scanner;

public class LibrarySystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] books = new String[10];
        boolean[] issued = new boolean[10];
        int count = 0;
        while (true) {
            System.out.println("\n===== LIBRARY MENU =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    if (count < books.length) {
                        System.out.print("Enter book title: ");
                        books[count] = sc.nextLine();
                        issued[count] = false;
                        count++;
                        System.out.println("Book added successfully.");
                    } else {
                        System.out.println("Library is full!");
                    }
                    break;

                case 2:
                    if (count == 0) System.out.println("No books available.");
                    else {
                        System.out.println("\nBooks in library:");
                        for (int i = 0; i < count; i++) {
                            System.out.println((i + 1) + ". " + books[i] +
                                    (issued[i] ? " (Issued)" : " (Available)"));
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter book number to issue: ");
                    int issueNum = sc.nextInt();
                    if (issueNum > 0 && issueNum <= count) {
                        if (!issued[issueNum - 1]) {
                            issued[issueNum - 1] = true;
                            System.out.println("Book issued successfully.");
                        } else System.out.println("Book already issued!");
                    } else System.out.println("Invalid number.");
                    break;

                case 4:
                    System.out.print("Enter book number to return: ");
                    int returnNum = sc.nextInt();
                    if (returnNum > 0 && returnNum <= count) {
                        if (issued[returnNum - 1]) {
                            issued[returnNum - 1] = false;
                            System.out.println("Book returned successfully.");
                        } else System.out.println("That book wasn't issued.");
                    } else System.out.println("Invalid number.");
                    break;

                case 5:
                    System.out.println("Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
