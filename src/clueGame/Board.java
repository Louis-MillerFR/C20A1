// Louis Miller CSCI306 Section B

package clueGame;
import java.util.*;
import experiment.TestBoardCell;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;

public class Board {
	private BoardCell[][] grid; // Stores the grid of the boardcells
	private int numRows; // keeps the number of rows
	private int numColumns; // keeps the number of columns
	private String layoutConfigFile; // keeps the config file for the layout
	private String setupConfigFile; // keeps the config file for the layout
	private Map<Character, Room> roomMap; // keeps track of the characters and room links
	private static Board theInstance = new Board(); // Instance of the board
	private Set<BoardCell> targets; // Holds the resulting targets from calcTargets()
	private Set<BoardCell> visited; // Holds the visited targets during calcTargets calculation
	private char roomStart; // Holds the character of a calc target's starting room
	private String[] weapons = new String[6]; // Keeps track of all of the weapons
	private ArrayList<HumanPlayer> humans = new ArrayList<HumanPlayer>(); // keeps track of humans
	private ArrayList<ComputerPlayer> computers = new ArrayList<ComputerPlayer>(); // keeps track of computers
	private ArrayList<Card> deck; // keeps the unserved cards from deck
	private Solution solution; // keeps the solution
	private Boolean loadedCards; // keeps the loaded cards
	private int turn;
	
	private Board() {
		super();
	}
	
	public static Board getInstance() { // returns the board's instance
		return theInstance;
	}
	
	public void initialize() { // Sets up the board
		try { // loads the config files
			this.loadConfigFiles();
		} catch (BadConfigFormatException e) {
			System.out.println("Error in files");
		}
		this.setCellProperties(); // Set up all board cells
		if (loadedCards) { // deal cards if specified in setup
			this.deal();
		}
	}
	
