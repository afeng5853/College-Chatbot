package Chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import colleges.CollegeParser;
import grammar.SentenceParser;
import com.opencsv.CSVReader;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Mr. Levin
 * @version September 2017
 */
public class ChatBotFeng extends ChatBotLevin implements Emotion
{
	public ChatBotFeng() {

	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String getResponse(String statement) throws FileNotFoundException, IOException
	{
		CollegeParser collegeParser = new CollegeParser(statement);
		ArrayList<String> colleges = collegeParser.getColleges();
		String response = "";
		
		if (userQuestions(statement)) {
			if (colleges.size() > 0) {
				if (userRequestsSATScore(statement) != -1) {
					getSATResponse(statement, colleges);
				} else if (userRequestsGPA(statement) != -1) {
					
				}
			}
		}
		
		
		return response;
	}
	
	private int userRequestsSATScore(String statement) {
		return findKeyword(statement, "SAT");
	}
	
	private int userRequestsGPA(String statement) {
		return findKeyword(statement, "GPA");
	}
	
	private String getSATResponse(String statement, ArrayList<String> colleges) {
		// only 1 college!
		if (colleges.size() > 1) {
			return "I can only give an SAT score for one college at a time";
		}
		// will work on multiple colleges later
		for (String college : colleges) {
			int avgSatScore = getAvgSATScore(college);
			return "The average SAT score for " + college + " is ":
		}
	}
	
	private int getAvgSATScore(String college) {
		//college column d, sat score column BH
		CSVReader csvReader = new CSVReader(new FileReader("src/colleges/college data.csv"), ",");
		String [] nextLine;
		for (String field: nextLine) {
		      if (field.matches(searchWord)) {
		         // matched word...
		      }
		   }
	}
}
