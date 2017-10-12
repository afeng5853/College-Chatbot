package chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import colleges.CollegeParser;
import grammar.GreetingsDictionary;
import util.TextFileReader;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Thomas Zhou
 * @version September 2017
 */

// u should add help keyword -alex
public class ChatBotZhou extends ChatBotLevin
{
	private static int STATE = 0;
	private static final int SAT = 1;
	private String satCategory = "";
	private char answer;
	private int testNum = 1;
	public String getGreeting()
	{
		return "Hello! Would you like to take practice for reading, grammar, or math?";
	}
	//t
	public String getResponse(String statement) throws FileNotFoundException, IOException
	{
		String response = statement;
		GreetingsDictionary greetingsDict = new GreetingsDictionary();
		if (STATE == SAT)
		{
			int score = 0;
			int questionCount = 0;
			Scanner input = new Scanner(System.in);
			while (STATE == SAT)
			{
				String question = printQuestion(testNum, satCategory);
				if (question.equals("")) {
					STATE = 0;
					testNum = 1;
					double percentage = ((double) score / (double) questionCount) * 100;
					System.out.println("You got " + percentage + "% correct");
					if (percentage >= 90) {
						System.out.println("Good job!");
					} else if (percentage >= 70) {
						System.out.println("Decent!");
					} else {
						System.out.println("You did really bad!");
					}
					System.out.println("Would you like to take a practice math, grammar, or reading exam?");
					break;
				}
				System.out.println(question);
				boolean out = false;
				while (true) {
					String userInput = input.nextLine();
					if (findKeyword(userInput, "stop") != -1 || 
							findKeyword(userInput, "pause") != -1 ||
							findKeyword(userInput, "don't") != -1 ||
							findKeyword(userInput, "do not") != -1 ||
							findKeyword(userInput, "dont") != -1) {
						System.out.println("Okay, would you like to take reading or math or grammar??");
						STATE = 0;
						testNum = 1;
						out = true;
						break;
					}
					if (userInput.toLowerCase().equals("a") ||
							userInput.toLowerCase().equals("b") ||
							userInput.toLowerCase().equals("c") ||
							userInput.toLowerCase().equals("d")) {
						if (userInput.toUpperCase().charAt(0) == answer) {
							System.out.println("You got it correct!");
							score++;
						} else {
							System.out.println("The correct answer was: " + answer);
						}
						questionCount++;
						break;
					} else {
						System.out.println("Please type the corresponding later");
					}
				}
				if (out) {
					break;
				}
				testNum++;
			}
		}
		else if (findKeyword(statement, "grammar") != -1) {
			STATE = SAT;
			satCategory = "grammar";
			response = "Okay, any last words before you start?";
		} 
		else if (findKeyword(statement, "reading") != -1) {
			STATE = SAT;
			satCategory = "reading";
			response = "Okay, any last words before you start?";
		} 
		else if (findKeyword(statement, "math") != -1) {
			STATE = SAT;
			satCategory = "math1";
			response = "Okay, any last words before you start?";
		}
		else if (greetingsDict.contains(statement.toLowerCase())) {
			response = "Nice to meet you!";
		} 
		// If the user wants any of the other chatbots
		else if (findKeyword(statement, "feng") != -1) {
			System.out.println("Okay, bye!");
			response = "~CALLFENG";
		}
		else if (findKeyword(statement, "cheung") != -1) {
			System.out.println("Okay, bye!");
			response = "~CALLCHEUNG";
		}
		else if (findKeyword(statement, "chan") != -1) {
			System.out.println("Okay, bye!");
			response = "~CALLCHAN";
		} 
		else if (findKeyword(statement, "chan") != -1) {
			System.out.println("Okay, bye!");
			response = "~CALLCHAN";
		} 
		else if (findKeyword(statement, "no") != -1) {
			String[] bots = new String[] {"FENG", "CHEUNG", "CHAN"};
			Random r = new Random();
			int botChoice = r.nextInt(bots.length);
			String botChoiceStr = bots[botChoice];
			System.out.println("Okay then, go talk to Chatbot "  + botChoiceStr);
			response = "~CALL" + botChoiceStr;
		} 
		else {
			System.out.println("I don't know what you said");
		}
		return response;
	}
	
	public String printQuestion(int number, String category) throws FileNotFoundException, IOException
	{
		TextFileReader text = new TextFileReader("src/sat problems/" + category + ".txt");
		ArrayList<String> textfile = text.getDict();
		String response = "";
		boolean isQuestion = false;
		for (int i = 0; i < textfile.size(); i++)
		{
			String line = textfile.get(i).trim();
			if (line.equals("") && isQuestion)
			{
				return response;
			}
			if (isQuestion)
			{
				response += line + "\n";
			}
			
			//Second part of condition is still broken
			if (line.length() > 0 && (!Character.isAlphabetic(line.charAt(0)) && Integer.parseInt(Character.toString(line.charAt(0))) == number))
			{
				isQuestion = true;
				answer = line.charAt(3);
			}
		}
		return "";
	}
	 
	public String printL(int num) throws FileNotFoundException, IOException
	{
		TextFileReader text = new TextFileReader("src/sat problems/grammar.txt");
		ArrayList<String> textfile = text.getDict();
		return textfile.get(6);
	}
}
/*
String response = "";

CollegeParser  = new CollegeParser(statement);
ArrayList<String> colleges = test.getColleges();
System.out.println(colleges);
return response;
*/