package chatbots;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import bodyParts.Brain;
import chatbots.ChatBotBase;
import chatbots.Emotion;
import grammar.FinanceDictionary;
import grammar.SentenceParser;;


/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Raymond Cheung
 * @version September 2017
 */

public class ChatBotCheung extends ChatBotBase implements Emotion
{
	private Brain brain;
	
	public ChatBotCheung() {
		brain = new Brain();
		brain.addMemoryType("responses");
	}

	public String getResponse(String statement) throws FileNotFoundException, IOException
	{
		//http://www.collegegold.com/download/efcworksheetindependent.pdf
		
		String fasfa = "The Free Application for Federal Student Aid (FAFSA) is a form that can be prepared annually by current and prospective college students (undergraduate and graduate) in the United States to determine their eligibility for student financial aid.\r\n";
		String css = "The CSS/Financial Aid PROFILE (often written as CSS PROFILE), short for the College Scholarship Service PROFILE, is an application distributed by the College Board in the United States allowing college students to apply for financial aid. ... Each CSS PROFILE costs a fee, varying from year to year.\r\n";
		String response = "";
	
		FinanceDictionary finance = new FinanceDictionary();
		if (statement.length() == 0)
		{
			response = "Please type something. I can help you with college finance. ";
		}
		else if (findKeyword(statement,"help")  >= 0) {
			response = fasfa;
		}
		else if (inDictionary(statement, finance)){
			response = "Would you like help with college finance?";
		}
		else if (findKeyword(statement, "calculator") >= 0) {
			calculateEFC("calculator");
		}
		else {
			response = "idk"; 
		}
		brain.addToMemory("responses", response);
		ArrayList<String> responses = brain.getMemory("responses");
		String lastResponse = responses.get(responses.size()-1);
		//System.out.println(lastResponse);
		if (lastResponse.equals("Would you like help with college finance?") && findKeyword(statement, "yes") >= 0 ) {
			response = "Greatzz";
			System.out.println(response);
		}

		//System.out.println(response);
		return response;
	}
	
	public boolean inDictionary (String statement, FinanceDictionary dict) throws FileNotFoundException, IOException {
		ArrayList<String> wordList = SentenceParser.getWords(statement);
		for (String word : wordList) {
			if (dict.contains(word)) {
				return true;
			}
		}
		return false;
	}
	
	public String calculateEFC (String statement) throws FileNotFoundException, IOException {
		String response = "";

		response = "What is your income?";
		if (findKeyword(statement,"calculator")  >= 0) {
			response = "hS";
		}
	return response;

	}
}