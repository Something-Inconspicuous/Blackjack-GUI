import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * The {@code Seat} class represents a seat at the blackjack table.
 * <p>
 * Each {@code Seat} object has all of the GUI stored inside of it, and is added to the {@code Player} obeject
 * instance that it corrosponds to. It stores <b>Strictly</b> GUI components or related objects/variables
 * 
 * @see Player
 */
public class Seat {
    private JPanel mainPane;

    private JPanel playerInfoPanel;
    private JTextField betInput;
    private JLabel nameLabel;
    private JLabel chipsLabel;
    private JLabel betLabel;

    private JPanel cardsPanel;
    private JPanel handPanel;
    private JPanel splitPanel;


    private ArrayList<JLabel> handCardsLabels;
    private ArrayList<JLabel> splitCardsLabels;
    private ArrayList<Card> handCards;
    private ArrayList<Card> splitCards;
    

    /**
     * Constructs a {@code Seat} object to hold everything that an individual seat at the tabel would need to hold
     * @param name -- {@code String} The name of the player sitting at the seat
     */
    public Seat(String name){
        mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

        playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new GridLayout(2, 2, 0, 0));
        nameLabel = new JLabel("Name: " + name);


        betInput = new JTextField(10);
        betInput.setVisible(false);
        //this piece of code was found at 
        //https://www.tutorialspoint.com/how-can-we-make-jtextfield-accept-only-numbers-in-java and modified by me
        betInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
               if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                  betInput.setEditable(true);
               } else {
                  betInput.setEditable(false);
               }
            }
         });
        
        
        chipsLabel = new JLabel("Chips: ");
        betLabel = new JLabel("Bet: ");

        playerInfoPanel.add(nameLabel);
        playerInfoPanel.add(betInput);
        //betInput.setVisible(false);
        playerInfoPanel.add(chipsLabel);
        playerInfoPanel.add(betLabel);

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.PAGE_AXIS));
        
        handPanel = new JPanel();
        handPanel.setLayout(new GridLayout(2, 6, 0, 0));

        splitPanel = new JPanel();
        splitPanel.setLayout(new GridLayout(2, 6, 0, 0));
        
        cardsPanel.add(handPanel);
        cardsPanel.add(splitPanel);

        handCardsLabels = new ArrayList<JLabel>();
        splitCardsLabels = new ArrayList<JLabel>();

        handCards = new ArrayList<Card>();
        splitCards = new ArrayList<Card>();

        mainPane.add(playerInfoPanel);
        mainPane.add(cardsPanel);
        mainPane.add(splitPanel);
    }

    /**
     * Updates the display to display the proper amount of chips
     * @param chips -- {@code int} the amount of chips to display
     */
    public void updateChips(int chips){
        chipsLabel.setText("Chips: " + chips);
    }

    /**
     * Updates the display to display the current bet
     * @param bet -- {@code int} the bet to display
     */
    public void updateBet(int bet){
        betLabel.setText("Bet: " + bet);
    }

    /**
     * Adds a card to the displayed hand of the seat
     * @param card -- {@code Card} the card to add
     */
    public void addCard(Card card){
        handCards.add(card);

        ImageIcon cardIcon = new ImageIcon(card.toImageLink());

        handCardsLabels.add(new JLabel(cardIcon));
        handPanel.add(handCardsLabels.get(handCardsLabels.size() - 1));
    }

    /**
     * Adds a card to the displayed split hand of the seat
     * @param card -- {@code Card} the card to add
     */
    public void addSplit(Card card){
        splitCards.add(card);

        ImageIcon cardIcon = new ImageIcon(card.toImageLink());

        splitCardsLabels.add(new JLabel(cardIcon));
        splitPanel.add(splitCardsLabels.get(splitCardsLabels.size() - 1));
    }

    /**
     * Changes the image of a card to either show or hide the card's face
     * @param index -- {@code int} the index of the card in the seat to change
     * @param visibility -- {@code boolean} whether or not the card is displayed face up
     */
    public void setCardVisible(int index, boolean visibility){
        //System.out.println(handCards.get(0));
        ImageIcon icon = visibility ?
        new ImageIcon(handCards.get(index).toImageLink())
        : new ImageIcon(Card.CARD_BACK_URL);
        

        handCardsLabels.get(index).setIcon(icon);
    }

    /**
     * Removes all cards from the seat
     */
    public void clearCards(){
        for(JLabel label: handCardsLabels){
            handPanel.remove(label);
        }

        for(JLabel label: splitCardsLabels){
            splitPanel.remove(label);
        }

        handCardsLabels = new ArrayList<JLabel>();
        splitCardsLabels = new ArrayList<JLabel>();
    }

    /**
     * Sets whether a seat can bet
     * @param b -- {@code boolean} what to set the visibility og the input to
     */
    public void setBetting(boolean b){
        betInput.setVisible(b);
    }

    /**
     * Gets the number inputted for the bet
     * @return {@code int} the number in the bet input, or -1 if nothin is in the input
     * @throws ArithmeticException the text in {@link #betInput} is unable to be parsed into an {@code int}
     */
    public int getBetInput(){
        String bet = betInput.getText();
        if(bet.equals("")){
            return -1;
        }
        return Integer.parseInt(bet);
    }

    /**
     * returns the display of the seat as a {@link JPanel}
     * @return {@code JPanel} the GUI components all formatted together in one panel
     */
    public JPanel getDisplay(){
        return mainPane;
    }
}
