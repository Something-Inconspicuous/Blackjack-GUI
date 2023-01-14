/*
 * Title: Blackjack
 * Date Started: 1/5/23
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import java.util.ArrayList;

class Main{
    // TODO: make multiplayer
    // BACKEND
    private static final int MAXPlAYERCOUNT = 8;

    private static ArrayList<Player> players = new ArrayList<Player>();
    private static final int defaultChips = 100;

    // TODO: design and create front end
    // FRONTEND
    //GridBagLayout is confusing, but it's fine
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    JFrame frame;
    JPanel contentPane;
    

    //Start menu components
    JPanel titleScreen;
    JLabel titleLabel;
    JButton startButton;
    //JLabel titleImage; //potential for an image on the title screen, idk

    //Main game components
    JPanel gameScreen;

    //ArrayList<Image> cardImages;
    //ArrayList<ArrayList<Image>> allCardImages; //it looks really dumb, but it works in theory, so lets go with it

    JPanel allSeats;
    //JPanel[] seats; //each seat will will be a panel that display the player with the same index's stuff
    //JPanel dealerSeat; //the dealer's seat will be seperate

    //JPanel playerSeats; //will display the player's panels

    JPanel bottomPanel; //will display all of the info stuff
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

        contentPane = new JPanel();

        buildTitleScreen();
        buildTable();

        contentPane.add(titleScreen);
        contentPane.add(gameScreen);
        gameScreen.setVisible(false);
        frame.setContentPane(contentPane);

        frame.setSize(1280, 720); //increased from 480p to 720p
        frame.setVisible(true);
    }

    //event handler for button clicks
    class Click implements ActionListener{
        public void actionPerformed(ActionEvent event){
            //System.out.println(event.getActionCommand());
            switch(event.getActionCommand()){
                case "start":
                    for(Player p : players) {
                        //p.getSeat().addCard(new Card(4, 2));
                        //p.getSeat().addCard(new Card(4, 2));
                        //p.getSeat().getDisplay();
                        allSeats.add(p.getSeat().getDisplay());
                      
                    }
                    titleScreen.setVisible(false);
                    gameScreen.setVisible(true);
                    
                    break;

                case "hit":
                    //TODO: hit code
                    players.get(0).getSeat().addCard(new Card(4, 2)); //testing



                    gameScreen.revalidate();
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
        for (int i = 0; i < MAXPlAYERCOUNT; i++) {
            players.add(new Player(defaultChips, "Player " + (i + 1)));

        }
        


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

        gbc.insets = new java.awt.Insets(0, 0, 50, 0);
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

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));

        allSeats = new JPanel();
        allSeats.setLayout(new GridLayout(3, 3, 5, 5));
        //allSeats.setBounds(1, 1, 1, 1);

        //seats = new JPanel[MAXPlAYERCOUNT];
        //for(int i = 0; i < MAXPlAYERCOUNT; i++){
            //seats[i] = new JPanel(); 
            //seat.setLayout(null); //TODO: reminder: change the layout to work better
        //}

        //dealerSeat = new JPanel();

        infoPanel = new JPanel();
        infoLabel = new JLabel(players.get(0).getName() + " with " + players.get(0).getBetAmount()); //change to hand value

        buttonsPanel = new JPanel();

        hitButton = new JButton("Hit");
        hitButton.setActionCommand("hit");
        hitButton.addActionListener(new Click());
        buttonsPanel.add(hitButton);

        standButton = new JButton("Stand");
        buttonsPanel.add(standButton);

        splitButton = new JButton("Split");
        buttonsPanel.add(splitButton);
        
        doubleButton = new JButton("Double Down");
        buttonsPanel.add(doubleButton);
        
        infoPanel.add(infoLabel);

        //allSeats.add(seats[0]);
        //allSeats.add(dealerSeat);
        //for(int i = 1; i < seats.length; i++){
            //allSeats.add(seats[i]);
        //}

        bottomPanel.add(infoPanel);
        bottomPanel.add(buttonsPanel);

        gameScreen.add(allSeats);
        gameScreen.add(bottomPanel);
    }
}
