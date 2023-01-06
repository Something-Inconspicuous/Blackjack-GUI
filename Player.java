public class Player {
    private int chipsAmount;
    private int betAmount;
    private String name;
   
   public Player() {
    chipsAmount = 100;
   }
   public Player(int amount, String name) {
    chipsAmount = amount;
    this.name = name;
   }
   
   /**
    * get how many chips a user has. Mostly for Calculate winner Function
    * @return amount of chips that the player has
    */
   public int getChipsAmount() {
    return chipsAmount;
   }
   /**
    * 
    * @return Get user's bet
    */
   public int getBetAmount() {
    return betAmount;
   }
   
   /**
    * get user's name for added detail and immersion
    * @return get user's name
    */
   public String getName() {
    return name;
   }

   /**
    * Set user's bet
    * @param amount
    */
   public void setBet(int amount) {
    betAmount =  amount;
   }
    
}
