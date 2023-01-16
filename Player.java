
import java.util.ArrayList;

public class Player {
    private Seat seat;

    private int chipsAmount;
    private int betAmount;
    private String name;

    public boolean isPlaying;

    private ArrayList<Card> hand;
    private ArrayList<Card> splitHand;
    
    /**
     * Constructs an empty {@code Player} object.
     * <p>
     * Creates a {@code Player} object without assigning anything.
     * 
     * @see #Player(int, String)
     * @apiNote unused, why is this here?
     */
    public Player() {}
    
    /**
     * Constructs a {@code Player} object with specified chips and name
     * 
     * @param amount -- {@code int} the amount of chips to start the player with
     * @param name -- {@code String} the name to give the player
     */
    public Player(int amount, String name) {
        chipsAmount = amount;
        this.name = name;

        hand = new ArrayList<Card>();
        splitHand = new ArrayList<Card>();
        seat = new Seat(this.name);
        seat.updateChips(chipsAmount);
    }
    
    /**
     * Adds a given {@link Card} object to the player's hand
     * @param newCard -- {@code Card} the card to add
     */
    public void addCard(Card newCard){
        hand.add(newCard);
        seat.addCard(newCard);
    }
    
    /**
     * Adds a given {@link Card} object to the player's second hand if they split
     * @param newCard -- {@code Card} the card to add
     * @see #split()
     */
    public void addCardSplit(Card newCard){
        splitHand.add(newCard);
        seat.addSplit(newCard);
    }

    /**
     * Splits the player's hand into two hands
     * <p>
     * The second card in {@link #hand} is added to {@link #splitHand} and removed from itself.
     */
    public void split(){
        splitHand.add(hand.get(1));
        hand.remove(1);
        
        seat.clearCards();
        seat.addCard(hand.get(0));
        seat.addSplit(hand.get(0));
    }
    
    /**
     * Clears the player's {@link #hand} and {@link #splitHand}
     */
    public void resetHands(){
        hand = new ArrayList<Card>();
        splitHand = new ArrayList<Card>();
    }

   // SECTION getters
    /**
     * Returns the amount of chips the player has
     * @return {@code int} the amount of chips the player has
     */ 
    public int getChipsAmount() {
        return chipsAmount;
    }

    /**
     * Returns the amount the player has bet total
     * 
     * @return {@code int} the player's bet
     */
    public int getBetAmount() {
        return betAmount;
    }
   
    /**
     * Returns the player's name for added detail and immersion
     * 
     * @return {@code String} the player's name
     */
    public String getName() {
        return name;
    }

    public Seat getSeat(){
        return seat;
    }
   // !SECTION
   
   // SECTION setters
    /**
     * Sets the amount of chips the player is betting to {@code amount}
     * @param amount -- the amount to bet
     */
    public void setBet(int amount) {
        betAmount =  amount;
    }

   // !SECTION

   public void updateSeat(){
    
   }
}