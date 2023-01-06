/*
 * Title: Blackjack
 * Date Started: 1/5/23
 */

import javax.swing.*;

import javafx.geometry.Insets;

import java.awt.event.*;
import java.awt.*;

import java.util.ArrayList;

class Main{
    // TODO: make multiplayer
    // BACKEND
    private static final int MAXPlAYERCOUNT = 7;

    private static Player user = new Player(); // for testing, will create multiple users later
    
    // TODO: design and create front end
    // FRONTEND
    //GridBagLayout is confusing, but it's fine
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    JFrame frame;
    

    //Start menu components
    JPanel titleScreen;
    JLabel titleLabel;
    JButton startButton;
    //JImageIcon titleImage; //potential for an image on the title screen, idk

    //Main game components
    JPanel gameScreen;

    ArrayList<Image> cardImages;
    ArrayList<ArrayList<Image>> allCardImages; //it looks really dumb, but it works in theory, so lets go with it

    JPanel allSeats;
    JPanel[] seats; //each seat will will be a panel that display the player with the same index's stuff
    JPanel dealerSeat; //the dealer's seat will be seperate

    JPanel playerSeats; //will display the player's panels

    JPanel infoPanel; //will display who's turn it is and the buttons to play the game
    JLabel infoLabel; //will display who's turn it is
    JPanel buttonsPanel; //will hold the buttons

    JButton hitButton;
    JButton standButton;
    JButton splitButton;
    JButton doubleButton;

    public Main(){
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildTitleScreen();

        frame.setContentPane(titleScreen);

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

                case "hit":
                    //TODO: hit code
                    break;

                case "stand":
                    //TODO: stand code
                    break;

                case "split":
                    //TODO: split code
                    break;
                
                case "double":
                    //TODO: double down code
            }
        }
    }

    private static void runGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        new Main();
    }
  
    public static void main(String[] args) {
        System.out.println(user.getChipsAmount());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
            }
        });
    }

    //SECTION screen build methods
    private void buildTitleScreen(){
        titleScreen = new JPanel();
        //titleScreen.setLayout(null);
        titleScreen.setLayout(layout);

        titleLabel = new JLabel("Blackjack");
        //titleLabel.setBounds(100, 30, 120, 50);

        startButton = new JButton("GO!");

        gbc.insets = new java.awt.Insets(100, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        layout.setConstraints(titleLabel, gbc);
        titleScreen.add(titleLabel);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        layout.setConstraints(startButton, gbc);
        startButton.addActionListener(new Click());
        startButton.setActionCommand("start");
        titleScreen.add(startButton);
    }

    private void buildTable(){
        gameScreen = new JPanel();
        gameScreen.setLayout(new BoxLayout(gameScreen, BoxLayout.PAGE_AXIS));

        allSeats = new JPanel();
        allSeats.setLayout(new GridLayout(3, 3, 5, 5));
        //allSeats.setBounds(1, 1, 1, 1);

        seats = new JPanel[MAXPlAYERCOUNT];
        for(JPanel seat : seats){
            seat.setLayout(new FlowLayout()); //TODO: reminder: change the layout to work better
        }

        dealerSeat = new JPanel();

        infoPanel = new JPanel();
        infoLabel = new JLabel();

        buttonsPanel = new JPanel();

    }
}
