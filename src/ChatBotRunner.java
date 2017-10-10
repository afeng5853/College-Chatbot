
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import chatbots.ChatBotBase;
import chatbots.ChatBotChan;
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
		ChatBotChan chatbot1 = new ChatBotChan();
		ChatBotCheung chatbot2 = new ChatBotCheung();
		ChatBotFeng chatbot3 = new ChatBotFeng();
		ChatBotZhou chatbot4 = new ChatBotZhou();
		
		
		System.out.println("Hello! We are chatbots that will assist you with your college application!\n"
							+ "Please pick a chatbot to talk to!\n"
							+ "1. Chatbot Chan - Major Interests\n"
							+ "2. Chatbot Cheung - Financial Aid\n"
							+ "3. Chatbot Feng - General information about a college\n"
							+ "4. Chatbot Zhou - Prepartion for the SAT");
		Scanner in = new Scanner (System.in);
		
		int choice;
		while (true) {
			try {
				choice = Integer.parseInt(in.nextLine());
				if (choice >= 1 && choice <= 4) {
					break;
				} else {
					System.out.println("Please enter a valid number indicating the chatbot you want to talk to.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number indicating the chatbot you want to talk to.");
			}
		}
		
		// Greetings
		switch (choice) {
			case 1:
			// Chan
				System.out.println (chatbot1.getGreeting());
				break;
			case 2:
			// Cheung
				System.out.println (chatbot2.getGreeting());
				break;
			case 3:
			// Feng
				System.out.println (chatbot3.getGreeting());
				break;
			case 4:
			// Zhou
				System.out.println (chatbot4.getGreeting());
				break;
		// Responses
		}
		String statement = in.nextLine();
		while (!statement.equals("Bye"))
		{
			switch (choice) {
				case 1:
				// Chan
					System.out.println (chatbot1.getResponse(statement));
					break;
				case 2:
				// Cheung
					System.out.println (chatbot2.getResponse(statement));
					break;
				case 3:
				// Feng
					System.out.println (chatbot3.getResponse(statement));
					break;
				case 4:
				// Zhou
					System.out.println (chatbot4.getResponse(statement));
					break;
			}
			
			statement = in.nextLine();
		}
		in.close();
	}

}