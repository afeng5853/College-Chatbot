package chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
public class ChatBotFeng extends ChatBotLevin
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
		brain.addMemoryType("SAT");
	}
	
	/**
	 * Returns the bot's standard greeting
	 * @return returns the bot's standard greeting
	 */
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
		// If the user wants any of the other chatbots
		else if (findKeyword(statement, "zhou") != -1) {
			System.out.println("Okay, bye!");
			response = "~CALLZHOU";
		}
		else if (findKeyword(statement, "cheung") != -1) {
			System.out.println("Okay, bye!");
			response = "~CALLCHEUNG";
		}
		else if (findKeyword(statement, "chan") != -1) {
			System.out.println("Okay, bye!");
			response = "~CALLCHAN";
		}
		// if the user mentions anything related to majors, refer to chatbot chan
		else if (findKeyword(statement, "chan") != -1 ||findKeyword(statement, "major") != -1) {
			System.out.println("Sorry, I don't know much about college majors but my friend Chatbon Chan does! I'll refer him to you. Goodbye!");
			response = "~CALLCHAN";
		}
		// if the user mentions anything related to financial aid, refer to chatbot cheung
		else if (findKeyword(statement, "cheung") != -1 || findKeyword(statement, "financial aid") != -1) {
			System.out.println("Sorry, I don't know much about financial aid but my friend Chatbon Cheung does! I'll refer him to you. Goodbye!");
			response = "~CALLCHEUNG";
		}
		else if (findKeyword(statement, "undo") != -1 ||
				findKeyword(statement, "mistake") != -1 ||
				findKeyword(statement, "typo") != -1) {
			String lastAction = brain.getLastMemory("action");
			// Should go requires SAT score response, so if a mistake was made, delete the last memory
			if (lastAction.equals("should go") && !brain.getMemory("SAT").isEmpty()) {
				brain.deleteLastMemory("SAT");
				response = "Okay, I've completely forgotten about your score. What is your question?";
			} else {
				// anger++
				response = "You are an idiot!";
			}
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
			// can questions
			if (findKeyword(statement, "i") != -1) {
				// if user asks about himself
				response = "Can you? Of course you can!";
			} else {
				response = "Can I? Of course I can!";
			}
		} else if (findKeyword(statement, "should") != -1) {
			response = parseShouldQuestion(statement, colleges);
		} else {
			response = genericResponse(statement);
		}
		
		return response;
	}
	
	/**
	 * Gives a response based on the user's what/how question
	 * 
	 * @param statement the user statement
	 * @param colleges the list of colleges in the user statement
	 * @return a response based on the rules given for a what/how question
	 * @throws IOException 
	 */
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
			else if (findKeyword(statement, "about") != -1 && 
					brain.getMemory("action").size() > 0 && 
					SentenceParser.getWords(statement).size() <= 3 + colleges.get(0).length()) {
				// if contains the about keyword and there was a previous question asked
				// for questions like 'What about Yale University?'
				// places previous question at the start
				statement = brain.getLastMemory("action") + " " + statement;
				// retry the parsing
				// try to get the question word by getting the first word
				String firstWord = statement.split(" ")[0];
				if (firstWord.equals("what") || firstWord.equals("how")) {
					return parseWhatHowQuestion(statement, colleges);
				} else if (firstWord.equals("where")) {
					return parseWhereQuestion(statement, colleges);
				}
				else if (firstWord.equals("should")) {
					return parseShouldQuestion(statement, colleges);
				} else {
					//this should never happen
					return "Huh?";
				}
			}
			else if (userQueriesCollege(statement, colleges)) {
				brain.addToMemory("action", "how is");
				response = getOpinion(statement, colleges);
			} 
			else {
				System.out.println("Sorry, I didn't understand.");
			}
		} else {
			// no colleges found
			response = caseOfCollegeNotRecognized(statement);
		}
		return response;
	}
	
	/**
	 * Gives a response based on the user's where question
	 * 
	 * @param statement the user statement
	 * @param colleges the list of colleges in the user statement
	 * @return a response based on the rules given for a where question
	 * @throws IOException 
	 */
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
	
	/**
	 * Gives a response based on the user's should question
	 * 
	 * @param statement the user statement
	 * @param colleges the list of colleges in the user statement
	 * @return a response based on the rules given for a should question
	 * @throws IOException 
	 */
	private String parseShouldQuestion(String statement, ArrayList<String> colleges) throws IOException {
		String response = "";
		if (colleges.size() <= 0 && brain.getMemory("colleges").size() > 0) {
			// if there aren't any colleges in the sentence, get the last college in memory
				String lastCollege = brain.getLastMemory("colleges");
				// ArrayList of only the last college
				colleges = new ArrayList<>();
				colleges.add(lastCollege);
		}
		if (colleges.size() > 0) {
			if (userRequestsShouldGo(statement)) {
				brain.addToMemory("action", "should go");
				response = getShouldGoResponse(statement, colleges);
			} else {
				System.out.println("Sorry, I didn't understand.");
			}
		} else {
			// no colleges found
			response = caseOfCollegeNotRecognized(statement);
		}
		return response;
	}
	
	/**
	 * Gives a response based on the user's lack of colleges in the statement
	 * 
	 * @param statement the user statement
	 * @return a response based on the rules given for a lack of colleges in the statement
	 */
	private String caseOfCollegeNotRecognized(String statement) {
		String response = "";
		// if the statement is related to college/university
		if (findKeyword(statement.toLowerCase(), "college") != -1 ||
			findKeyword(statement.toLowerCase(), "university") != -1) {
			response = "Sorry, I do not recognize that college (Proper capitalization please!)";
		} else {
			response = "Please do not talk to me about matters not related to college!";
		}
		return response;
	}
	
	/* Boolean functions for detecting keywords in the statement for a certain action */
	
	/**
	 * Returns true/false if the user requested a college's admission rate based on keywords
	 * @param statement the user statement
	 * @return returns true/false if the user requested a college's admission rate
	 */
	private boolean userRequestsAdmissionRate(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "admission") != -1 && findKeyword(statement, "rate") != -1;
	}

	/**
	 * Returns true/false if the user requested a college's average SAT score based on keywords
	 * @param statement the user statement
	 * @return returns true/false if the user requested a college's average SAT score
	 */
	private boolean userRequestsSATScore(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "sat") != -1;
	}
	
	/**
	 * Returns true/false if the user requested a college's average ACT score based on keywords
	 * @param statement the user statement
	 * @return returns true/false if the user requested a college's average ACT score
	 */
	private boolean userRequestsACTScore(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "act") != -1;
	}
	
	/**
	 * Returns true/false if the user requested a college's average GPA based on keywords
	 * @param statement the user statement
	 * @return returns true/false if the user requested a college's average GPA
	 */
	private boolean userRequestsGPA(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "gpa") != -1;
	}
	
	/**
	 * Returns true/false if the user requested a college's location based on keywords
	 * @param statement the user statement
	 * @return returns true/false if the user requested a college's location
	 */
	private boolean userRequestsLocation(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "where") != -1 && 
				findKeyword(statement, "is") != -1 || findKeyword(statement, "location") != -1;
	}
	
	/**
	 * Returns true/false if the user requested a college's average cost based on keywords
	 * @param statement the user statement
	 * @return returns true/false if the user requested a college's average cost
	 */
	private boolean userRequestsCost(String statement) {
		statement = statement.toLowerCase();
		return (findKeyword(statement, "much") != -1 &&
				findKeyword(statement, "is") != -1) ||
				findKeyword(statement, "tuition") != -1 ||
				findKeyword(statement, "cost") != -1;
	}
	
	/**
	 * Returns true/false if the user requested whether or not they should go to college based on keywords
	 * @param statement the user statement
	 * @return returns true/false if the user requested whether or not they should go to college
	 */
	private boolean userRequestsShouldGo(String statement) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "go") != -1;
	}
	
	/**
	 * Returns true/false if the user asks about a college based on keywords
	 * @param statement the user statement
	 * @return Returns true/false if the user asks about a college
	 */
	private boolean userQueriesCollege(String statement, ArrayList<String> colleges) {
		statement = statement.toLowerCase();
		return findKeyword(statement, "is") != -1 || findKeyword(statement, "about") != -1  && 
				SentenceParser.getWords(statement).size() <= 3 + colleges.get(0).length();
	}
	
	/**
	 * Returns a response based on SAT college scores
	 * @param statement the user statement
	 * @param colleges the list of colleges in the statement
	 * @return Returns a response based on SAT college scores
	 */
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
	
	/**
	 * Get sat score of a college
	 * @param college the college in the statement
	 * @return Returns the sat score as a String
	 */
	private String getAvgSATScore(String college) throws IOException {
		String satScoreStr = CollegeParser.getCollegeData(college, SAT);
		if (satScoreStr == null) {
			return null;
		} else {
			return satScoreStr;
		}
	}
	
	/**
	 * Returns a response based on ACT college scores
	 * @param statement the user statement
	 * @param colleges the list of colleges in the statement
	 * @return Returns a response based on ACT college scores
	 */
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
	
	/**
	 * Get ACT score of a college
	 * @param college the college in the statement
	 * @return Returns the ACT score as a String
	 */
	private String getAvgACTScore(String college) throws IOException {
		String satScoreStr = CollegeParser.getCollegeData(college, ACT);
		if (satScoreStr == null) {
			return null;
		} else {
			return satScoreStr;
		}
	}
	
	/**
	 * Returns a response based on college admission rates
	 * @param statement the user statement
	 * @param colleges the list of colleges in the statement
	 * @return Returns a response based on college admission rates
	 */
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
	
	/**
	 * Get the admission rate score of a college
	 * @param college the college in the statement
	 * @return Returns a double of the admission rate
	 */
	private double getAdmissionRate(String college) throws IOException {
		String admissionRateStr = CollegeParser.getCollegeData(college, ADMISSION_RATE);
		if (admissionRateStr == null) {
			return -1;
		} else {
			return Double.parseDouble(admissionRateStr);
		}
	}
	
	/**
	 * Returns a response based on the college's location
	 * @param statement the user statement
	 * @param colleges the list of colleges in the statement
	 * @return Returns a response based on the college's location
	 */
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
	
	/**
	 * Gets the location of a college
	 * @param college the college in the statement
	 * @return Returns a string of the location of the college
	 */
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
	
	/**
	 * Returns a response based on the college's cost
	 * @param statement the user statement
	 * @param colleges the list of colleges in the statement
	 * @return Returns a response based on the college's cost
	 */
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
	
	/**
	 * Returns a response based on the rules of a should go statement
	 * @param statement the user statement
	 * @param colleges the list of colleges in the statement
	 * @return Returns a response based on the rules of a should go statement
	 */
	private String getShouldGoResponse(String statement, ArrayList<String> colleges) throws IOException {
		// only 1 college!
		if (colleges.size() > 1) {
			// emotion ?
			return "One college at a time please!";
		}
		Scanner input = new Scanner(System.in);
		// will work on multiple colleges later
		for (String college : colleges) {
			boolean hasSATScore = brain.getMemory("SAT").size() > 0;
			String response = "";
			if (!hasSATScore) {
				System.out.println("What is your SAT score out of 1600?");
				response = input.nextLine();
			}
			// If the person says he didn't take the SAT yet and hasn't reported an SAT score to the bot
			if (!hasSATScore && (findKeyword(response, "didnt") != -1 
					|| findKeyword(response, "didn't") != -1
					|| findKeyword(response, "did not") != -1)) {
				System.out.println("That's okay, if you want to take a mock SAT exam, you can ask Chatbot Zhou!\n"
									+ "Would you like to take one?");
				response = input.nextLine();
				// Yes or no response to take SAT from person
				if (findKeyword(response, "yes") != -1 
						|| findKeyword(response, "would like to") != -1
						|| findKeyword(response, "sure") != -1) {
					return "~CALLZHOU";
				} else if (findKeyword(response, "no") != -1) {
					return "Okay.";
				} else {
					return "Huh?";
				}
			} else {
				while (true) {
					int score = 0;
					try {
						if (!hasSATScore) {
							score = Integer.parseInt(response);
							brain.addToMemory("SAT", Integer.toString(score));
						} else {
							score = Integer.parseInt(brain.getLastMemory("SAT"));
						}
					} catch (NumberFormatException e) {
						System.out.println("Please enter a valid number.");
						response = input.nextLine();
					}
					int collegeSATScore = Integer.parseInt(getAvgSATScore(college));
					if (collegeSATScore > 1600) {
						// anger++
						brain.deleteLastMemory("SAT");
						return "Are you trying to lie to me or is this just a misunderstanding?";
					} else if (Math.abs(collegeSATScore - score) < 100 || score > collegeSATScore) {
						return "You have a chance to get in!";
					} else {
						return "Don't even try!";
					}
					
				}
			}
		}
		return "Huh?"; // This should never happen
	}
	
	/**
	 * Gets the cost of a college
	 * @param college the college in the statement
	 * @return Returns a string of the cost of the college
	 */
	private String getCost(String college) throws IOException {
		String cost = CollegeParser.getCollegeData(college, FOUR_YEAR_COST);
		if (cost == null) {
			return null;
		} else {
			return cost;
		}
	}
	
	/**
	 * Gets the opinion of a college based on it's SAT score
	 * @param statement the user statement
	 * @param colleges the list of colleges in the statement
	 * @return Returns the opinion of a college based on it's SAT score
	 */
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
