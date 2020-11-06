// Louis Miller CSCI306 Section B

package clueGame;
import java.util.*;

public class BoardCell {
	private int row; // row of the cell
	private int col; // column of the cell
	private char initial; // initial character of the cell
	private DoorDirection doorDirection = DoorDirection.NONE; // Door direction for the cell
	private boolean roomLabel; // keeps if this is a label
	private boolean roomCenter; // keeps if this is a room center
	private boolean occupied = false; // keeps if the cell is occupied
	private char secretPassage; // keeps track of which room a secretPassage connects to
	private Set<BoardCell> adjList = new HashSet<BoardCell>();; // keeps track of adjacent cells
	
	public BoardCell(int row, int column, String content) {
		this.row = row; // sets the row
		col = column; // sets the column
		initial = content.charAt(0); // sets the initial
		secretPassage = content.charAt(0); // sets the secret passage to none by default
		
		if (content.length() != 1) { // Gives special set up if there is a second character
			if (content.charAt(1) == '^') { // Checks for a door and assigns the proper door direction
				doorDirection = DoorDirection.UP;
			} else if (content.charAt(1) == 'v') {
				doorDirection = DoorDirection.DOWN;
			} else if (content.charAt(1) == '>') {
				doorDirection = DoorDirection.RIGHT;
			} else if (content.charAt(1) == '<') {
				doorDirection = DoorDirection.LEFT;
			}
			
			roomLabel = (content.charAt(1) == '#'); // Assigns if room label
			roomCenter = (content.charAt(1) == '*'); // Assigns if room center
			if (!(roomLabel || roomCenter) && doorDirection == DoorDirection.NONE) { // Assigns secret Passage
				secretPassage = content.charAt(1);
			}
		}
	}

	public boolean isDoorway() { // checks if this is a doorway
		 return (doorDirection != DoorDirection.NONE);
	}
	
	public void addAdj(BoardCell adj) { // Adds adjacent cell
		adjList.add(adj);
	}
	
	public Set<BoardCell> getAdjList() { // Returns the list of adjacent cells
		return adjList;
	}
	
	public DoorDirection getDoorDirection() { // gives the door's direction
		return doorDirection;
	}
	
	public boolean isLabel() { // checks if this is a room label
		return roomLabel;
	}
	
	public boolean isRoomCenter() { // checks if this is a room center
		return roomCenter;
	}
	
	public char getSecretPassage() { // gets the stored secret passage
		return secretPassage;
	}
	
	public char getInitial() { // gets the initial of the cell
		return initial;
	}
	
	public void setOccupied(boolean occupied) { // Set if the cell is occupied
		this.occupied = occupied;
	}
	
	public boolean getOccupied() { // Return whether the cell is occupied
		return occupied;
	}
}
