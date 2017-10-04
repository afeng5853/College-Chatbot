package Chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import dictionaries.SentenceManipulator;

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
		SentenceManipulator sentenceParser = new SentenceManipulator(statement);
		HashMap<String, ArrayList<Integer>> partsOfSpeech = sentenceParser.getPartsOfSpeech();
		ArrayList<Integer> nounIndices = partsOfSpeech.get("noun");
		System.out.println(nounIndices);
		
		String response = "I like ";
		
		for (int index : nounIndices) {
			response += sentenceParser.getWord(index) + ", ";
		}
		
		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}

		return response;
	}
}
