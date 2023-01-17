
import java.util.ArrayList;

public class Player {
    private Seat seat;

    private int chipsAmount;
    private int betAmount;
    private String name;

    public boolean isPlaying;
    public boolean isStood, isBust;
    public boolean splitHandIsPlaying;
    public boolean splitHandIsBust, splitHandIsStood;

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

        isPlaying = true;
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

        splitHandIsPlaying = true;
    }
    
    /**
     * Clears the player's {@link #hand} and {@link #splitHand}
     */
    public void resetHands(){
        hand = new ArrayList<Card>();
        splitHand = new ArrayList<Card>();
    }

    public void reset(){
        this.resetHands();
        isBust = false;
        isStood = false;
        isPlaying = true;
        splitHandIsBust = false;
        splitHandIsPlaying = false;
        splitHandIsStood = false;
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

    /**
     * Returns the score of the player's main hand
     * @return {@code int} the score of the player's main hand
     * @see #calcValue(ArrayList)
     */
    public int getScore(){
        return calcValue(hand);
    }

    /**
     * Returns the score of the player's split hand
     * @return {@code int} the score of the player's split hand
     * @see #calcValue(ArrayList)
     */
    public int getSplitScore(){
        return calcValue(splitHand);
    }

    /**
     * calaculates the total value a given hand has
     * @param h -- {@code ArrayList<Card>} the hand to calculate the score of
     * @return {@code int} the value of the hand
     * @see #getScore()
     * @see #getSplitScore()
     */
    public static int calcValue(ArrayList<Card> h){
        int totalValue = 0;
        boolean hasAce = false;
        for(Card card : h){
            if(card.getFace() == 14 || card.getFace() == 1){
                hasAce = true;
                totalValue += 1;
            } else if(card.getFace() >= 10){
                totalValue += 10;
            } else{
                totalValue += card.getFace();
            }
        }
        if(hasAce && totalValue <= 11){
            totalValue += 10;
        }

        return totalValue;
    }
   // !SECTION
   
   // SECTION setters
    /**
     * Sets the amount of chips the player is betting to {@code amount}
     * @param amount -- the amount to bet
     */
    public void setBet(int amount) {
        betAmount =  amount;
        seat.updateBet(betAmount);
    }

   // !SECTION
}