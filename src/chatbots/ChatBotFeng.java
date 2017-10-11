package chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import colleges.CollegeParser;
import grammar.GreetingsDictionary;
import grammar.SentenceParser;
import bodyParts.Brain;
/*
	TODO random responses for one college only and others
		 decide between exact college names or allow more variance
*/

/**
 * A program to carry on conversations about College Data
 * This version:
 * @author Alex Feng
 * @version October 2017
 */
public class ChatBotFeng extends ChatBotBase implements Emotion
{
	// College csv file column index
	private static final int SAT = 11;
	private static final int ACT = 12;
	private static final int ADMISSION_RATE = 9;
	private static final int CITY = 1;
	private static final int STATE = 2;
	private static final int ZIP_CODE = 3;
	private static final int FOUR_YEAR_COST = 13;
	
	private Brain brain = new Brain();

	public ChatBotFeng() throws IOException {
		brain.addMemoryType("colleges");
		brain.addMemoryType("action");
	}
	
	public String getGreeting()
	{
		return "Hey! The name's Alex, and I'm here to help you get any information about a particular college.\n"
				+ "Ask away!";
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
		if (colleges.size() > 0) {
			brain.addToMemory("colleges", colleges.get(0));
		}
		
		String response = "";
		GreetingsDictionary greetingsDict = new GreetingsDictionary();
		if (greetingsDict.contains(statement.toLowerCase())) {
			response = "Nice to meet you!";
		}
		else if (findKeyword(statement, "what") != -1 ||
			findKeyword(statement, "whats") != -1 ||
			findKeyword(statement, "how") != -1 ||
			findKeyword(statement, "hows") != -1) {
			// What questions
			response = parseWhatHowQuestion(statement, colleges);
		} else if (findKeyword(statement, "where") != -1 ||
			findKeyword(statement, "wheres") != -1) {
			//Where questions
			response = parseWhereQuestion(statement, colleges);
		} else if (findKeyword(statement, "can") != -1) {
			response = "Can I? Of course I can!";
		} 	else {
			response = genericResponse(statement);
		}
		
		return response;
	}
	
	private String parseWhatHowQuestion(String statement, ArrayList<String> colleges) throws IOException {
		String response = "";
		if (colleges.size() <= 0 && brain.getMemory("colleges").size() > 0) {
		// if there aren't any colleges in the sentence, get the last college in memory
			String lastCollege = brain.getLastMemory("colleges");
			// ArrayList of only the last college
			colleges = new ArrayList<>();
			colleges.add(lastCollege);
		}
		if (colleges.size() > 0 || brain.getMemory("colleges").size() > 0) {
			if (userRequestsSATScore(statement)) {
				brain.addToMemory("action", "what sat");
				response = getSATResponse(statement, colleges);
			} 
			else if (userRequestsACTScore(statement)) {
				brain.addToMemory("action", "what act");
				response = getACTResponse(statement, colleges);
			} 
			else if (userRequestsAdmissionRate(statement)) {
				brain.addToMemory("action", "what admission rate");
				response = getAdmissionRateResponse(statement, colleges);
			} 
			else if (userRequestsCost(statement)) {
				brain.addToMemory("action", "what tuition");
				response = getCostResponse(statement, colleges);
			} 
			else if (userRequestsGPA(statement)) {
				brain.addToMemory("action", "what gpa");
				response = "Sorry that data is currently unavailable";
			} 
			else if (userRequestsLocation(statement)) {
				brain.addToMemory("action", "where is");
				response = getLocationResponse(statement, colleges);
			}
			else if (userQueriesCollege(statement, colleges)) {
				brain.addToMemory("action", "how is");
				response = getOpinion(statement, colleges);
			} 
			else if (findKeyword(statement, "about") != -1 && 
					brain.getMemory("action").size() > 0 && 
					SentenceParser.getWords(statement).size() <= 3 + colleges.get(0).length()) {
				// if contains the about keyword and there was a previous question asked
				// for questions like 'What about Yale University?'
				// places previous question at the start
				statement = brain.getLastMemory("action") + " " + statement;
				// retry the parsing
				return parseWhatHowQuestion(statement, colleges);
			} else {
				System.out.println("Sorry, I didn't understand.");
			}
		} else {
			// no colleges found
			response = caseOfCollegeNotRecognized(statement);
		}
		return response;
	}
	
	private String parseWhereQuestion(String statement, ArrayList<String> colleges) throws IOException {
		String response = "";
		if (colleges.size() <= 0 && brain.getMemory("colleges").size() > 0) {
			// if there aren't any colleges in the sentence, get the last college in memory
				String lastCollege = brain.getLastMemory("colleges");
				// ArrayList of only the last college
				colleges = new ArrayList<>();
				colleges.add(lastCollege);
		}
		if (colleges.size() > 0) {
			if (userRequestsLocation(statement)) {
				brain.addToMemory("action", "where is");
				response = getLocationResponse(statement, colleges);
			} else {
				System.out.println("Sorry, I didn't understand.");
			}
		} else {
			// no colleges found
			response = caseOfCollegeNotRecognized(statement);
		}
		return response;
	}
	
	
	private String caseOfCollegeNotRecognized(String statement) {
		String response = "";
		if (findKeyword(statement.toLowerCase(), "college") != -1 ||
			findKeyword(statement.toLowerCase(), "university") != -1) {
			response = "Sorry, I do not recognize that college (Proper capitalization please!)";
		} else {
			response = "Please do not talk to me about matters not related to college!";
		}
		return response;
	}
	
