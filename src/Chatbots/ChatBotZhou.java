package Chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import colleges.CollegeParser;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Mr. Levin
 * @version September 2017
 */
public class ChatBotZhou extends ChatBotLevin
{
	public String getResponse(String statement) throws FileNotFoundException, IOException
	{
		String response = "";
		
		CollegeParser test = new CollegeParser(statement);
		ArrayList<String> colleges = test.getColleges();
		System.out.println(colleges);
		return response;
	}
	 
}
