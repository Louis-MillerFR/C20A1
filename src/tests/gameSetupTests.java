package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class gameSetupTests {
	
private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	@Test
	public void testSolutionCards() {
		// Test that each solution card is the correct type
		assertTrue(board.getSolution().room.getType() == CardType.ROOM);
		assertTrue(board.getSolution().weapon.getType() == CardType.WEAPON);
		assertTrue(board.getSolution().person.getType() == CardType.PERSON);
	}
	
	@Test
	public void testPlayerCount() {
		// Test that there is the appropriate amount of humans and computers
		assertTrue(board.getHumans().size() == 1);
		assertTrue(board.getComputers().size() == 5);
	}
	
	@Test
	public void testCardsDealt() {
		// Test that each player has the correct number of cards dealt
		for (int i = 0; i < board.getHumans().size(); i++) {
			assertEquals(board.getHumans().get(i).getCards().size(), 3);
		}
		for (int i = 0; i < board.getComputers().size(); i++) {
			assertTrue(board.getComputers().get(i).getCards().size() == 3);
		}
	}
	
	@Test
	public void testNoDuplicates() {
		// Test that there are no duplicate cards
		Set<Card> cards = new HashSet<Card>(); 
		for (int i = 0; i < board.getHumans().size(); i++) {
			for (int j = 0; j < board.getHumans().get(i).getCards().size(); j++) {
				cards.addAll(board.getHumans().get(i).getCards());
			}
		}
		for (int i = 0; i < board.getComputers().size(); i++) {
			for (int j = 0; j < board.getComputers().get(i).getCards().size(); j++) {
				cards.addAll(board.getComputers().get(i).getCards());
			}
		}
		assertTrue(cards.size() == 18);
	}
}
