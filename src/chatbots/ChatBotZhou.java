package chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import colleges.CollegeParser;
import grammar.GreetingsDictionary;
import util.TextFileReader;
import answers.answers;
/**
 * SAT Practice Bot
 * This specific chatbot will give the user SAT practice questions for the writing and math sections.
 * @author Thomas Zhou
 * @version 10.11
 */

/*
 * Implement emotion
 * greetingsDict.contains(statement.toLowerCase()) should work for punctuation
 * After 5 questions finish, reset , score and sat estimate
 * replace answer arrays with hashmaps possibly
 * fix initialized variables
 */
public class ChatBotZhou extends ChatBotLevin
{
	private static String SECTION = "none";
	private static boolean SAT = false; //Variable that tracks if testing is in progress
	private static final String SECTION1 = "reading";
	private static final String SECTION2 = "grammar";
	private static final String SECTION3 = "math1";
	private static final String SECTION4 = "math2";
	int testNum = 1;
	int score = 0;
	
	String[] reading = {"B","B","C","D","B"};
	String[] grammar = {"D","B","A","C","B"}; 
	String[] math1 = {"D","C","B","A","B"}; 
	String[] math2 = {"D","B","D","B","107"};  
	
	public String getGreeting()
	{
		return "Hello! I will help you in preparing for the SAT. Would you like to practice?";
	}
	
	public String getResponse(String statement) throws FileNotFoundException, IOException
	{
		String response = genericResponse(statement);
		GreetingsDictionary greetingsDict = new GreetingsDictionary();
		if (statement.length() == 0)
		{
			response = "Please enter something.";
		}
		else if (greetingsDict.contains(statement.toLowerCase())) {
			response = "Nice to meet you!";
		}
		else if (SAT)
		{
			if (SECTION == SECTION1)
			{
				response = gradeQuestion(statement, reading);
			}
			if (SECTION == SECTION2)
			{
				response = gradeQuestion(statement, grammar);
			}
			else if (SECTION == SECTION3)
			{
				response = gradeQuestion(statement, math1);
			}
			if (SECTION == SECTION4)
			{
				response = gradeQuestion(statement, math2);
			}
		}
		else if (findKeyword(statement, "prepare") != -1 || findKeyword(statement, "practice") != -1 || findKeyword(statement, "yes") != -1)
		{
			response = "Which section would you like to work on? For math, please specify if you would like to work on the calculator or no calculator. \n Reading \n Writing/Grammar \n Math";
		}
		else if (findKeyword(statement, "grammar") != -1 || findKeyword(statement, "math") != -1 || findKeyword(statement, "reading") != -1 
				|| findKeyword(statement, "writing") != -1)
		{
			response = startSection(statement);
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
		return response;
	}
	
	public String startSection(String statement) throws FileNotFoundException, IOException
	{
		String response = "";
		if ((findKeyword(statement, "writing") != -1 || findKeyword(statement, "grammar") != -1) && findKeyword(statement, "reading") == -1 && findKeyword(statement, "math") == -1)
		{
			SECTION = SECTION2;
			testNum = 1;
			SAT = true;
			response = printQuestion(testNum, SECTION);
		}
		if ((findKeyword(statement, "writing") == -1 && findKeyword(statement, "grammar") == -1) && findKeyword(statement, "reading") != -1 && findKeyword(statement, "math") == -1)
		{
			SECTION = SECTION1;
			testNum = 1;
			SAT = true;
			response = printQuestion(testNum, SECTION);
		}
		if ((findKeyword(statement, "writing") == -1 && findKeyword(statement, "grammar") == -1) && findKeyword(statement, "reading") == -1 && findKeyword(statement, "math") != -1)
		{
			if (findKeyword(statement, "calculator") == -1)
			{
				response = "Which section would you like to work on? For math, please specify if you would like to work on the calculator or no calculator. \n Reading \n Writing/Grammar \n Math";
			}
			else if ((findKeyword(statement, "no") != -1))
			{
				SECTION = SECTION3;
				testNum = 1;
				SAT = true;
				response = printQuestion(testNum, SECTION);
			}
			else
			{
				SECTION = SECTION4;
				testNum = 1;
				SAT = true;
				response = printQuestion(testNum, SECTION);
			}
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
			String line = textfile.get(i);
			if (line.equals("") && isQuestion)
			{
				return response;
			}
			if (isQuestion)
			{
				response += line + "\n";
			}
			if (line.length() > 0 && Character.isDigit(line.charAt(0)) && Integer.parseInt(Character.toString(line.charAt(0))) == number)
			{
				isQuestion = true;
			}
		}
		return response;
	}
	
	public String gradeQuestion(String statement, String[] answer) throws FileNotFoundException, IOException
	{
		String response = "";
		if (statement.toUpperCase().equals(answer[testNum-1]))
		{
			testNum++;
			if (testNum > 5)
			{
				SAT = false;
				return "You finished! Would you like to work on another section?";
			}
			response = "Correct! \n" + printQuestion(testNum, SECTION);
			score++;
		}
		else
		{
			testNum++;
			if (testNum > 5)
			{
				SAT = false;
				return "You finished! Would you like to work on another section?";
			}
			response = "Incorrect! The correct answer is " + answer[testNum-1] + ". \n" + printQuestion(testNum, SECTION);
		}
		return response; 
	}
}