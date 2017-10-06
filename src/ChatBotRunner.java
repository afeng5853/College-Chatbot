
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import chatbots.ChatBotBase;
import chatbots.ChatBotCheung;
import chatbots.ChatBotFeng;
import chatbots.ChatBotZhou;

/**
 * A simple class to run our chatbot teams.
 * @author Mr. Levin
 * @version September 2017
 */
public class ChatBotRunner
{

	/**
	 * Create instances of each chatbot, give it user input, and print its replies. Switch chatbot responses based on which chatbot the user is speaking too.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		ChatBotCheung chatbot1 = new ChatBotCheung();
		//ChatBotFeng chatbot1 = new ChatBotFeng();
		//ChatBotZhou chatbot2 = new ChatBotZhou();
		//chatbot2.getResponse("My mom went to Princeton University, while my Dad went to Stony Brook");
		
		System.out.println (chatbot1.getGreeting());
		Scanner in = new Scanner (System.in);
		String statement = in.nextLine();
		//System.out.println(chatbot1.getResponse(statement));

		
		while (!statement.equals("Bye"))
		{
			System.out.println (chatbot1.getResponse(statement));
			statement = in.nextLine();
		}
		
	}

}
