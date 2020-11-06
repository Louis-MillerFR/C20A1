// Louis Miller C12A2 Clue Paths 1 Section B

package experiment;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;
public class TestBoard {
	
	// Holds the dimensions of the board
	final static int COLS = 4;
	final static int ROWS = 4;
	
	private TestBoardCell[][] grid = new TestBoardCell[COLS][ROWS]; // Holds the board cells
	private Set<TestBoardCell> visited; // Holds the visited list
	private Set<TestBoardCell> targets; // Holds the resulting targets from calcTargets()
	

	// Creates the board with default board cells
	public TestBoard() {
		
		// Creates the board cells
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
		
		// Creates the adjacencyList
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				if (i > 0 && this.getCell(i, j).isRoom() == this.getCell(i-1, j).isRoom()) { // Previous column
					this.getCell(i, j).addAdj(this.getCell(i-1, j));
				}
				if (i < COLS-1 && this.getCell(i, j).isRoom() == this.getCell(i+1, j).isRoom()) { // Next column
					this.getCell(i, j).addAdj(this.getCell(i+1, j));
				}
				if (j > 0 && this.getCell(i, j).isRoom() == this.getCell(i, j-1).isRoom()) { // Previous row
					this.getCell(i, j).addAdj(this.getCell(i, j-1));
				}
				if (j < ROWS-1 && this.getCell(i, j).isRoom() == this.getCell(i, j+1).isRoom()) { // Next row
					this.getCell(i, j).addAdj(this.getCell(i, j+1));
				}
			}
		}
	}
	
	// Calculates the targets from a given cell and travelling a given pathlength
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		if (pathlength > 0) { // Initial call initialisation of parameters
			targets = new HashSet<TestBoardCell>(); 
			visited = new HashSet<TestBoardCell>();
			this.calcTargets(startCell, -1 * pathlength);
			return;
		}
		
		if (pathlength == 0 || startCell.isRoom()) { // Adds to list if this is a room or no longer moving
			targets.add(startCell);
			return;
		}
		
		// Continue to move
		visited.add(startCell); // Adds to visited list
		for (TestBoardCell location: startCell.getAdjList()) { // Checks all paths and continues those available
			if (!visited.contains(location) && !location.getOccupied()) {
				this.calcTargets(location, pathlength + 1);
			}
		}
		visited.remove(startCell); // remove from visited list
		return;
	}
	
	// Fetches the targets
	public Set<TestBoardCell> getTargets() {
		return targets;	
	}
	
	// Returns the cell at the specified location
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
}
