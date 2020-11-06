// Louis Miller C12A2 Clue Paths 1 Section B

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
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
	
	// Test a variety of walkway scenarios
		// These tests are Yellow on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			// Test with one walkway piece
			Set<BoardCell> testList = board.getAdjList(19, 24);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(19, 23)));
			
			// Test between walls
			testList = board.getAdjList(10, 4);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(10, 3)));
			assertTrue(testList.contains(board.getCell(10, 5)));

			// Test next to nothing
			testList = board.getAdjList(7, 15);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(7, 14)));
			assertTrue(testList.contains(board.getCell(7, 16)));
			assertTrue(testList.contains(board.getCell(6, 15)));
			assertTrue(testList.contains(board.getCell(8, 15)));

			// Test near unusable space
			testList = board.getAdjList(12,8);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(13, 8)));
			assertTrue(testList.contains(board.getCell(11, 8)));
			assertTrue(testList.contains(board.getCell(12, 7)));
		}
		
		
		// Tests adjacency lists for rooms with secret doors
		@Test
		public void testAdjacencySecretDoors()
		{
			// Test with TK Secret Door
			Set<BoardCell> testList = board.getAdjList(13, 19);
			assertEquals(4, testList.size());
			// Secret Passage
			assertTrue(testList.contains(board.getCell(3, 12)));
			// Door walkways
			assertTrue(testList.contains(board.getCell(18, 22)));
			assertTrue(testList.contains(board.getCell(13, 14)));
			assertTrue(testList.contains(board.getCell(9, 17)));
			
			// Test with GS Secret Door
			testList = board.getAdjList(1, 3);
			assertEquals(2, testList.size());
			// Secret Passage
			assertTrue(testList.contains(board.getCell(21, 2)));
			// Door walkway
			assertTrue(testList.contains(board.getCell(3, 4)));
		}		
		
		// Tests the adjacency list of room cells not at the center
		@Test
		public void testAdjacencyRoom()
		{
			// Tests corners
			Set<BoardCell> testList = board.getAdjList(23, 24);
			assertEquals(0, testList.size());
			testList = board.getAdjList(0, 24);
			assertEquals(0, testList.size());
			testList = board.getAdjList(23, 0);
			assertEquals(0, testList.size());
			// Not corner but in room
			testList = board.getAdjList(2,2);
			assertEquals(0, testList.size());
		}
		
		// Tests the adjacency list of doorways
		@Test
		public void testAdjacencyDoorways()
		{
			// Test with one walkway piece
			Set<BoardCell> testList = board.getAdjList(23, 9);
			assertEquals(3, testList.size());
			// Walkway
			assertTrue(testList.contains(board.getCell(23, 8)));
			assertTrue(testList.contains(board.getCell(23, 8)));
			// Room center
			assertTrue(testList.contains(board.getCell(20, 10)));
			
			// Test between walls
			testList = board.getAdjList(13, 14);
			assertEquals(3, testList.size());
			// Walkway
			assertTrue(testList.contains(board.getCell(12, 14)));
			assertTrue(testList.contains(board.getCell(14, 14)));
			// Room center
			assertTrue(testList.contains(board.getCell(13, 19)));

			// Test between walls
			testList = board.getAdjList(3, 17);
			assertEquals(4, testList.size());
			// Walkway
			assertTrue(testList.contains(board.getCell(2, 17)));
			assertTrue(testList.contains(board.getCell(4, 17)));
			// Doorway
			assertTrue(testList.contains(board.getCell(3, 16)));
			// Room center
			assertTrue(testList.contains(board.getCell(3, 21)));
		}
		
		// Tests targeting with separate distances
		@Test
		public void testTargetsDistance() {
			// Location 1
			// test a roll of 1
			board.calcTargets(board.getCell(17, 1), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCell(17, 2)));
			// test a roll of 2
			board.calcTargets(board.getCell(17, 1), 2);
			targets= board.getTargets();
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCell(17, 3)));
			// test a roll of 3
			board.calcTargets(board.getCell(17, 1), 3);
			targets= board.getTargets();
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCell(17, 4)));
			
			// Location 2
			// test a roll of 1
			board.calcTargets(board.getCell(10, 5), 1);
			targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(10, 4)));
			assertTrue(targets.contains(board.getCell(10, 6)));
			// test a roll of 2
			board.calcTargets(board.getCell(10, 5), 2);
			targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(10, 3)));
			assertTrue(targets.contains(board.getCell(10, 7)));
			// test a roll of 3
			board.calcTargets(board.getCell(10, 5), 3);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(10, 2)));
			assertTrue(targets.contains(board.getCell(10, 8)));
			assertTrue(targets.contains(board.getCell(11, 7)));
			
			// Location 3
			// test a roll of 1
			board.calcTargets(board.getCell(6, 16), 1);
			targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(6, 17)));
			assertTrue(targets.contains(board.getCell(6, 15)));
			assertTrue(targets.contains(board.getCell(5, 16)));	
			assertTrue(targets.contains(board.getCell(7, 16)));	
			// test a roll of 2
			board.calcTargets(board.getCell(6, 16), 2);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(4, 16)));
			assertTrue(targets.contains(board.getCell(5, 17)));
			assertTrue(targets.contains(board.getCell(8, 16)));	
			assertTrue(targets.contains(board.getCell(6, 14)));	
			assertTrue(targets.contains(board.getCell(7, 17)));
			assertTrue(targets.contains(board.getCell(7, 15)));
			// test a roll of 3
			board.calcTargets(board.getCell(6, 16), 3);
			targets= board.getTargets();
			assertEquals(12, targets.size());
			assertTrue(targets.contains(board.getCell(6, 17)));
			assertTrue(targets.contains(board.getCell(6, 15)));
			assertTrue(targets.contains(board.getCell(5, 16)));	
			assertTrue(targets.contains(board.getCell(7, 16)));	
			assertTrue(targets.contains(board.getCell(3, 16)));
			assertTrue(targets.contains(board.getCell(9, 16)));
			assertTrue(targets.contains(board.getCell(8, 15)));	
			assertTrue(targets.contains(board.getCell(8, 17)));	
			assertTrue(targets.contains(board.getCell(7, 18)));
			assertTrue(targets.contains(board.getCell(4, 17)));
			assertTrue(targets.contains(board.getCell(7, 14)));	
			assertTrue(targets.contains(board.getCell(6, 13)));	
		}
		
		@Test
		// test to verify how occupied locations change path
		public void testTargetsOccupied() {
			// test for completely blocked path
			board.getCell(23, 15).setOccupied(true);
			board.calcTargets(board.getCell(24, 14), 1);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCell(23, 14)));
			board.calcTargets(board.getCell(24, 14), 2);
			targets = board.getTargets();
			assertEquals(0, targets.size());
			
			// test for partially blocked path
			board.getCell(17, 3).setOccupied(true);
			board.calcTargets(board.getCell(17, 4), 3);
			targets = board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(17, 7)));
			assertTrue(targets.contains(board.getCell(18, 6)));
			
			// test for traversible block
			board.getCell(9, 21).setOccupied(true);
			board.calcTargets(board.getCell(9, 22), 4);
			targets = board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(8, 21)));
			assertTrue(targets.contains(board.getCell(9, 24)));
			assertTrue(targets.contains(board.getCell(9, 20)));
			assertTrue(targets.contains(board.getCell(8, 19)));
		}
		
		// Tests targeting with room starts
				@Test
				public void testTargetsRooms() {
					// Room to room through walkway
					board.calcTargets(board.getCell(22, 20), 3);
					Set<BoardCell> targets= board.getTargets();
					assertEquals(5, targets.size());
					assertTrue(targets.contains(board.getCell(19, 16)));
					assertTrue(targets.contains(board.getCell(23, 16)));
					assertTrue(targets.contains(board.getCell(20, 15)));
					assertTrue(targets.contains(board.getCell(22, 15)));
					// Into the room
					assertTrue(targets.contains(board.getCell(20, 10)));
					
					// Room with secret passage
					// Room to room through walkway
					// Blocked by other room
					board.calcTargets(board.getCell(21, 2), 2);
					targets= board.getTargets();
					assertEquals(3, targets.size());
					assertTrue(targets.contains(board.getCell(21, 6)));
					assertTrue(targets.contains(board.getCell(22, 7)));

					// From the passage
					assertTrue(targets.contains(board.getCell(1, 3)));
				}
}
