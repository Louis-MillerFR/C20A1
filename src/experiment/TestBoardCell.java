// Louis Miller C12A2 Clue Paths 1 Section B

package experiment;
import java.util.*;
public class TestBoardCell {
	private static int cellRow; // stores the cell's row
	private static int cellColumn; // stores the cell's column
	private boolean occupied = false; // determines if the cell is occupied
	private boolean room = false; // determines if the cell is a room
	private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>(); // Holds the adjacency list
	
	// Initializes the cell with provided coordinates
	public TestBoardCell(int row, int column) {
		cellRow = row;
		cellColumn = column;
	}
	public int[] cellCoords() {
		return new int[]{cellRow, cellColumn};
	}
	
	// Gives the cell's adjacent cells
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	// Updates whether the cell is a room
	public void setRoom(boolean bool) {
		room = bool;
	}
	
	// Returns if the cell is or isn't a room
	public boolean isRoom() {
		return room;
	}
	
	// Updates whether the cell is occupied
	public void setOccupied(boolean bool) {
		occupied = bool;
	}
	
	// Returns if the cell is or isn't occupied
	public boolean getOccupied() {
		return occupied;
	}
	
	// Add a cell to the adjacency list
	public void addAdj(TestBoardCell added) {
		adjList.add(added);
	}
	
}
