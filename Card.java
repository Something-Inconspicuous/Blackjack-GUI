/**
 * The {@code Card} class represents a single card per instance.
 * <p>
 * Each card stores its own suit and face as integers. The cards are displayed as the 
 * <a href="https://en.wikipedia.org/wiki/French-suited_playing_cards">French Suited</a>,
 * <a href="https://en.wikipedia.org/wiki/Standard_52-card_deck">Standerd 52-card deck</a>
 * using the 
 * <a href=https://en.wikipedia.org/wiki/French-suited_playing_cards#English_pattern>English Pattern</a>.
 * 
 * 
 * @see #getFace()
 * @see #getSuit()
 * @see Deck
 * @see https://en.wikipedia.org/wiki/Playing_card
 * @apiNote {@link #suit} and {@link #face} are {@code private}. Use their respective get methods to access them
 */
public class Card {

    /**
     * The suit of the card as in {@code int} as follows
     * <ol>
     *   <li>Spades</li>
     *   <li>Hearts</li>
     *   <li>Diamonds</li>
     *   <li>Clubs</li>
     * </ol> 
     */
    private int suit;
    /** 
     * The face of the card as an {@code int}
     */
    private int face;

    public static final String CARD_BACK_URL = "images/cardback.png";
    
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
     *   <li>Spades</li>
     *   <li>Hearts</li>
     *   <li>Diamonds</li>
     *   <li>Clubs</li>
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
            case 14:
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
        String link = this.toString();

        link = "images/" + link.toLowerCase().replaceAll(" ", "") + ".png";
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