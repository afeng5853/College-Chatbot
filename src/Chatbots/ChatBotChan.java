package Chatbots;
import java.util.Random;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Alex Chan
 * @version September 2017
 */
public class ChatBotChan extends ChatBotLevin implements Emotion
{
	public ChatBotChan()
	{
		
	}
	private String getGreeting()
	{
		return "Greetings, User. Please enter an interest you have.";
	}
	private String interests(String interest)
	{
		interest = "";
		if (interest.length() == 0)
		{
			interest = "If you do not respond with an interest, I cannot give you a suggestion";
		}
		else if (findKeyword(interest, "math") >= 0)
		{
			interest = "The economics major, or computer science major may be a good choice in regards to your interests.";
		}
		else if (findKeyword(interest, "read") >= 0 || findKeyword(interest, "reading") >= 0 || findKeyword(interest, "books") >= 0 || findKeyword(interest, "literature") >= 0)
		{
			interest = "Majors involving the English language or literature";
		}
	}
}
