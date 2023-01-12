/*
 * Title: Blackjack
 * Date Started: 1/5/23
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import java.util.ArrayList;

class Main{
    // BACKEND
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static final int defaultChips = 100;
    // TODO: design and create front end
    // FRONTEND
    // GridBagLayout is confusing, but it's fine
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    JFrame frame;
    JPanel pane;

    //Start menu components
    JLabel titleLabel;
    JButton startButton;
    //JImageIcon titleImage; //potential for an image on the title screen, idk

    //Main game components
    ArrayList<Image> cardImages;
    ArrayList<ArrayList<Image>> allCardImages; //it looks really dumb, but it works in theory, so lets go with it

    public Main(){
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pane = new JPanel();
        pane.setLayout(layout);

        titleLabel = new JLabel("Blackjack");
        startButton = new JButton("GO!");

        gbc.gridx = 0;
        gbc.gridy = 0;
        layout.setConstraints(titleLabel, gbc);
        pane.add(titleLabel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        layout.setConstraints(startButton, gbc);
        startButton.addActionListener(new Click());
        startButton.setActionCommand("start");
        pane.add(startButton);

        frame.setContentPane(pane);

        frame.setSize(640, 480); //idk, thought 480p was a good resolution
        frame.setVisible(true);
    }

    //event handler for button clicks
    class Click implements ActionListener{
        public void actionPerformed(ActionEvent event){
            switch(event.getActionCommand()){
                case "start":
                    startButton.setVisible(false);
                    titleLabel.setVisible(false);
                    break;
            }
        }
    }

    private static void runGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        new Main();
    }
  
    public static void main(String[] args) {
        int playerNum = 2;
        for (int i = 0; i < playerNum; i++) {
            players.add(new Player(defaultChips, "Player " + (i + 1)));

        }
        for(Player p : players) {
        System.out.println(p.getName());
        System.out.println(p.getChipsAmount());
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
            }
        });
    }
}
