package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import clueGame.*;

public class GameSolutionTest {
	
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
	public void testAccusations() {
		// Test that each accusation works properly
		Solution solution = board.getSolution();
		assertTrue(board.checkAccusation(solution)); // Solution is the solution
		assertFalse(board.checkAccusation(new Solution(new Card("TEST", CardType.ROOM), solution.weapon, solution.person))); // Wrong Room
		assertFalse(board.checkAccusation(new Solution(solution.room, new Card("TEST", CardType.WEAPON), solution.person))); // Wrong Weapon
		assertFalse(board.checkAccusation(new Solution(solution.room, solution.weapon, new Card("TEST", CardType.PERSON)))); // Wrong Person
	}
	
	@Test
	public void testDisprove() { // test that disaproving works properly
		Solution suggestion = board.getSolution(); // we use the solution as a sample suggestion
		HumanPlayer tester = new HumanPlayer("Test Subject", null, 6, 9);
		tester.updateHand(new Card("TEST", CardType.ROOM));
		tester.updateHand(new Card("TEST", CardType.WEAPON));
		tester.updateHand(new Card("TEST", CardType.PERSON));
		assertTrue(tester.disproveSuggestion(suggestion) == null); // Case with no matching cards
		
		tester = new HumanPlayer("Test Subject", null, 6, 9);
		tester.updateHand(new Card("TEST", CardType.ROOM));
		tester.updateHand(new Card("TEST", CardType.WEAPON));
		tester.updateHand(suggestion.person);
		assertTrue(tester.disproveSuggestion(suggestion) == suggestion.person); // Case with one matching card
		
		
		tester = new HumanPlayer("Test Subject", null, 6, 9);
		tester.updateHand(suggestion.room);
		tester.updateHand(suggestion.weapon);
		tester.updateHand(suggestion.person);
		Set<Card> solutions = new HashSet<Card>();
		for (int i = 0; i < 100; i++) {  // Case with several matching cards (Theoretically can incorrectly fail but at extremely low odds)
			solutions.add(tester.disproveSuggestion(suggestion));
		}
		assertTrue(solutions.size()>1);
	}
	
	
	@Test
	public void testSuggestions() {
		// Disprove case where nobody can disprove
		assertTrue(board.handleSuggestion(board.getSolution()) == null);
		
		// Disprove case where only the accusing player can disprove
		Set<Card> solutions = board.currentPlayer(0).getCards();
		Card person = null;
		Card room = null;
		Card weapon = null;
		for (Card card : solutions) {
			if (card.getType() == CardType.PERSON) {
				person = card;
			} else if (card.getType() == CardType.ROOM) {
				room = card;
			} else if (card.getType() == CardType.WEAPON) {
				weapon = card;
			}
		}
		assertTrue(board.handleSuggestion(new Solution(room, weapon, person)) == null); 
		
	
		// Disprove case where the only one who can disprove is the human
		board.nextTurn();
		assertTrue(board.handleSuggestion(new Solution(room, weapon, person)) != null);  
		
		// Disprove case where two players can disprove but the first one is the one to do so
		solutions = board.currentPlayer(3).getCards();
		Iterator<Card> itr = solutions.iterator();
		person = itr.next();
		assertTrue(board.handleSuggestion(new Solution(room, weapon, person)) == person);
	}
}