	private boolean userRequestsAdmissionRate(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "admission") != -1 && findKeyword(statement, "rate") != -1;
	}

	private boolean userRequestsSATScore(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "sat") != -1;
	}
	
	private boolean userRequestsACTScore(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "act") != -1;
	}
	
	private boolean userRequestsGPA(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "gpa") != -1;
	}
	
	private boolean userRequestsLocation(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "where") != -1 && 
				findKeyword(statement, "is") != -1 || findKeyword(statement, "location") != -1;
	}
	
	private boolean userRequestsCost(String statement) {
		statement = statement.toLowerCase();
		return (findKeyword(statement, "much") != -1 &&
				findKeyword(statement, "is") != -1) ||
				findKeyword(statement, "tuition") != -1 ||
				findKeyword(statement, "cost") != -1;
	}
	
	private boolean userQueriesCollege(String statement, ArrayList<String> colleges) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "is") != -1 || findKeyword(statement, "about") != -1  && 
				SentenceParser.getWords(statement).size() <= 3 + colleges.get(0).length();
	}
	
	private String getSATResponse(String statement, ArrayList<String> colleges) throws IOException {
		// only 1 college!
		if (colleges.size() > 1) {
			return "I can only give an SAT score for one college at a time.";
		}
		// will work on multiple colleges later
		for (String college : colleges) {
			String avgSATScore = getAvgSATScore(college);
			if (avgSATScore == null) {
				return "Sorry, I don't have any data on " + college + ".";
			} else {
				return "The average SAT score for " + college + " is " + avgSATScore + ".";
			}
		}
		return "Huh?"; // This should never happen
	}
	
	private String getAvgSATScore(String college) throws IOException {
		String satScoreStr = CollegeParser.getCollegeData(college, SAT);
		if (satScoreStr == null) {
			return null;
		} else {
			return satScoreStr;
		}
	}
	
	private String getACTResponse(String statement, ArrayList<String> colleges) throws IOException {
		// only 1 college!
		if (colleges.size() > 1) {
			return "I can only give an SAT score for one college at a time.";
		}
		// will work on multiple colleges later
		for (String college : colleges) {
			String avgACTScore = getAvgACTScore(college);
			if (avgACTScore == null) {
				return "Sorry, I don't have any data on " + college;
			} else {
				return "The average ACT score for " + college + " is " + avgACTScore + ".";
			}
		}
		return "Huh?"; // This should never happen
	}
	
	private String getAvgACTScore(String college) throws IOException {
		String satScoreStr = CollegeParser.getCollegeData(college, ACT);
		if (satScoreStr == null) {
			return null;
		} else {
			return satScoreStr;
		}
	}
	
	private String getAdmissionRateResponse(String statement, ArrayList<String> colleges) throws IOException {
		// only 1 college!
		if (colleges.size() > 1) {
			return "One college at a time please!";
		}
		// will work on multiple colleges later
		for (String college : colleges) {
			double admissionRate = getAdmissionRate(college);
			if (admissionRate == -1) {
				return "Sorry, I don't have any data on " + college  + ".";
			} else {
				return "The admission rate for " + college + " is " + (admissionRate * 100) + "%.";
			}
		}
		return "Huh?"; // This should never happen
	}
	
	private double getAdmissionRate(String college) throws IOException {
		String admissionRateStr = CollegeParser.getCollegeData(college, ADMISSION_RATE);
		if (admissionRateStr == null) {
			return -1;
		} else {
			return Double.parseDouble(admissionRateStr);
		}
	}
	
	private String getLocationResponse(String statement, ArrayList<String> colleges) throws IOException {
		// only 1 college!
		if (colleges.size() > 1) {
			// emotion ?
			return "One college at a time please!";
		}
		// will work on multiple colleges later
		for (String college : colleges) {
			String location = getLocation(college);
			if (location == null) {
				return "Sorry, I don't have any data on " + college  + ".";
			} else {
				return "The location of " + college + " is " + location  + ".";
			}
		}
		return "Huh?"; // This should never happen
	}
	
	private String getLocation(String college) throws IOException {
		String city = CollegeParser.getCollegeData(college, CITY);
		String state = CollegeParser.getCollegeData(college, STATE);
		String zip = CollegeParser.getCollegeData(college, ZIP_CODE);
		if (city == null) {
			return null;
		} else {
			return city + ", " + state + " " + zip;
		}
	}
	
	private String getCostResponse(String statement, ArrayList<String> colleges) throws IOException {
		// only 1 college!
		if (colleges.size() > 1) {
			// emotion ?
			return "One college at a time please!";
		}
		// will work on multiple colleges later
		for (String college : colleges) {
			String cost = getCost(college);
			if (cost == null) {
				return "Sorry, I don't have any data on " + college  + ".";
			} else {
				return "The four year cost of " + college + " is $" + cost  + ".";
			}
		}
		return "Huh?"; // This should never happen
	}
	
	private String getCost(String college) throws IOException {
		String cost = CollegeParser.getCollegeData(college, FOUR_YEAR_COST);
		if (cost == null) {
			return null;
		} else {
			return cost;
		}
	}
	
	private String getOpinion(String statement, ArrayList<String> colleges) throws IOException {
		// only 1 college!
		if (colleges.size() > 1) {
			// emotion ?
			return "One college at a time please!";
		}
		// will work on multiple colleges later
		for (String college : colleges) {
			String satScoreStr = getAvgSATScore(college);
			if (satScoreStr == null) {
				return "I would not recommend going here";
			}
			int satScore = Integer.parseInt(satScoreStr);
			if (satScore >= 1400) {
				return college + " is an excellent college to go to!";
			} else if (satScore >= 1200) {
				return college + " is a good college to go to!";
			} else {
				return college + " is not that bad of a college.";
			}
		}
		return "Huh?"; // This should never happen
	}
}
