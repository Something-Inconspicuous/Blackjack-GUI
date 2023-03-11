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
    public static final int MAX_PlAYER_COUNT = 8;
    public static final int DEFAULT_CHIPS = 100;

    /**
     * Will store the index in {@link #players} that corrosponds to the current player to take their turn is
     */
    private int current = 0;

    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Player dealer = new Player(0, "Dealer");
    private static Deck deck = new Deck();

    private static boolean isInsurance = false;
    
    //SECTION - FRONTEND
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

    JButton insYesButton;
    JButton insNoButton;
    //!SECTION

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
        gameScreen.setPreferredSize(new Dimension(1000, 900));
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setSize(1280, 900);
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
                    frame.setMinimumSize(new Dimension(1240, 860));
                    break;

                case "hit":

                    if(!player.isStood && !player.isBust){
                       player.addCard(deck.dealTopCard());
                       infoLabel.setText(player.getName() + "'s turn with " + player.getScore());
                    } else if(player.splitHandIsPlaying){
                        player.addCardSplit(deck.dealTopCard());
                        infoLabel.setText(player.getName() + "'s split hand with " + player.getScore());
                    }

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
                        infoLabel.setText(player.getName() + "'s split hand with " + player.getSplitScore());
                    } else{
                        endCurrentTurn();
                    }

                    gameScreen.revalidate();
                    break;

                case "stand":
                    //player.getSeat().clearCards(); //testing
                    if(player.isStood || player.isBust && player.splitHandIsPlaying){
                        player.splitHandIsStood = true;
                        endCurrentTurn();
                    } else{
                        player.isStood = true;
                        if(!player.splitHandIsPlaying){
                            endCurrentTurn();
                        } else{
                            infoLabel.setText(player.getName() + "'s split hand with " + player.getSplitScore());
                        }
                        
                    }

                    
                    gameScreen.revalidate();
                    break;

                case "split":
                    if(player.getHand().size() == 2){
                        if(player.getHand().get(0).getFace() == player.getHand().get(1).getFace()
                        && !player.splitHandIsPlaying){
                            player.split();
                            player.addCard(deck.dealTopCard());
                            player.addCardSplit(deck.dealTopCard());
                            player.splitHandIsPlaying = true;
                        }
                    }
                    
                    gameScreen.revalidate();
                    break;
                
                case "double":
                    int s = player.getScore();
                    if(s >= 9 && s <= 11 && player.getHand().size() == 2){
                        player.addCard(deck.dealTopCard());
                        player.isStood = true;
                        player.setBet(player.getBetAmount()*2);
                    }

                    //stand
                    if(player.isStood || player.isBust && player.splitHandIsPlaying){
                        player.splitHandIsStood = true;
                        endCurrentTurn();
                    } else{
                        player.isStood = true;
                        if(!player.splitHandIsPlaying){
                            endCurrentTurn();
                        } else{
                            infoLabel.setText(player.getName() + "'s split hand with " + player.getSplitScore());
                        }
                        
                    }

                    gameScreen.revalidate();
                    break;
                
                case "submit":
                    //check if anyone is playing, if not, then we ignore this input
                    boolean noOneBetting = true;
                    for(Player p : players){
                        int pBet = p.getSeat().getBetInput();
                        if(pBet > 0){
                            noOneBetting = false;
                            break;
                        }
                    }
                    if(noOneBetting){
                        break;
                    }

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
                            noOneBetting = false;
                            p.setBet(pBet);
                        }

                        p.getSeat().setBetting(false);
                        p.updateSeat();
                    }


                    submitBets.setVisible(false);

                    deck = new Deck();
                    deck.shuffle();
                    for (Player p : players) {
                        if(p.isPlaying){
                            p.addCard(deck.dealTopCard());
                            p.addCard(deck.dealTopCard()); 
                        }
                    }
                    dealer.addCard(deck.dealTopCard());
                    dealer.addCard(deck.dealTopCard());
                    dealer.getSeat().setCardVisible(0, false);

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

                    if(dealer.getHand().get(1).getFace() == 14 || dealer.getHand().get(1).getFace() == 1){
                        enableInsurance();
                    }
                    gameScreen.revalidate();
                    break;

                case "payout":
                    payoutBets.setVisible(false);
                    betting();
                    
                    for (Player p : players) {
                       int value = p.getScore();
                       int dealerValue = dealer.getScore();

                       int wonChips = 0;
                       if((p.getHand().size() == 2) && (value == 21)) {
                            wonChips += ((int)(p.getBetAmount() * 1.5)); 
                       } else if(p.isBust) {
                            wonChips -= p.getBetAmount();
                       } else if(dealer.isBust) {
                            wonChips += p.getBetAmount();
                       } else if(value > dealerValue) {
                            wonChips += p.getBetAmount();
                       } else if (value < dealerValue) {
                            wonChips -= p.getBetAmount();
                       } else{
                        //the player and dealer have tied, so no bet is paid out
                       }
                       if(p.splitHandIsPlaying){
                            int splitValue = p.getSplitScore();
                            if((p.getHand().size() == 2) && (value == 21)) {
                                wonChips += ((int)(p.getBetAmount() * 1.5)); 
                            } else if(p.isBust) {
                                wonChips -= p.getBetAmount();
                            } else if(dealer.isBust) {
                                wonChips += p.getBetAmount();
                            } else if(splitValue > dealerValue) {
                                wonChips += p.getBetAmount();
                            } else if (splitValue < dealerValue) {
                                wonChips -= p.getBetAmount();
                            } else{
                             //the player and dealer have tied, so no bet is paid out
                            }
                       }
                       p.setChipsAmount(p.getChipsAmount() + wonChips);
                       p.setBet(0);
                       p.reset();
                       p.updateSeat();
                       //reset the player's bet to 0
                    }
                    dealer.reset();
                    betting();
                    gameScreen.revalidate();
                    break;

                case "yes":
                    if(dealer.getScore() == 21){
                        player.setChipsAmount(player.getChipsAmount() + (int)(player.getBetAmount()/2) );
                    } else{
                        player.setChipsAmount(player.getChipsAmount() - (int)(player.getBetAmount()/2) );
                    }
                    endCurrentTurn();
                    break;

                case "no":
                    endCurrentTurn();
                    break;
            }
        }
    }

    private static void runGUI(){
        JFrame.setDefaultLookAndFeelDecorated(false);
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

        titleLabel = new JLabel("Blackjack");
        //titleLabel.setBounds(100, 30, 120, 50);

        startButton = new JButton("GO!");
        titleScreen.add(titleLabel);

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
        buttonsPanel.add(payoutBets);

        insYesButton = new JButton("Yes");
        insYesButton.setActionCommand("yes");
        insYesButton.addActionListener(new Click());
        insYesButton.setVisible(false);
        buttonsPanel.add(insYesButton);

        insNoButton = new JButton("No");
        insNoButton.setActionCommand("no");
        insNoButton.addActionListener(new Click());
        insNoButton.setVisible(false);
        buttonsPanel.add(insNoButton);
        
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
        current++;
        if(current >= MAX_PlAYER_COUNT){
            //the rounnd is over, move on to dealer's turn
            if(isInsurance){
                current = -1;
                for (Player p : players) {
                    //find the first playing player
                    if(p.isPlaying){
                        current = players.indexOf(p);
                        break;
                    }
                }
                isInsurance = false;
                infoLabel.setText(players.get(current).getName() + "'s turn with " + players.get(current).getScore());
                
                if(dealer.getScore() == 21){
                    dealerTurn();                
                } else{
                    for (Player p : players) {
                        p.updateSeat();
                    }
                }
            } else{
                dealerTurn();
            }
        } else{
            //finds the next playing player
            
            while(current < MAX_PlAYER_COUNT && !players.get(current).isPlaying){
                current++;
            }
            if(current >= MAX_PlAYER_COUNT){
                if(isInsurance){
                    current = -1;
                    for (Player p : players) {
                        //find the first playing player
                        if(p.isPlaying){
                            current = players.indexOf(p);
                            break;
                        }
                    }
                    isInsurance = false;

                    insYesButton.setVisible(false);
                    insNoButton.setVisible(false);
                    hitButton.setVisible(true);
                    standButton.setVisible(true);
                    splitButton.setVisible(true);
                    doubleButton.setVisible(true);

                    infoLabel.setText(players.get(current).getName() + "'s turn with " + players.get(current).getScore());
                } else{
                    dealerTurn();
                }
            } else{
                if(isInsurance){
                    infoLabel.setText(players.get(current).getName() + ", would you like to make an insurance bet?");
                } else{
                    infoLabel.setText(players.get(current).getName() + "'s turn with " + players.get(current).getScore());
                }
            }
        }
    }

    private void dealerTurn(){
        dealer.isBust = false;
        dealer.getSeat().setCardVisible(0, true);

        while(dealer.getScore() < 17){
            dealer.addCard(deck.dealTopCard());
            //gameScreen.revalidate();
        }
        
        if(dealer.getScore() > 21){
            infoLabel.setText("Dealer busts with " + dealer.getScore());
            dealer.isBust = true;
        } else{
            infoLabel.setText("Dealer stands with " + dealer.getScore());
        }

        for(int i = 0; i < buttonsPanel.getComponentCount(); i++){
            buttonsPanel.getComponent(i).setVisible(false);
        }

        payoutBets.setVisible(true);
        current = 0;
    }

    private void enableInsurance(){
        isInsurance = true;
        current = -1;
        for (Player p : players) {
            //find the first playing player
            if(p.isPlaying){
                current = players.indexOf(p);
                break;
            }
        }

        for(int i = 0; i < buttonsPanel.getComponentCount(); i++){
            buttonsPanel.getComponent(i).setVisible(false);
        }
        insYesButton.setVisible(true);
        insNoButton.setVisible(true);

        infoLabel.setText(players.get(current).getName() + ", would you like to make an insurance bet?");

    }
}
