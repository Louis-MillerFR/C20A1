// Louis Miller CSCI306 Section B

package clueGame;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;


public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() { // Used for when there is an issue with the config format
		super("Bad Config Format thrown");
		try { // Write the error to an errorlog
			File obj = new File("errorlog.txt");
			obj.createNewFile();
			FileWriter writer = new FileWriter("errorlog.txt");
			writer.write("An error occurred during the initialisation of the game due to a bad format.");
			writer.close();
		} catch (IOException e) { // error when trying to write the error
			System.out.println("An error occurred when writing to file");
		}
	}
	
}
