package chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import grammar.GreetingsDictionary;

/**
 * A program to carry on conversations with a human user. Used to recommend majors based on interests.
 * This version:
 * @author Alex Chan
 * @version September 2017
 */
public class ChatBotChan extends ChatBotLevin
{
	private int emotion = 0;
	Scanner in = new Scanner (System.in);
	public ChatBotChan()
	{
		
	}
	public String getGreeting()
	{
		return "Greetings, User. Please enter an interest you have.";
	}
	public String getResponse(String statement) throws FileNotFoundException, IOException
	{
		String response = "";
		GreetingsDictionary greetingsDict = new GreetingsDictionary();
		if (statement.length() == 0)
		{
			response = "If you do not respond with an interest, I cannot give you a suggestion";
			emotion--;
		}
		else if (greetingsDict.contains(statement.toLowerCase())) {
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
		else if (findKeyword(statement, "feng") != -1) {
			System.out.println("Okay, bye!");
			response = "~CALLFENG";
		}
		
		else if (findKeyword(statement, "math", 0) >= 0 || findKeyword(statement, "mathematics", 0) >= 0)
		{
			response = "The economics major, or computer science major may be a good choice in regards to your interests.";
			emotion++;
		}
		else if (findKeyword(statement, "computers", 0) >= 0 || findKeyword(statement, "computer", 0) >= 0 || 
				findKeyword(statement, "coding", 0) >= 0 || findKeyword(statement, "programming", 0) >= 0)
		{
			response = "The software engineering major may be a good choice in regards to your interests.";
			emotion++;
		}
		else if (findKeyword(statement, "read", 0) >= 0 || findKeyword(statement, "reading", 0) >= 0 || findKeyword(statement, "books", 0) >= 0 || 
				findKeyword(statement, "literature", 0) >= 0 || findKeyword(statement, "writing", 0) >= 0 || findKeyword(statement, "write", 0) >= 0)
		{
			response = "Majors involving the English language or literature may be a good choice in regards to your interests.";
			emotion++;
		}
		else if (findKeyword(statement, "business", 0) >= 0 || findKeyword(statement, "finance", 0) >= 0 || findKeyword(statement, "statistics", 0) >= 0 || 
				findKeyword(statement, "money", 0) >= 0 || findKeyword(statement, "economics", 0) >= 0)
		{
			response = "I have a few matches, but please answer this question to allow me to narrow down"
					+ " the choices. Do you consider yourself a strong leader? Please answer with a yes or no.";
			System.out.println(response);
			statement = in.nextLine();
			if(statement.equals("no"))
			{
				response = "Economics may be a better suit considering your comforts.";
				emotion++;
			}
			else if (statement.equals("yes"))
			{
				response = "I believe the communications and buisness major is good for your traits.";
				emotion++;
			}
			else
			{
				response = "Please type a valid answer.";
				emotion--;
			}
		}
		else if (findKeyword(statement, "biology", 0) >= 0 || findKeyword(statement, "enviorment", 0) >= 0)
		{
			response = "The biological science major is a good choice.";
			emotion++;
		}
		else if (findKeyword(statement, "chemistry", 0) >= 0)
		{
			response = "The organic chemistry major is a good choice. The chemical engineering major may be an interesting one too.";
			emotion++;
		}		
		else if (findKeyword(statement, "medicine", 0) >= 0 || findKeyword(statement, "health", 0) >= 0 || findKeyword(statement, "doctor", 0) >= 0 || findKeyword(statement, "nurse", 0) >= 0)
		{
			response = "The nursing major is a good choice.";
			emotion++;
		}
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want",0) >= 0)
		{
			response = transformIWantStatement(statement);
		}	
		else
		{
			response = getRandomResponse();
			emotion--;
		}
		return response;
	}
	
	public int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim().toLowerCase();
		goal = goal.toLowerCase();

		// The only change to incorporate the startPos is in the line below
		int psn = phrase.indexOf(goal, startPos);

		// Refinement--make sure the goal isn't part of a word
		while (psn >= 0)
		{
			// Find the string of length 1 before and after the word
			String before = " ", after = " ";
			if (psn > 0)
			{
				before = phrase.substring(psn - 1, psn);
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(
						psn + goal.length(),
						psn + goal.length() + 1);
			}

			// If before and after aren't letters, we've found the word
			if (((before.compareTo("a") < 0) || (before
					.compareTo("z") > 0)) // before is not a
											// letter
					&& ((after.compareTo("a") < 0) || (after
							.compareTo("z") > 0)))
			{
				return psn;
			}

			// The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal, psn + 1);

		}
		return -1;
	}
	
	public String getRandomResponse()
	{
		Random r = new Random ();
		if (emotion == 0)
		{	
			return randomNeutralResponses [r.nextInt(randomNeutralResponses.length)];
		}
		if (emotion < 0)
		{	
			return randomAngryResponses [r.nextInt(randomAngryResponses.length)];
		}	
		return randomHappyResponses [r.nextInt(randomHappyResponses.length)];
	}
	
	public String [] randomNeutralResponses = {"Interesting, tell me more",
			"Hmmm.",
			"Do you really think so?",
			"You don't say.",
			"It's all boolean to me.",
			"I'm not sure if I quite understood that.",
			"Could you say that again?"
	};
	public String [] randomAngryResponses = {"Stop fooling around.", "If you don't wish to cooperate, then I cannot help you.", "Do you even plan on going to college?"};
	public String [] randomHappyResponses = {"You are suprisingly cooperative, unlike previous users...", "Today is a good day", "You make me feel like a brand new pair of shoes."};
}
