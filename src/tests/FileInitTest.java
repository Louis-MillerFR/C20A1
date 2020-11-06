// Louis Miller CSCI306 Section B

package tests;

/*
 * This program tests that config files are loaded properly.
 */

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTest {
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	@Test
	public void testDimensions() {
		// Tests the correct dimensions were loaded
		assertEquals(25, board.getNumRows() );
		assertEquals(25, board.getNumColumns() );
	}
	
	// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() {
			int numDoors = 0;
			for (int row = 0; row < board.getNumRows(); row++)
				for (int col = 0; col < board.getNumColumns(); col++) {
					BoardCell cell = board.getCell(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(18, numDoors);
		}
		
		// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
		// two cells that are not a doorway.
		// These cells are white on the planning spreadsheet
		@Test
		public void FourDoorDirections() {
			BoardCell cell = board.getCell(7, 8);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
			cell = board.getCell(3, 4);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.UP, cell.getDoorDirection());
			cell = board.getCell(18, 7);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
			cell = board.getCell(4, 4);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
			// Test that walkways are not doors
			cell = board.getCell(0, 4);
			assertFalse(cell.isDoorway());
		}
		
		@Test
		public void testRoomLabels() {
			// To ensure data is correctly loaded, test retrieving a few rooms
			// from the hash, including the first and last in the file and a few others
			assertEquals("Kitchen", board.getRoom('K').getName() );
			assertEquals("Gaming Room", board.getRoom('G').getName() );
			assertEquals("Bathroom", board.getRoom('B').getName() );
			assertEquals("Theatre", board.getRoom('T').getName() );
			assertEquals("Library", board.getRoom('L').getName() );
		}
		
		// Tests if the room label and center location are correct
		@Test
		public void testRoomLocation() {
			assertEquals(board.getCell(0, 2), board.getRoom('G').getLabelCell());
			assertEquals(board.getCell(1, 3), board.getRoom('G').getCenterCell());
			
			assertEquals(board.getCell(6, 2), board.getRoom('C').getLabelCell());
			assertEquals(board.getCell(7, 3), board.getRoom('C').getCenterCell());
			
			assertEquals(board.getCell(12, 2), board.getRoom('L').getLabelCell());
			assertEquals(board.getCell(14, 3), board.getRoom('L').getCenterCell());
			
			assertEquals(board.getCell(19, 2), board.getRoom('S').getLabelCell());
			assertEquals(board.getCell(21, 2), board.getRoom('S').getCenterCell());
			
			assertEquals(board.getCell(2, 11), board.getRoom('K').getLabelCell());
			assertEquals(board.getCell(3, 12), board.getRoom('K').getCenterCell());
			
			assertEquals(board.getCell(18, 9), board.getRoom('D').getLabelCell());
			assertEquals(board.getCell(20, 10), board.getRoom('D').getCenterCell());
			
			assertEquals(board.getCell(2, 20), board.getRoom('U').getLabelCell());
			assertEquals(board.getCell(3, 21), board.getRoom('U').getCenterCell());
			
			assertEquals(board.getCell(12, 18), board.getRoom('T').getLabelCell());
			assertEquals(board.getCell(13, 19), board.getRoom('T').getCenterCell());
			
			assertEquals(board.getCell(21, 18), board.getRoom('B').getLabelCell());
			assertEquals(board.getCell(22, 20), board.getRoom('B').getCenterCell());
		}
		
		@Test
		public void testSetup() {
			// Ensure that the setup files were correctly loaded
			assertEquals(9, board.getRoomCount());
			assertEquals(board.getCell(0, 0).getInitial(), 'G');
			assertEquals(board.getCell(24, 24).getInitial(), 'X');
			assertEquals(board.getCell(15, 5).getInitial(), 'L');
			assertEquals(board.getCell(20, 10).getInitial(), 'D');
		}
}
