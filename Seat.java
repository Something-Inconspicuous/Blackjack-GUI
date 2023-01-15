import javax.swing.*;
import java.awt.*;
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
    private JLabel nameLabel;
    private JLabel chipsLabel;
    private JLabel betLabel;

    private JPanel cardsPanel;
    private JPanel handPanel;
    private JPanel splitPanel;


    private ArrayList<JLabel> handCards;
    private ArrayList<JLabel> splitCards;
    

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
        chipsLabel = new JLabel("Chips: ");
        betLabel = new JLabel("Bet: ");

        playerInfoPanel.add(nameLabel);
        playerInfoPanel.add(new JLabel()); //add an empty label to have the name on the topo and the chips info on bottom
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

        handCards = new ArrayList<JLabel>();
        splitCards = new ArrayList<JLabel>();

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
        ImageIcon cardIcon = new ImageIcon(card.toImageLink());

        handCards.add(new JLabel(cardIcon));
        //System.out.println("added " + cardIcon);
        handPanel.add(handCards.get(handCards.size() - 1));
    }

    /**
     * Adds a card to the displayed split hand of the seat
     * @param card -- {@code Card} the card to add
     */
    public void addSplit(Card card){
        ImageIcon cardIcon = new ImageIcon(card.toImageLink());

        splitCards.add(new JLabel(cardIcon));
        splitPanel.add(splitCards.get(splitCards.size() - 1));
    }

    /**
     * Removes all cards from the seat
     */
    public void clearCards(){
        for(JLabel label: handCards){
            handPanel.remove(label);
        }

        for(JLabel label: splitCards){
            splitPanel.remove(label);
        }

        handCards = new ArrayList<JLabel>();
        splitCards = new ArrayList<JLabel>();
    }

    public JPanel getDisplay(){
        return mainPane;
    }
}
