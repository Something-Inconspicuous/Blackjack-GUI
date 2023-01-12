/**
 * The {@code Card} class represents a single card per instance.
 * <p>
 * Each card stores its own suit and face as integers.
 * These are stored privately, so use the below methods to access them.
 * 
 * @see #getFace()
 * @see #getSuit()
 */
public class Card {

    private int suit, face;
    
    /**
     * Empty constructor, sets nothing
     */
    public Card() {}

    /**
     * Constructs a {@code Card} object with specified suit and face
     * @param suit -- {@code int} Suit of card
     * @param face -- {@code int} Face of card 
     */
    
     public Card(int suit, int face) {
        this.suit = suit;
        this.face = face;
    }
    
    /**
     * Returns the suit of the card
     * <p>
     * 
     * The suit is stored as in integer as follows
     * <ol>
     *   <li>Clubs</li>
     *   <li>Diamonds</li>
     *   <li>Hearts</li>
     *   <li>Spades</li>
     * </ol> 
     * 
     * @return {@code int} the suit of the card as above
     */
    public int getSuit() {
        return this.suit;
    }

    /**
     * Returns the face value of the card
     * 
     * @return {@code int} the face value of the card 
     */
    public int getFace() {
        return this.face;
    }

    /**
     * Gives a {@code String} representation in the format of "Face of Suit"
     *
     * @return {@code String} the card as a String 
     */
    @Override
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

    /**
     * Returns the link to the image of the card
     * 
     * @return {@code String} the imageurl of the image of the card
     */
    public String toImageLink(){
        //TODO: add images
        String link = this.toString();

        link = "image/" + link.toLowerCase().replaceAll(" ", "") + ".png";
        //all of the card images follow the pattern of "faceofsuit.png"

        return link;
    }

    /**
     * Returns the link of the image of a card of given suit and face
     * 
     * @param suit -- {@code int} the suit of the card image wanted
     * @param face -- {@code int} the face of the card image wanted
     * @return {@code String} the imagurl of a card with given suit and face
     * @see #toImageLink()
     */
    public static String toImageLink(int suit, int face){
        return (new Card(suit, face)).toImageLink();
    }
}