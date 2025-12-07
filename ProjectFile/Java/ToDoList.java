package Java;
import java.util.Scanner;

public class ToDoList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] tasks = new String[10];
        int count = 0;

        while (true) {
            System.out.println("\n1. Add Task\n2. View Tasks\n3. Exit");
            System.out.print("Choose: ");
            int ch = sc.nextInt();
            sc.nextLine();
            System.out.println("");
            if (ch == 1) {
                if (count < 10) {
                    System.out.print("Enter task: ");
                    tasks[count++] = sc.nextLine();
                } else System.out.println("List full!");
            } else if (ch == 2) {
                for (int i = 0; i < count; i++)
                    System.out.println((i + 1) + ". " + tasks[i]);
            } else if (ch == 3) break;
        }
        sc.close();
    }
}
