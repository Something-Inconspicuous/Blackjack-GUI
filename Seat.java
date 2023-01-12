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
    JPanel mainPane;

    JPanel playerInfoPanel;
    JLabel nameLabel;
    JLabel chipsLabel;
    JLabel betLabel;

    JPanel cardsPanel;
    JPanel handPanel;
    JPanel splitPanel;

    ArrayList<JLabel> handCards;
    ArrayList<JLabel> splitCards;

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
        chipsLabel = new JLabel();

        playerInfoPanel.add(nameLabel);
        playerInfoPanel.add(new JLabel()); //add an empty label to have the name on the topo and the chips info on bottom
        playerInfoPanel.add(chipsLabel);

        cardsPanel = new JPanel();
        
        handPanel = new JPanel();
        handPanel.setLayout(new GridLayout(6, 2, 0, 0));

        splitPanel = new JPanel();
        splitPanel.setLayout(new GridLayout(6, 2, 0, 0));

        mainPane.add(nameLabel);
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
     * Adds a card to the displayed hand of the seat
     * @param card -- {@code Card} the card to add
     */
    public void addCard(Card card){
        ImageIcon cardIcon = new ImageIcon(card.toImageLink());

        handCards.add(new JLabel(cardIcon));
        handPanel.add(new JLabel(cardIcon));
    }

    /**
     * Removes all cards from the seat
     */
    public void clearCards(){
        for(JLabel label: handCards){
            handPanel.remove(label);
            handCards.remove(label);
        }

        for(JLabel label: splitCards){
            splitPanel.remove(label);
            splitCards.remove(label);
        }
    }
}
