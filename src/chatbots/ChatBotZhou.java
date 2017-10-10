package chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import colleges.CollegeParser;
import util.TextFileReader;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Mr. Levin
 * @version September 2017
 */
public class ChatBotZhou extends ChatBotBase
{
	public String getResponse(String statement) throws FileNotFoundException, IOException
	{
		String response = "";
		
		CollegeParser test = new CollegeParser(statement);
		ArrayList<String> colleges = test.getColleges();
		System.out.println(colleges);
		return response;
	}
	
	public String printQuestion(int number, String category) throws FileNotFoundException, IOException
	{
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
				answer = line.charAt(3);
				return response;
			}
			if (isQuestion)
			{
				response += line + "\n";
			}
			if ((int)line.charAt(0) == line.charAt(0))
			{
				isQuestion = true;
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
