public class Card {

    private int suit, face;
    
    public Card() {}

    /**
     * Constructs a card object with specified suit and face
     * @param suit -- Suit of card (int)
     * @param face -- Face of card (int)
     */
    
     public Card(int suit, int face) {
        this.suit = suit;
        this.face = face;
    }
   
    public int getSuit() {
        return this.suit;
    }
    public int getFace() {
        return this.face;
    }
    public String toString() {
        String output = "";
        //add switch case that sets output to the face of the card
        switch(this.getFace()) {
            case 1:
                output = "Ace";
                break;  
            case 11:
                output = "Jack";
                break;
            case 12:
                output = "Queen";
                break;
            case 13:
                output = "King";
                break;
            default:
                output = Integer.toString(this.getFace());
        }
        //add another switch case that adds " of " and the suit of the card
        switch(this.getSuit()){
            case 1:
                output += " of Spades";
                break;
            case 2:
                output += " of Hearts";
                break;
            case 3:
                output += " of Diamonds";
                break;
            case 4:
                output += " of Clubs";
                break;
        }

        return output;
    }
}