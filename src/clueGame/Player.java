package clueGame;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player { // Manages players
	private String name; // Keeps the player's name
	private Color color; // Keeps the player's color
	protected int row; // Keeps the player's row
	protected int column; // Keeps the player's column
	private Set<Card> hand = new HashSet<Card>();  // Gives the player a card
	
	public void updateHand(Card card) { // Gives the player a card
		hand.add(card);
	}
	
	public Player(String name, Color color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		column = col;
		
	}
	
	public Set<Card> getCards() { // return the player's cards
		return hand;
	}
	
	public String getName() { // returns the player's name
		return name;
	}
	
	public Color getColor() { // returns the player's color
		return color;
	}
	
	public int getRow() { // returns the player's row
		return row;
	}
	
	public int getColumn() { // returns the player's column
		return column;
	}
}
