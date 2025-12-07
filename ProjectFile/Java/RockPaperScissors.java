package Java;
import java.util.Scanner;
import java.util.Random;
public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] moves = {"rock", "paper", "scissors"};
        String computer = moves[new Random().nextInt(3)];

        System.out.print("Enter rock, paper, or scissors: ");
        String player = sc.next().toLowerCase();

        if (player.equals(computer)) System.out.println("Draw!");
        else if ((player.equals("rock") && computer.equals("scissors")) ||
                 (player.equals("paper") && computer.equals("rock")) ||
                 (player.equals("scissors") && computer.equals("paper")))
            System.out.println("You win!");
        else 
            System.out.println("You lose! Computer chose " + computer);
        sc.close();
    }
}
