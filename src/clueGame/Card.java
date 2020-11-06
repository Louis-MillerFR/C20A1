package clueGame;

public class Card {
	private String cardName; // Holds the card's name
	private CardType cardType; // Holds the card's type
	
	public Card(String name, CardType type) {
		cardName = name;
		cardType = type;
	}
	
	public Boolean equals(Card target) { // checks if too cards share the same type
		return (cardName == target.getName() && cardType == target.getType());
	}
	
	public String getName() { // Gives the card's name
		return cardName;
	}
	
	public CardType getType() { // Gives the card's type
		return cardType;
	}
}
