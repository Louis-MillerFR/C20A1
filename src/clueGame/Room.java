// Louis Miller CSCI306 Section B

package clueGame;

public class Room {
	private String name; // name of the room
	private BoardCell centerCell; // Board cell corresponding to the room's center
	private BoardCell labelCell; // Board cell corresponding to the room's label
	
	public Room(String code) { 
		name = code;
	}
	
	public String getName() { // returns the room's name
		return name;
	}
	
	public BoardCell getLabelCell() { // returns the label cell
		return labelCell;
	}
	
	public BoardCell getCenterCell() { // returns the center cell
		return centerCell;
	}
	
	public void setLabelCell(BoardCell label) { // sets the label cell
		labelCell = label;
	}
	
	public void setCenterCell(BoardCell center) { // sets the center cell
		centerCell = center;
	}
	
}