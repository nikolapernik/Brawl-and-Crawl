import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        System.out.println("Welcome to Brawl and Crawl!");
        final String[] userResponse = {""};
        while (!userResponse[0].equalsIgnoreCase("quit")) {
            userResponse[0] = "";
            Character player1 = chooseCharacter(read, "Player 1");
            Character player2 = chooseCharacter(read, "Player 2");

            //determine who starts
            int turnChance = (int) (Math.random() * 100);
            Character current = (turnChance > 50) ? player1 : player2; //interesting way to shorten ifs I came across
            Character other = (current == player1) ? player2 : player1;
            JFrame loadingScreen = new JFrame("Loading Screen");
            loadingScreen.setSize(550, 300);
            JPanel loadingScreenPanel = new JPanel(new GridLayout(4, 2));
            loadingScreen.setContentPane(loadingScreenPanel);
            loadingScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField firstCharP1 = new JTextField(current.getCharacter() + " starts first!");
            firstCharP1.setHorizontalAlignment(JTextField.CENTER);
            firstCharP1.setEditable(false);
            firstCharP1.setBackground(Color.BLACK);
            firstCharP1.setForeground(Color.RED);
            firstCharP1.setFont(new Font("Raleway", Font.BOLD, 27));
            loadingScreenPanel.add(firstCharP1);

            JTextField statsCurr = new JTextField(current.getCharacter() + "'s stats: " + " | " + current.getHealth() + " | " + current.getDamage() + " | " + current.getArmor() + " | " + current.getPotCount());
            statsCurr.setHorizontalAlignment(JTextField.CENTER);
            statsCurr.setEditable(false);
            statsCurr.setBackground(Color.BLACK);
            statsCurr.setForeground(Color.RED);
            statsCurr.setFont(new Font("Raleway", Font.BOLD, 27));
            loadingScreenPanel.add(statsCurr);
            JTextField statsOth = new JTextField(other.getCharacter() + "'s stats: " + " | " + other.getHealth() + " | " + other.getDamage() + " | " + other.getArmor() + " | " + other.getPotCount());
            statsOth.setHorizontalAlignment(JTextField.CENTER);
            statsOth.setEditable(false);
            statsOth.setBackground(Color.BLACK);
            statsOth.setForeground(Color.RED);
            statsOth.setFont(new Font("Raleway", Font.BOLD, 27));
            loadingScreenPanel.add(statsOth);
            JButton startButton = new JButton("Load Battle");
            startButton.setBackground(Color.BLACK);
            startButton.setForeground(Color.RED);
            startButton.setFont(new Font("Raleway", Font.BOLD, 30));

            loadingScreenPanel.add(startButton);
            loadingScreen.setVisible(true);
            startButton.addActionListener(new ActionListener() {
                                              @Override
                                              public void actionPerformed(ActionEvent e) {
                                                  try {
                                                      Thread.sleep(2000);
                                                  } catch (InterruptedException ee) {
                                                      Thread.currentThread().interrupt();
                                                      return;
                                                  }
                                                  loadingScreen.setVisible(false);
                                                  loadingScreen.dispose();
                                                  return;
                                              }
                                          }
            );
            while (loadingScreen.isVisible()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ee) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            //battle loop
            JFrame battle = new JFrame("Battle");
            battle.setSize(550, 500);
            JPanel battlePanel = new JPanel(new GridLayout(5, 2));
            battle.setContentPane(battlePanel);
            battle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            battlePanel.setBackground(Color.BLACK);
            //i used AI to implement an image as requested in the rubric document
            //it helped me learn about ImageIcon, as well as the importance of accurately naming your paths to the file
            //i used it to have a banner i drew by hand during the battles :)
            ImageIcon icon = new ImageIcon("src/Banner_New.png");
            JLabel banner = new JLabel(icon);
            battlePanel.add(banner);
            JTextField currHP = new JTextField();
            currHP.setBackground(Color.BLACK);
            currHP.setForeground(Color.RED);
            currHP.setFont(new Font("Raleway", Font.BOLD, 27));
            currHP.setHorizontalAlignment(JTextField.CENTER);
            battlePanel.add(currHP);
            JTextField othHP = new JTextField();
            othHP.setBackground(Color.BLACK);
            othHP.setForeground(Color.RED);
            othHP.setFont(new Font("Raleway", Font.BOLD, 27));
            othHP.setHorizontalAlignment(JTextField.CENTER);
            battlePanel.add(othHP);
            JTextField status = new JTextField();
            status.setEditable(false);
            status.setBackground(Color.BLACK);
            status.setForeground(Color.RED);
            status.setFont(new Font("Raleway", Font.BOLD, 20));
            status.setHorizontalAlignment(JTextField.CENTER);
            battlePanel.add(status);
            battlePanel.add(new LogoPanel()); //i used AI for this to work properly
            battle.setVisible(true);
            while (player1.getHealth() > 0 && player2.getHealth() > 0) {
                currHP.setText(current.getCharacter() + "'s current HP: " + current.getHealth());
                currHP.repaint();
                othHP.setText(other.getCharacter() + "'s current HP: " + other.getHealth());
                othHP.repaint();


                System.out.println("-----------------------------------");
                System.out.println("Turn Started!");
                current.attack(other);
                status.setText(current.getCharacter() + " has attacked " + other.getCharacter());
                status.repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                //death check
                if (other.getHealth() <= 0) {
                    status.setText(other.getCharacter() + " has died. ");
                    status.repaint();
                    othHP.setText(other.getCharacter() + "'s current HP: 0");
                    othHP.repaint();
                    System.out.println(other.getCharacter() + " has died.");
                    break;
                }

                other.attack(current);
                status.setText(other.getCharacter() + " has attacked " + current.getCharacter());
                status.repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                //death check 2
                if (current.getHealth() <= 0) {
                    status.setText(current.getCharacter() + " has died.");
                    status.repaint();
                    currHP.setText(current.getCharacter() + "'s current HP: 0");
                    currHP.repaint();
                    System.out.println(current.getCharacter() + " has died.");
                    break;
                }

                //potChance
                if ((int) (Math.random() * 100) >= 50) {
                    status.setText(current.getCharacter() + " has consumed a pot and healed 15HP.");
                    status.repaint();

                    current.consumePot();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                if ((int) (Math.random() * 100) >= 50) {
                    status.setText(other.getCharacter() + " has consumed a pot and healed 15HP.");
                    status.repaint();
                    other.consumePot();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            battle.setVisible(false);
            battle.dispose();
            // Determine winner(victory screen)

            if (player1.getHealth() > 0) {
                JFrame winScreen = new JFrame("Winscreen");
                JPanel winScreenPanel = new JPanel(new GridLayout(2, 2));
                winScreen.setSize(450, 300);
                winScreen.setContentPane(winScreenPanel);
                winScreenPanel.setBackground(Color.BLACK);
                winScreenPanel.setForeground(Color.RED);
                JTextField winner = new JTextField(player1.getCharacter() + " has WON!");
                winner.setHorizontalAlignment(JTextField.CENTER);
                winner.setFont(new Font("Raleway", Font.BOLD, 27));
                winner.setBackground(Color.BLACK);
                winner.setForeground(Color.RED);
                winScreenPanel.add(winner);
                JButton retryButton = new JButton("Play Again");
                retryButton.setFont(new Font("Raleway", Font.BOLD, 27));
                retryButton.setBackground(Color.BLACK);
                retryButton.setForeground(Color.RED);
                winScreenPanel.add(retryButton);
                winScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                winScreen.setVisible(true);
                retryButton.addActionListener(new ActionListener() {
                                                  @Override
                                                  public void actionPerformed(ActionEvent e) {
                                                      userResponse[0] = "r";
                                                      winScreen.setVisible(false);
                                                      winScreen.dispose();
                                                      return;
                                                  }
                                              }
                );
                while (userResponse[0].isEmpty()) {
                    try {
                        Thread.sleep(100); // Short delay to avoid busy waiting
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                System.out.println("-----------------------------------");
                System.out.println(player1.getCharacter() + " has won!");
                System.out.println("-----------------------------------");
            } else {
                JFrame winScreen = new JFrame("Winscreen");
                JPanel winScreenPanel = new JPanel(new GridLayout(2, 2));
                winScreen.setSize(450, 300);
                winScreen.setContentPane(winScreenPanel);
                winScreenPanel.setBackground(Color.BLACK);
                winScreenPanel.setForeground(Color.RED);
                JTextField winner = new JTextField(player2.getCharacter() + " has WON!");
                winner.setHorizontalAlignment(JTextField.CENTER);
                winner.setFont(new Font("Raleway", Font.BOLD, 27));
                winner.setBackground(Color.BLACK);
                winner.setForeground(Color.RED);
                winScreenPanel.add(winner);
                JButton retryButton = new JButton("Play Again");
                retryButton.setFont(new Font("Raleway", Font.BOLD, 27));
                retryButton.setBackground(Color.BLACK);
                retryButton.setForeground(Color.RED);
                winScreenPanel.add(retryButton);
                winScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                winScreen.setVisible(true);

                retryButton.addActionListener(new ActionListener() {
                                                  @Override
                                                  public void actionPerformed(ActionEvent e) {

                                                      userResponse[0] = "r";
                                                      winScreen.setVisible(false);
                                                      winScreen.dispose();
                                                      return;
                                                  }
                                              }
                );
                while (userResponse[0].isEmpty()) {
                    try {
                        Thread.sleep(100); // Short delay to avoid busy waiting
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("-----------------------------------");
                System.out.println(player2.getCharacter() + " has won!");
                System.out.println("-----------------------------------");
            }
            System.out.println("Press enter to restart, type quit to quit");


        }
        System.out.println("ThanK for playing! See you next time.");
    }

    // Helper method to choose character
    public static Character chooseCharacter(Scanner read, String playerLabel) {
        final String[] choice = {""};
        JFrame frame = new JFrame("Character Selection");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        frame.setSize(480, 640);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField titleP1 = new JTextField("Choose your");
        titleP1.setEditable(false);
        titleP1.setFont(new Font("Raleway", Font.BOLD, 35));
        titleP1.setBackground(Color.BLACK);
        titleP1.setForeground(Color.RED);
        JTextField titleP2 = new JTextField("character!");
        titleP2.setBackground(Color.BLACK);
        titleP2.setForeground(Color.RED);
        titleP2.setEditable(false);
        titleP2.setFont(new Font("Raleway", Font.BOLD, 35));
        JButton wizard = new JButton("Wizard");
        wizard.setFont(new Font("Raleway", Font.BOLD, 40));
        wizard.setBackground(Color.BLACK);
        wizard.setForeground(Color.RED);
        JButton warrior = new JButton("Warrior");
        warrior.setFont(new Font("Raleway", Font.BOLD, 40));
        warrior.setBackground(Color.BLACK);
        warrior.setForeground(Color.RED);
        JButton berzerker = new JButton("Berzerker");
        berzerker.setFont(new Font("Raleway", Font.BOLD, 40));
        berzerker.setBackground(Color.BLACK);
        berzerker.setForeground(Color.RED);
        JButton joker = new JButton("Joker");
        joker.setFont(new Font("Raleway", Font.BOLD, 40));
        joker.setBackground(Color.BLACK);
        joker.setForeground(Color.RED);


        panel.add(titleP1);
        panel.add(titleP2);
        panel.add(wizard);
        panel.add(warrior);
        panel.add(berzerker);
        panel.add(joker);
        frame.setVisible(true);
        wizard.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         choice[0] = "wizard";
                                         System.out.println("Wizard chosen");
                                         frame.setVisible(false);
                                         frame.dispose();
                                         return;
                                     }
                                 }
        );
        warrior.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                          choice[0] = "warrior";
                                          System.out.println("Warrior chosen");
                                          frame.setVisible(false);
                                          frame.dispose();
                                          return;
                                      }
                                  }
        );
        berzerker.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            choice[0] = "berzerker";
                                            System.out.println("Berzerker chosen");
                                            frame.setVisible(false);
                                            frame.dispose();
                                            return;
                                        }
                                    }
        );
        joker.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        choice[0] = "joker";
                                        System.out.println("Joker chosen");
                                        frame.setVisible(false);
                                        frame.dispose();
                                        return;
                                    }
                                }
        );
        while (choice[0].isEmpty()) {
            try {
                Thread.sleep(100); // Short delay to avoid busy waiting
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        while (!choice[0].equalsIgnoreCase("Warrior") &&
                !choice[0].equalsIgnoreCase("Wizard") &&
                !choice[0].equalsIgnoreCase("Berzerker") &&
                !choice[0].equalsIgnoreCase("Joker")) {
            System.out.println(playerLabel + ", choose your character: Warrior, Wizard, Berzerker, Joker.");
            choice[0] = read.nextLine();
            if (!choice[0].equalsIgnoreCase("Warrior") &&
                    !choice[0].equalsIgnoreCase("Wizard") &&
                    !choice[0].equalsIgnoreCase("Berzerker") &&
                    !choice[0].equalsIgnoreCase("Joker")) {
                System.out.println("Invalid choice! Please try again.");
            }
        }

        System.out.println("You chose: " + choice[0]);

        return switch (choice[0].toLowerCase()) {
            case "wizard" -> new Wizard();
            case "warrior" -> new Warrior();
            case "berzerker" -> new Berzerker();
            case "joker" -> new Joker();
            default -> null; // should never happen
        };


    }


    }
class LogoPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        //background
        g.fillRect(0,0,550, 100);
        //shields
        g.setColor(Color.RED);
        g.drawOval(100, 30, 40, 40);
        g.fillRect(119, 30, 3, 40);
        g.fillRect(100, 50, 40, 3);
        g.drawOval(400, 30, 40, 40);
        g.fillRect(419, 30, 3, 40);
        g.fillRect(400, 50, 40, 3);

        //swords
        g.fillRect(230, 30, 5, 40);
        g.fillRect(219, 55, 25, 5);
        g.fillRect(300, 30, 5, 40);
        g.fillRect(293, 55, 25, 5);

    }
}