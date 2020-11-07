package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;


public class ComputerAITest {
	
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
		ComputerPlayer ai = new ComputerPlayer("Test Subject", null, 7, 3);
		assertTrue(ai.createSuggestion().room == ai.getRoom().getCard());
		
		// Test for multiple weapons not seen
		Set<Card> cards = new HashSet<Card>();
		for (int i = 0; i < 100; i++) {  // Case with several matching cards (Theoretically can incorrectly fail but at extremely low odds)
			cards.add(ai.createSuggestion().weapon);
		}
		assertTrue(cards.size() > 1);
	
		// Test for multiple people not seen
		cards = new HashSet<Card>();
		for (int i = 0; i < 100; i++) {  // Case with several matching cards (Theoretically can incorrectly fail but at extremely low odds)
			cards.add(ai.createSuggestion().person);
		}
		assertTrue(cards.size() > 1);
		
		// Test for last weapon and person not seen selected:
		for (int i = 0; i < 5; i++) {
			Solution chosen = ai.createSuggestion();
			ai.updateSeen(chosen.person);
			ai.updateSeen(chosen.weapon);
		}
		
		cards = new HashSet<Card>(); 
		for (int i = 0; i < 100; i++) {
			cards.add(ai.createSuggestion().weapon);
			cards.add(ai.createSuggestion().person);
		}
		assertTrue(cards.size() == 2);
	}

	@Test
	public void testPath() {
		// Test that there it selects randomly if no rooms in the list
		ComputerPlayer ai = new ComputerPlayer("Test Subject", null, 7, 3);
		Set<BoardCell> targets = new HashSet<BoardCell>();
		for (int i = 0; i < 100; i++) {  // Case with several matching cards (Theoretically can incorrectly fail but at extremely low odds)
			targets.add(ai.selectTargets(2));
		}
		assertTrue(targets.size() > 1);
		
		// Test that it selects a room if it has not seen it
		targets = new HashSet<BoardCell>();
		for (int i = 0; i < 100; i++) {  // Case with several matching cards (Theoretically can incorrectly fail but at extremely low odds)
			targets.add(ai.selectTargets(3));
		}
		assertTrue(targets.size() == 1);
		
		// Test that it selects at random if a room has been seen
		ai.updateSeen(board.getRoom(ai.selectTargets(3)).getCard());
		targets = new HashSet<BoardCell>();
		for (int i = 0; i < 100; i++) {  // Case with several matching cards (Theoretically can incorrectly fail but at extremely low odds)
			targets.add(ai.selectTargets(3));
		}
		assertTrue(targets.size() > 1);
	}
}
