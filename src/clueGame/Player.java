package clueGame;
import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Player { // Manages players
	private String name; // Keeps the player's name
	private Color color; // Keeps the player's color
	protected int row; // Keeps the player's row
	protected int column; // Keeps the player's column
	private Set<Card> hand = new HashSet<Card>();  // Gives the player a card
	private Set<Card> seen = new HashSet<Card>();
	protected ArrayList<Card> unseen;
	protected static ArrayList<Card> completeDeck;
	protected static Board board;
	
	public void updateHand(Card card) { // Gives the player a card
		hand.add(card);
		this.updateSeen(card);
	}
	
	public void updateSeen(Card seenCard) {
		seen.add(seenCard);
	}
	
	public Player(String name, Color color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		column = col;
		
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		Random rand = new Random();
		int randomizer = rand.nextInt(120);
		boolean firstTime = true;
		for (int i = randomizer % 3; i != (randomizer)%3 || firstTime; i = (i+1)%3) {
			firstTime = false;
			if (i == 0 && hand.contains(suggestion.room)) {
				return suggestion.room;
			} else if (i == 1 && hand.contains(suggestion.weapon)) {
				return suggestion.weapon;
			} else if (i == 2 && hand.contains(suggestion.person)) {
				return suggestion.person;
			}
		}
		return null;
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
	
	public void updateReferences(ArrayList<Card> deck, Board board) {
		completeDeck = new ArrayList<Card>(deck);
		Player.board = board;
	}
	
	public void updateDeck() {
		unseen = completeDeck;
		unseen.removeAll(seen);
	}
	
	public Room getRoom() {
		return board.getRoom(getLocation());
	}
	
	public BoardCell getLocation() {
		return board.getCell(row, column);
	}
}
