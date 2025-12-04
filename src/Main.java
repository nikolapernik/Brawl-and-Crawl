import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        System.out.println("Welcome to Brawl and Crawl!");
        String userResponse = "";
        while(!userResponse.equalsIgnoreCase("quit"))
        {
            Character player1 = chooseCharacter(read, "Player 1");
            Character player2 = chooseCharacter(read, "Player 2");

            //determine who starts
            int turnChance = (int) (Math.random() * 100);
            Character current = (turnChance > 50) ? player1 : player2; //interesting way to shorten ifs I came across
            Character other = (current == player1) ? player2 : player1;
            System.out.println(current.getName() + " starts first!");
            System.out.println(current.getName() + "'s stats: " + current.getName() + " | " + current.getHealth() + " | " + current.getDamage() + " | " + current.getArmor() + " | " + current.getPotCount());
            System.out.println(other.getName() + "'s stats: " + other.getName() + " | " + other.getHealth() + " | " + other.getDamage() + " | " + other.getArmor() + " | " + other.getPotCount());
            //battle loop
            while (player1.getHealth() > 0 && player2.getHealth() > 0) {
                System.out.println("-----------------------------------");
                System.out.println("Turn Started!");
                current.attack(other);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                //death check
                if (other.getHealth() <= 0) {
                    System.out.println(other.getName() + " has died.");
                    break;
                }

                other.attack(current);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                //death check 2
                if (current.getHealth() <= 0) {
                    System.out.println(current.getName() + " has died.");
                    break;
                }

                //potChance
                if ((int) (Math.random() * 100) >= 50) current.consumePot();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                if ((int) (Math.random() * 100) >= 50) other.consumePot();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            // Determine winner
            if (player1.getHealth() > 0) {
                System.out.println("-----------------------------------");
                System.out.println(player1.getName() + " has won!");
                System.out.println("-----------------------------------");
            } else {
                System.out.println("-----------------------------------");
                System.out.println(player2.getName() + " has won!");
                System.out.println("-----------------------------------");
            }
            System.out.println("Press enter to restart, type quit to quit");
            userResponse = read.nextLine();
        }
        System.out.println("ThanK for playing! See you next time.");
    }

    // Helper method to choose character
    public static Character chooseCharacter(Scanner read, String playerLabel) {
        String choice = "";
        while (!choice.equalsIgnoreCase("Warrior") &&
                !choice.equalsIgnoreCase("Wizard") &&
                !choice.equalsIgnoreCase("Berzerker") &&
                !choice.equalsIgnoreCase("Joker")) {
            System.out.println(playerLabel + ", choose your character: Warrior, Wizard, Berzerker, Joker.");
            choice = read.nextLine();
            if (!choice.equalsIgnoreCase("Warrior") &&
                    !choice.equalsIgnoreCase("Wizard") &&
                    !choice.equalsIgnoreCase("Berzerker") &&
                    !choice.equalsIgnoreCase("Joker")) {
                System.out.println("Invalid choice! Please try again.");
            }
        }

        System.out.println("You chose: " + choice);
        System.out.println("Name your fighter:");
        String name = read.nextLine();

        return switch (choice.toLowerCase()) {
            case "wizard" -> new Wizard(name);
            case "warrior" -> new Warrior(name);
            case "berzerker" -> new Berzerker(name);
            case "joker" -> new Joker(name);
            default -> null; // should never happen
        };
    }
}
