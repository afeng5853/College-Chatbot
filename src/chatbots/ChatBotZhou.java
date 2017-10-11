package chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import colleges.CollegeParser;
import grammar.GreetingsDictionary;
import util.TextFileReader;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Mr. Levin
 * @version September 2017
 */
public class ChatBotZhou extends ChatBotBase
{
	private static int STATE = 1;
	private static final int SAT = 1;
	int testNum = 1;
	public String getGreeting()
	{
		return "Hello! testing 123";
	}
	public String getResponse(String statement) throws FileNotFoundException, IOException
	{
		String response = statement;
		GreetingsDictionary greetingsDict = new GreetingsDictionary();
		if (greetingsDict.contains(statement.toLowerCase())) {
			response = "Nice to meet you!";
		}
		
		else if (STATE == SAT)
		{
			while (STATE == SAT)
			{
				printQuestion(testNum, "grammar");
				testNum++;
			}
		}
		return response;
	}
	
	public String printQuestion(int number, String category) throws FileNotFoundException, IOException
	{
		STATE = SAT;
		TextFileReader text = new TextFileReader("src/sat problems/" + category + ".txt");
		ArrayList<String> textfile = text.getDict();
		String response = "";
		boolean isQuestion = false;
		Character answer;
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
			System.out.println(Integer.parseInt(Character.toString(line.charAt(0))) == number);
			//Second part of condition is still broken
			if (line.length() > 0 && ((line.charAt(0)) == number)
			{
				isQuestion = true;
				answer = line.charAt(3);
			}
			
		}
		return response;
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