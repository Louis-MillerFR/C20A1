package clueGame;

public class Solution {
	public static Card person; // keeps the person solution
	public static Card room; // keeps the room solution
	public static Card weapon; // keeps the weapon solution
	
	public Solution(Card room, Card weapon, Card person) { // creates the solution
		this.room = room; 
		this.weapon = weapon;
		this.person = person;
	}
}
