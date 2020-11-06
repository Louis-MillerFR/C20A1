// Louis Miller C12A2 Clue Paths 1 Section B

package tests;
import experiment.TestBoard;
import experiment.TestBoardCell;
import org.junit.jupiter.api.BeforeEach;

import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;


public class BoardTestsExp {
	TestBoard board;

	@BeforeEach // Run before each test to initialize board
	public void setUp() {
		//board should create adjacency list
		board = new TestBoard();
	}
	
	/*
	 * Test adjacencies for top left
	 */
	@Test
	public void testAdjacencyTL() {
		board = new TestBoard();
		TestBoardCell cell = board.getCell(0,0); // Creates cell of focus
		Set<TestBoardCell> testList = cell.getAdjList(); // Obtains Adjacency list
		 assertTrue(testList.contains(board.getCell(1, 0))); // Verifies that each cell which should be true is true
		 assertTrue(testList.contains(board.getCell(0, 1))); 
		 assertEquals(2, testList.size()); // Verifies the total count of cells is correct
	}
	
	/*
	 * Test adjacencies for bottom right
	 */
	@Test
	public void testAdjacencyBR() {
		board = new TestBoard();
		TestBoardCell cell = board.getCell(3,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for bottom left
	 */
	@Test
	public void testAdjacencyBL() {
		board = new TestBoard();
		TestBoardCell cell = board.getCell(3,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for right edge
	 */
	@Test
	public void testAdjacencyRE() {
		board = new TestBoard();
		TestBoardCell cell = board.getCell(1,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * Test adjacencies for no edge
	 */
	@Test
	public void testAdjacencyNE() {
		board = new TestBoard();
		TestBoardCell cell = board.getCell(1,2);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * Test targets with several rolls and start locations
	 */
	@Test
	public void testTargetsNormal() {
		board = new TestBoard();
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell,  3); // Calculates the list of targets with the correct distance
		Set<TestBoardCell> targets = board.getTargets(); // Gets the list of targets
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3,0)));
		assertTrue(targets.contains(board.getCell(2,1)));
		assertTrue(targets.contains(board.getCell(0,1)));
		assertTrue(targets.contains(board.getCell(1,2)));
		assertTrue(targets.contains(board.getCell(0,3)));
		assertTrue(targets.contains(board.getCell(1,0)));
	}
	
	/*
	 * Test targets when mixed
	 */
	@Test
	public void testTargetsMixed() {
		board = new TestBoard();
		// set up occupied cells.
		board.getCell(0,  2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell,  3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(3,  targets.size());
		assertTrue(targets.contains(board.getCell(1,  2)));
		assertTrue(targets.contains(board.getCell(2,  2)));
		assertTrue(targets.contains(board.getCell(3,  3)));
	}
	
	/*
	 * Tests targets within a pathlength of 1, equivalent to testAdjency
	 */
	@Test
	public void testTargetsImmediate() {
		board = new TestBoard();
		TestBoardCell cell = board.getCell(1, 0);
		board.calcTargets(cell,  1);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(3,  targets.size());
		assertTrue(targets.contains(board.getCell(0,  0)));
		assertTrue(targets.contains(board.getCell(1,  1)));
		assertTrue(targets.contains(board.getCell(2,  0)));
	}
	
	/*
	 * Test targets when all occupied are out of reach
	 */
	@Test
	public void testTargetsFar() {
		// set up occupied cells.
		board = new TestBoard();
		board.getCell(1,  3).setOccupied(true);
		board.getCell(2, 2).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell,  3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6,  targets.size());
		assertTrue(targets.contains(board.getCell(0,  3)));
		assertTrue(targets.contains(board.getCell(3,  0)));
		assertTrue(targets.contains(board.getCell(1,  2)));
		assertTrue(targets.contains(board.getCell(2,  1)));
		assertTrue(targets.contains(board.getCell(0,  1)));
		assertTrue(targets.contains(board.getCell(1,  0)));
	}
	
	/*
	 * Tests that occupied cells block the path
	 */
	@Test
	public void testTargetsBlock() {
		board = new TestBoard();
		// set up occupied cells.
		board.getCell(1,  0).setOccupied(true);
		board.getCell(0, 1).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell,  2);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(0,  targets.size());
	}
	
}
