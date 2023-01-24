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
    private int current = 0;

    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Player dealer = new Player(0, "Dealer");
    private static Deck deck = new Deck();
    

    // TODO: design and create front end
    // FRONTEND
    // GridBagLayout is confusing, but it's fine
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
    JButton submitBets; //used to submit player's bets
    JButton payoutBets;

    JButton hitButton;
    JButton standButton;
    JButton splitButton;
    JButton doubleButton;

    public Main(){
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JLabel(new ImageIcon("images/betterTable.png")); //background image
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
            Player player = players.get(current);
            switch(event.getActionCommand()){
                case "start":
                    allSeats.add(players.get(0).getSeat().getDisplay());
                    allSeats.add(dealer.getSeat().getDisplay());
                    for(int i = 1; i < players.size(); i++){
                        allSeats.add(players.get(i).getSeat().getDisplay());
                    }
                    
                    betting();

                    

                    

                    titleScreen.setVisible(false);
                    gameScreen.setVisible(true);
                    break;

                case "hit":
                    //TODO: hit code
                    


                    if(!player.isStood && !player.isBust){
                       player.addCard(new Card(4, 2)); //testing
                       infoLabel.setText(player.getName() + "'s turn with " + player.getScore());
                    } else if(player.splitHandIsPlaying){
                        player.addCardSplit(new Card(4, 2));
                        infoLabel.setText(player.getName() + "'s split hand with " + player.getScore());
                    }

                    //TODO: add check for bust
                    int score = player.getScore();
                    if(score > 21){
                        player.isBust = true;
                    } else if(score == 21){
                        player.isStood = true;
                    }


                    if(!player.isBust && !player.isStood){
                        //the player can still play
                        infoLabel.setText(player.getName() + "'s turn with " + player.getScore());
                    } else if(!player.splitHandIsBust && !player.splitHandIsStood && player.splitHandIsPlaying){
                        infoLabel.setText(player.getName() + "'s split hand with " + player.getScore());
                    } else{
                        endCurrentTurn();
                    }

                    gameScreen.revalidate();
                    break;

                case "stand":
                    //TODO: stand code
                    //player.getSeat().clearCards(); //testing
                    if(player.isStood || player.isBust && player.splitHandIsPlaying){
                        player.splitHandIsStood = true;
                        endCurrentTurn();
                    } else{
                        player.isStood = true;
                        if(!player.splitHandIsPlaying){
                            endCurrentTurn();
                        }
                        
                    }

                    
                    gameScreen.revalidate();
                    break;

                case "split":
                    //TODO: check if the player can split
                    if(player.getHand().size() == 2){
                        if(player.getHand().get(0).getFace() == player.getHand().get(1).getFace()
                        && !player.splitHandIsPlaying){
                            player.split();
                            player.addCard(new Card(4, 2));
                            player.addCardSplit(new Card(4, 2));
                        }
                    }
                    
                    //dealer.split();
                    gameScreen.revalidate();
                    break;
                
                case "double":
                    //TODO: double down code
                    dealerTurn();
                    break;
                
                case "submit":
                    hitButton.setVisible(true);
                    standButton.setVisible(true);
                    splitButton.setVisible(true);
                    doubleButton.setVisible(true);

                    for (Player p : players) {
                        int pBet = p.getSeat().getBetInput();
                        if(pBet <= 0){
                            p.isPlaying = false;
                        } else if(pBet > p.getChipsAmount()){
                            pBet = p.getChipsAmount();
                        }
                        if(p.isPlaying){
                            p.setBet(pBet);
                        }

                        p.getSeat().setBetting(false);
                    }
                    submitBets.setVisible(false);

                    deck = new Deck();
                    deck.shuffle();
                    for (Player p : players) {
                        if(p.isPlaying){
                            p.addCard(new Card(4, 2));
                            p.addCard(new Card(4, 2)); 
                        }
                    }
                    dealer.addCard(new Card(4, 2));
                    dealer.addCard(new Card(4, 2));

                    current = -1;
                    for (Player p : players) {
                        //find the first playing player
                        if(p.isPlaying){
                            current = players.indexOf(p);
                            break;
                        }
                    }
                    if(current != -1){
                        infoLabel.setText(players.get(current).getName() + "'s turn with " + players.get(current).getScore());
                    }
                    break;

                case "payout":
                    //TODO: payout bets
                    payoutBets.setVisible(false);
                    betting();
                    
                    for (Player p : players) {
                       int value = p.getScore();
                       int dealerValue = dealer.getScore();
                       if((p.getHand().size() == 2) && (value == 21)) {
                            p.setChipsAmount(p.getChipsAmount() + ((int)(p.getBetAmount() * 1.5))); 
                       } else if(p.isBust) {
                            p.setChipsAmount(p.getChipsAmount() - p.getBetAmount());
                       } else if(dealer.isBust) {
                            p.setChipsAmount(p.getChipsAmount() + p.getBetAmount());
                       } else if(value > dealerValue) {
                            p.setChipsAmount(p.getBetAmount() + p.getScore());
                       } else if (value < dealerValue) {
                            p.setChipsAmount(p.getBetAmount() - p.getScore());
                       }
                    }


                    gameScreen.revalidate();
            }
        }
    }

    private static void runGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        new Main();
    }
  
    public static void main(String[] args) {
        for (int i = 0; i < MAX_PlAYER_COUNT; i++) {
            players.add(new Player(DEFAULT_CHIPS, "Player " + (i + 1)));

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
        doubleButton.setActionCommand("double");
        doubleButton.addActionListener(new Click());
        buttonsPanel.add(doubleButton);

        submitBets = new JButton("Submit All Bets");
        submitBets.setActionCommand("submit");
        submitBets.addActionListener(new Click());

        payoutBets = new JButton("Payout Bets");
        payoutBets.setActionCommand("payout");
        payoutBets.addActionListener(new Click());
        payoutBets.setVisible(false);
        
        infoPanel.add(infoLabel);
        infoPanel.add(submitBets);

        bottomPanel.add(infoPanel);
        bottomPanel.add(buttonsPanel);

        gameScreen.add(allSeats);
        gameScreen.add(bottomPanel);
    }
    //!SECTION

    private void betting(){
        hitButton.setVisible(false);
        standButton.setVisible(false);
        splitButton.setVisible(false);
        doubleButton.setVisible(false);

        infoLabel.setText("Everyone, place your bets");
        submitBets.setVisible(true);
        for(Player p : players){
            p.getSeat().setBetting(true);
        }
    }

    private void endCurrentTurn(){
        if(current == MAX_PlAYER_COUNT - 1){
            //the rounnd is over, move on to dealer's turn
            dealerTurn();
        } else{
            //finds the next playing player
            current++;
            while(!players.get(current).isPlaying && current < MAX_PlAYER_COUNT){
                current++;
            }
        }
        infoLabel.setText(players.get(current).getName() + "'s turn with " + players.get(current).getScore());
    }

    private void dealerTurn(){
        while(dealer.getScore() < 17){
            dealer.addCard(new Card(4, 2));
            //gameScreen.revalidate();
        }
        
        if(dealer.getScore() > 21){
            infoLabel.setText("Dealer busts with " + dealer.getScore());
        } else{
            infoLabel.setText("Dealer stands with " + dealer.getScore());
        }
        payoutBets.setVisible(true);
    }
}