	public void setCellProperties() { // Stores the proper information for each cell
		for (int i = 0; i < numRows; i++) { // Sets the label and center cells properly
			for (int j = 0; j < numColumns; j++) {
				if (grid[i][j].isLabel()) roomMap.get(grid[i][j].getInitial()).setLabelCell(grid[i][j]);
				if (grid[i][j].isRoomCenter()) roomMap.get(grid[i][j].getInitial()).setCenterCell(grid[i][j]);
			}
		}
		
		List<DoorDirection> doors = Arrays.asList(new DoorDirection[] {DoorDirection.UP, DoorDirection.DOWN, DoorDirection.LEFT, DoorDirection.RIGHT}); // Contains significant door directions
		int[] adjY = {-1,1,0,0}; // Stores the y value for each adjacency
		int[] adjX = {0,0,-1,1}; // Stores the x value for each adjacency
		
		// Get adjacency list
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				int doorIndex = doors.indexOf(grid[i][j].getDoorDirection()); // Finds adjacency for doorways
				if (doorIndex != -1) { // If there is a door set the adjacencies for both sides
					grid[i][j].addAdj(roomMap.get(grid[i+adjY[doorIndex]][j+adjX[doorIndex]].getInitial()).getCenterCell());
					roomMap.get(grid[i+adjY[doorIndex]][j+adjX[doorIndex]].getInitial()).getCenterCell().addAdj(grid[i][j]);
				}
				
				if (grid[i][j].getInitial() == 'W') { // Adjacency for walkway
					for (int k = 0; k < 4; k++) {
						if (this.inBounds(i+adjY[k], j+adjX[k]) && grid[i+adjY[k]][j+adjX[k]].getInitial() == 'W') grid[i][j].addAdj(grid[i+adjY[k]][j+adjX[k]]);
					}
				}
	
				if (grid[i][j].getSecretPassage() != grid[i][j].getInitial()) { // Secret Passage added to adjacency
					roomMap.get(grid[i][j].getInitial()).getCenterCell().addAdj(roomMap.get(grid[i][j].getSecretPassage()).getCenterCell());
				}
			}
		}
	}
	
	public void deal() { // deal the cards
		Card room; // keeps the room card
		Card weapon; // keeps the weapon card
		Card person; // keeps the person card
		(new ComputerPlayer(null, null, 0, 0)).updateReferences(deck, theInstance); // gives the full deck to the player class.
		int itr; // iterator
		Random rand = new Random(); // random number generation
		do { // shuffle till find a room
			itr = rand.nextInt(deck.size());
			room = deck.get(itr);
		} while (room.getType() != CardType.ROOM);
		deck.remove(itr);
		do { // shuffle till find a weapon
			itr = rand.nextInt(deck.size());
			weapon = deck.get(itr);
		} while (weapon.getType() != CardType.WEAPON);
		deck.remove(itr);
		do { // shuffle till find a person
			itr = rand.nextInt(deck.size());
			person = deck.get(itr);
		} while (person.getType() != CardType.PERSON);
		deck.remove(itr);
		solution = new Solution(room, weapon, person); // Create solution
		
		int cardsPerPlayer = deck.size() / (humans.size() + computers.size()); // determines card amount to serve to player
		for (int j = 0; j < cardsPerPlayer; j++) {
			for (int i = 0; i < humans.size(); i++) { // gives humans the correct amount of cards
				itr = rand.nextInt(deck.size());
				humans.get(i).updateHand(deck.get(itr));
				deck.remove(itr);
			}
			for (int i = 0; i < computers.size(); i++) { // gives computers the correct amount of cards
				itr = rand.nextInt(deck.size());
				computers.get(i).updateHand(deck.get(itr));
				deck.remove(itr);
			}
		}
		turn = 0;
	}
	
	public void calcTargets(BoardCell start, int distance) { // Calculates the targets
		if (distance > 0) { // Initial call initialization of parameters
			targets = new HashSet<BoardCell>(); 
			visited = new HashSet<BoardCell>();
			roomStart = start.getInitial();
			this.calcTargets(start, -1 * distance);
			return;
		}
		
		if (distance == 0 || ((visited.size() != 0 && start.getInitial() != 'W' && start.getInitial() != roomStart))) { // Adds to list if this is a room or no longer moving
			targets.add(start);
			return;
		}

		visited.add(start); // Adds to visited list if we continue to move
		
		for (BoardCell location: start.getAdjList()) { // Checks all paths and continues those available
			if (!visited.contains(location) && (!location.getOccupied() || (location.getInitial() != 'W' && location.getInitial() != roomStart))) {
				this.calcTargets(location, distance + 1);
			}
		}
		visited.remove(start); // remove from visited list so paths don't get confused
	}
	
	public void loadConfigFiles() throws BadConfigFormatException{ // Loads the config files
		this.loadSetupConfig();
		this.loadLayoutConfig();
	}
	
	public void loadSetupConfig() throws BadConfigFormatException { // Loads the setup configuration
		try {
			deck = new ArrayList<Card>();
			loadedCards = false;
			int weaponCount = 0;
			roomMap = new HashMap<Character, Room>();
			File setup = new File(setupConfigFile); // correctly reads in the data as a processable format 
			Scanner reader = new Scanner(setup);
			while (reader.hasNextLine()) {
			 	String data = reader.nextLine();
			 	String[] dataArray = data.split(", ");
			 	if (dataArray[0].equals("Room")) { // Adds lines that need to be added to the list of rooms
			 		deck.add(new Card(dataArray[1], CardType.ROOM));
			 		roomMap.put(dataArray[2].charAt(0), new Room(dataArray[1],deck.get(deck.size()-1)));
			 		
			 	} else if  (dataArray[0].equals("Space")) {
			 		roomMap.put(dataArray[2].charAt(0), new Room(dataArray[1], null));
				} else if (dataArray[0].equals("Human")) {
			 		humans.add(new HumanPlayer(dataArray[1], new Color(Integer.parseInt(dataArray[3]), Integer.parseInt(dataArray[4]), Integer.parseInt(dataArray[5])), Integer.parseInt(dataArray[6]), Integer.parseInt(dataArray[7])));
			 		deck.add(new Card(dataArray[1], CardType.PERSON));
			 	} else if (dataArray[0].equals("Computer")) {
			 		computers.add(new ComputerPlayer(dataArray[1], new Color(Integer.parseInt(dataArray[3]), Integer.parseInt(dataArray[4]), Integer.parseInt(dataArray[5])), Integer.parseInt(dataArray[6]), Integer.parseInt(dataArray[7])));
			 		deck.add(new Card(dataArray[1], CardType.PERSON));
			 	} else if (dataArray[0].equals("Weapon")) { 
			 		weapons[weaponCount++] = dataArray[1];
			 		deck.add(new Card(dataArray[1], CardType.WEAPON));
			 		loadedCards = true;
			 	}
			 	else if (data.charAt(0) != '/') throw new BadConfigFormatException(); // Throws a BadConfigFormatException if there is something wrong with the setup file		
			}
			reader.close();
		} catch (FileNotFoundException e) { // Checks if unable to locate the config file
			System.out.println("Unable to find the config file");
			e.printStackTrace();
		}
	}
	
	public void loadLayoutConfig() throws BadConfigFormatException { // Loads the layout configuration
		try {
			File layout = new File(layoutConfigFile); // Reads in the data from file
			Scanner reader = new Scanner(layout);
			List<String> dataList = new ArrayList<String>();
			while (reader.hasNextLine()) dataList.add(reader.nextLine()); // Reads the data into strings for each row
			reader.close();
			
			numRows = dataList.size(); // determines the number of rows
			String[][] dataArray = new String[numRows][]; // converts the data into a processable format
			for (int i = 0; i < numRows; i++) {
				dataArray[i] = dataList.get(i).split(",");
				if (dataArray[i].length != dataArray[0].length) throw new BadConfigFormatException();
			}
			
			numColumns = dataArray[0].length;
			grid = new BoardCell[numRows][numColumns]; // Assigns all of the cells to the grid
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numColumns; j++) {
					if (!(roomMap.containsKey(dataArray[i][j].charAt(0))))  throw new BadConfigFormatException(); // checks that the character exists
					grid[i][j] = new BoardCell(i,j,dataArray[i][j]); // Adds board cell to grid
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find the layout file");
			e.printStackTrace();
		}
	}
	
	public Boolean checkAccusation(Solution accusation) {
		return solution.matches(accusation);
	}
	
	public Card handleSuggestion(Solution suggestion) {
		for (int i = 1; i < 6; i++) {
			if (currentPlayer((turn+i)%6).disproveSuggestion(suggestion) != null) {
				return currentPlayer((turn+i)%6).disproveSuggestion(suggestion);
			}
		}
		return null;
	}
	
	public Player currentPlayer(int turn) {
		if (turn < humans.size()) {
			return humans.get(turn);
		}
		turn -= humans.size();
		return computers.get(turn);
	}
	
	public void nextTurn() {
		turn = (turn+1) % 6;
	}
	
	public Boolean inBounds(int row, int col) { // Checks if location is in Bounds
		return (row >= 0 && row < numRows && col >= 0 && col < numColumns);
	}
	
	public void setConfigFiles(String layout, String info) { // Sets which files are used for the board configuration
		layoutConfigFile = layout;
		setupConfigFile = info;
	}
	
	public Room getRoom(char room) { // gets the room for a given character
		return roomMap.get(room);
	}
	 
	public Room getRoom(BoardCell room) { // gets the room for a given cell
		return roomMap.get(room.getInitial());
	}
	
	public Set<BoardCell> getAdjList(int row, int col) {	
		return grid[row][col].getAdjList();
	}
	
	public int getRoomCount() { // Returns the amount of rooms
		return roomMap.size() - 2; // Compensates for halls and unused spaces
	}
	
	public Set<BoardCell> getTargets() { // gets the targets last calculated
		return targets;
	}
	
	public int getNumRows() { // returns the number of rows
		return numRows;
	}
	
	public int getNumColumns() { // returns the number of columns
		return numColumns;
	}
	
	public BoardCell getCell(int row, int col) { // returns the specified cell
		return grid[row][col];
	}
	
	public Solution getSolution() { // returns the solution cards
		return solution;
	}
	
	public ArrayList<HumanPlayer> getHumans() { // returns the humans
		return humans;
	}
	
	public ArrayList<ComputerPlayer> getComputers() { // returns the computer
		return computers;
	}
}