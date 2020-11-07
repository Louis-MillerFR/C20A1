package clueGame;
import java.awt.Color;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;

public class ComputerPlayer extends Player {  // Manages Computer Players
	
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}
	
	public Solution createSuggestion() { // Generates a suggestion
		updateDeck(); // Find current unseen cards
		Random rand = new Random();
		int randomizer = rand.nextInt(1000);
		Card weapon = null;
		Card person = null;
		Boolean firstTime = true;	
		for (int i = (randomizer % unseen.size()); i != (randomizer)%unseen.size() || firstTime; i = (i+1)%unseen.size(), firstTime = false) { // loops through from a random point
			if (unseen.get(i).getType() == CardType.WEAPON) { // Adds the card to the proper type
				weapon = unseen.get(i);
			} else if (unseen.get(i).getType() == CardType.PERSON) {
				person = unseen.get(i);
			}
		}
		return new Solution(this.getRoom().getCard(), weapon, person);
	}
	
	public BoardCell selectTargets(int roll) { // Generates a target location for movement
		updateDeck(); // Find current unseen cards
		board.calcTargets(getLocation(), roll);
		ArrayList<BoardCell> targets = new ArrayList<BoardCell>(board.getTargets()); // Creates the targets to be processed
		
		Random rand = new Random();
		int randomizer = rand.nextInt(1000);
		boolean firstTime = true;
		for (int i = ((randomizer) % targets.size()); i != (randomizer)%targets.size() || firstTime; i = (i+1)% targets.size(), firstTime = false) { // Loops through from a random point
			if (targets.get(i).isRoomCenter() && unseen.contains(board.getRoom(targets.get(i).getInitial()).getCard())) { // Will go for an unvisited room if it sees it
				return targets.get(i); 
			}
		}
		return targets.get(randomizer%targets.size()); // Assigns a random target if no unvisited rooms in range
	}
}
