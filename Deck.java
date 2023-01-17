import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of 52 {@link Card} object instances.
 * 
 * @see Card
 * @see https://en.wikipedia.org/wiki/Playing_card
 * @see https://en.wikipedia.org/wiki/Standard_52-card_deck
 */
public class Deck{
  private ArrayList<Card> cards = new ArrayList<Card>();

  /**
   * Creates a deck of 52 cards, stored as an {@code ArrayList<Card>}. No Jokers are included
   */
  public Deck(){
    for(int i = 1; i <= 4; i++){
      for(int j = 2; j <= 14; j++){ //uncomment this to store an ace as a 14
      //for(int j = 1; j <= 13; j++){ //uncomment this to store an ace as a 1
        Card tempCard = new Card(i, j);
        cards.add(tempCard);
      }
    }
  }

  /**
   * Deals a card from a random location in the deck, removing it from the deck
   * @return {@code Card} -- a random card
   */
  public Card dealRandomCard(){
    int index = (int)(cards.size()*Math.random());
    Card tempCard = cards.get(index);
    cards.remove(cards.get(index));

    return tempCard;
  }

  /**
   * Deals the top card in the deck and removes it from the deck
   * @return {@code Card} -- the top card
   */
  public Card dealTopCard(){
    Card tempCard = cards.get(0);
    cards.remove(0);

    return tempCard;
  }

  /**
   * Randomizes the location of every card in the deck
   */
  public void shuffle(){
    Collections.shuffle(cards);
  }

  /**
   * Checks if the deck is empty
   * @return {@code boolean} -- true if deck is empty, false otherwise
   */
  public boolean isEmpty(){
    return cards.isEmpty();
  }
}