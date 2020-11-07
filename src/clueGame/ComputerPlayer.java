package clueGame;
import java.awt.Color;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;

public class ComputerPlayer extends Player {  // Manages Computer Players
	
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}
	
	public Solution createSuggestion() {
		updateDeck();
		Random rand = new Random();
		int randomizer = rand.nextInt(1000);
		Card weapon = null;
		Card person = null;
		Boolean firstTime = true;
		System.out.println(completeDeck.size());
		for (int i = (randomizer % unseen.size()); i != (randomizer)%unseen.size() || firstTime; i = (i+1)%unseen.size(), firstTime = false) {
			if (unseen.get(i).getType() == CardType.WEAPON) {
				weapon = unseen.get(i);
			} else if (unseen.get(i).getType() == CardType.PERSON) {
				person = unseen.get(i);
			}
		}
		
		
		return new Solution(this.getRoom().getCard(), weapon, person);
	}
	
	public BoardCell selectTargets(int roll) {
		updateDeck();
		board.calcTargets(getLocation(), roll);
		ArrayList<BoardCell> targets = new ArrayList<BoardCell>(board.getTargets());
		
		Random rand = new Random();
		int randomizer = rand.nextInt(1000);
		for (int i = ((randomizer+1) % targets.size()); i != (randomizer)%targets.size(); i = (i+1)% targets.size()) {
			if (targets.get(i).isRoomCenter() && unseen.contains(board.getRoom(targets.get(i).getInitial()).getCard())) {
				return targets.get(i);
			}
		}
		return targets.get(randomizer%targets.size());
	}
}
