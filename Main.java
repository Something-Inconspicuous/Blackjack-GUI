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
    //TODO: betting
    // BACKEND
    public static final int MAX_PlAYER_COUNT = 8;
    public static final int DEFAULT_CHIPS = 100;

    /**
     * Will store the index in {@link #players} that corrosponds to the current player to take their turn is
     */
    private static int currentPlayerIndex = 0;

    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Player dealer = new Player(0, "Dealer");
    private static Deck deck = new Deck();
    

    // TODO: design and create front end
    // FRONTEND
    //GridBagLayout is confusing, but it's fine
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    JFrame frame;
    JLabel contentPane;
    

    //Start menu components
    JPanel titleScreen;
    JLabel titleLabel;
    JButton startButton;

    //Main game components
    JPanel gameScreen;

    JPanel allSeats;

    JPanel bottomPanel; //will display all of the info stuff
    JPanel infoPanel; //will display who's turn it is and the buttons to play the game
    JLabel infoLabel; //will display who's turn it is
    JPanel buttonsPanel; //will hold the buttons
    JButton submitBets; //used to submit player's bets-

    JButton hitButton;
    JButton standButton;
    JButton splitButton;
    JButton doubleButton;

    public Main(){
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JLabel(new ImageIcon("images/table.png")); //background image
        contentPane.setLayout(new FlowLayout());

        buildTitleScreen();
        buildTable();

        contentPane.add(titleScreen);
        contentPane.add(gameScreen);
        
        gameScreen.setVisible(false);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setSize(1240, 860);
        frame.setVisible(true);
    }

    //Action Listener
    class Click implements ActionListener{
        public void actionPerformed(ActionEvent event){
            //System.out.println(event.getActionCommand());
            switch(event.getActionCommand()){
                case "start":
                    allSeats.add(players.get(0).getSeat().getDisplay());
                    allSeats.add(dealer.getSeat().getDisplay());
                    for(int i = 1; i < players.size(); i++){
                        allSeats.add(players.get(i).getSeat().getDisplay());
                    }

                    for(Player p : players){
                        p.addCard(new Card(4, 2));
                        p.addCard(new Card(4, 2));
                        p.getSeat().setBetting(true);
                    }
                    dealer.addCard(new Card(4, 2));
                    dealer.addCard(new Card(4, 2));


                    titleScreen.setVisible(false);
                    gameScreen.setVisible(true);
                    break;

                case "hit":
                    //TODO: hit code
                    //players.get(0).getSeat().addCard(new Card(4, 2)); //testing
                    players.get(currentPlayerIndex).addCard(new Card(4, 2));



                    gameScreen.revalidate();
                    break;

                case "stand":
                    //TODO: stand code
                    players.get(currentPlayerIndex).getSeat().clearCards();
                    gameScreen.revalidate();
                    break;

                case "split":
                    //TODO: check if the player can split
                    //players.get(currentPlayerIndex).split();
                    for(Player p : players){
                        p.split();
                    }
                    dealer.split();
                    gameScreen.revalidate();
                    break;
                
                case "double":
                    //TODO: double down code
                    break;
                
                case "submit":
                    for (Player p : players) {
                        int pBet = p.getSeat().getBetInput();
                        if(pBet <= 0){
                            p.isPlaying = false;
                        } else if(pBet > p.getChipsAmount()){
                            pBet = p.getChipsAmount();
                        }
                        p.setBet(pBet);

                        p.getSeat().setBetting(false);
                    }
                    submitBets.setVisible(false);

                    //TODO: player turns
            }
        }
    }

    private static void runGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        new Main();
    }
  
    public static void main(String[] args) {
        for (int i = 0; i < MAX_PlAYER_COUNT; i++) {
            players.add(new Player(MAX_PlAYER_COUNT, "Player " + (i + 1)));

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

        infoPanel = new JPanel();
        infoLabel = new JLabel(players.get(0).getName() + " with " + players.get(0).getBetAmount()); //change to hand value

        buttonsPanel = new JPanel();

        hitButton = new JButton("Hit");
        hitButton.setActionCommand("hit");
        hitButton.addActionListener(new Click());
        buttonsPanel.add(hitButton);

        standButton = new JButton("Stand");
        standButton.setActionCommand("stand");
        standButton.addActionListener(new Click());
        buttonsPanel.add(standButton);

        splitButton = new JButton("Split");
        splitButton.setActionCommand("split");
        splitButton.addActionListener(new Click());
        buttonsPanel.add(splitButton);
        
        doubleButton = new JButton("Double Down");
        buttonsPanel.add(doubleButton);

        submitBets = new JButton("Submit All Bets");
        submitBets.setActionCommand("submit");
        submitBets.addActionListener(new Click());
        
        infoPanel.add(infoLabel);
        infoPanel.add(submitBets);

        bottomPanel.add(infoPanel);
        bottomPanel.add(buttonsPanel);

        gameScreen.add(allSeats);
        gameScreen.add(bottomPanel);
    }
    //!SECTION
}
