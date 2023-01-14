
import java.util.ArrayList;

public class Player {
    private Seat seat;

    private int chipsAmount;
    private int betAmount;
    private String name;
    
    /**
     * Constructs an empty {@code Player} object.
     * <p>
     * Creates a {@code Player} object without assigning anything. When the dealer is
     * creates, this constructor is used
     * 
     * @see #Player(int, String)
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
        seat = new Seat(this.name);
        seat.updateChips(chipsAmount);
    }
    
    /**
     * Returns the amount of chips the player has
     * @return {@code int} the amount of chips the player has
     */ 
    public int getChipsAmount() {
        return chipsAmount;
    }

   // SECTION getters
   

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