package clueGame;

public class Solution {
	public Card person; // keeps the person solution
	public Card room; // keeps the room solution
	public Card weapon; // keeps the weapon solution
	
	public Solution(Card room, Card weapon, Card person) { // creates the solution
		this.room = room; 
		this.weapon = weapon;
		this.person = person;
	}
	
	public Boolean matches(Solution other) {
		if (this.room == other.room && this.weapon == other.weapon && this.person == other.person) {
			return true;
		}
		return false;
	}
}
