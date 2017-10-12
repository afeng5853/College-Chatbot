
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import chatbots.ChatBotLevin;
import chatbots.ChatBotChan;
import chatbots.ChatBotCheung;
import chatbots.ChatBotFeng;
import chatbots.ChatBotZhou;
import grammar.SalutationsDictionary;

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
		ChatBotFeng chatbot1 = new ChatBotFeng();
		ChatBotCheung chatbot2 = new ChatBotCheung();
		ChatBotChan chatbot3 = new ChatBotChan();
		ChatBotZhou chatbot4 = new ChatBotZhou();
		
		
		System.out.println("Hello! We are chatbots that will assist you with your college application!\n"
							+ "Please pick a chatbot to talk to!\n"
							+ "1. Chatbot Feng - General information about a college\n"
							+ "2. Chatbot Cheung - Financial Aid\n"
							+ "3. Chatbot Chan - Major Interests\n"
							+ "4. Chatbot Zhou - Preparation for the SAT");
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
		
		// Greetings from bots
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
				System.out.println(chatbot3.getGreeting());
				break;
			case 4:
			// Zhou
				System.out.println (chatbot4.getGreeting());
				break;
		// Responses
		}
		String statement = in.nextLine();
		SalutationsDictionary saluDict = new SalutationsDictionary();
		while (!saluDict.contains(statement.toLowerCase()))
		{
			switch (choice) {
				case 1:
				// Feng
					String fengResponse = chatbot1.getResponse(statement);
					// Swapping between bots
					if (fengResponse.equals("~CALLZHOU")) {
						choice = 4;
						System.out.println("Zhou: " + chatbot4.getGreeting());
					} else if (fengResponse.equals("~CALLCHAN")) {
						choice = 3;
						System.out.println("Chan: " + chatbot3.getGreeting());
					} else if (fengResponse.equals("~CALLCHEUNG")) {
						choice = 2;
						System.out.println("Cheung: " + chatbot2.getGreeting());
					} else {
						System.out.println(fengResponse);
					}
					break;
				case 2:
				// Cheung
					String cheungResponse = chatbot2.getResponse(statement);
					// Swapping between bots
					if (cheungResponse.equals("~CALLZHOU")) {
						choice = 4;
						System.out.println("Zhou: " + chatbot4.getGreeting());
					} else if (cheungResponse.equals("~CALLFENG")) {
						choice = 1;
						System.out.println("Feng: " + chatbot1.getGreeting());
					} else if (cheungResponse.equals("~CALLCHAN")) {
						choice = 3;
						System.out.println("Chan: " + chatbot3.getGreeting());
					} else {
						System.out.println(cheungResponse);
					}
					break;
				case 3:
				// Chan
					String chanResponse = chatbot3.getResponse(statement);
					// Swapping between bots
					if (chanResponse.equals("~CALLZHOU")) {
						choice = 4;
						System.out.println("Zhou: " + chatbot4.getGreeting());
					} else if (chanResponse.equals("~CALLFENG")) {
						choice = 1;
						System.out.println("Feng: " + chatbot1.getGreeting());
					} else if (chanResponse.equals("~CALLCHEUNG")) {
						choice = 2;
						System.out.println("Cheung: " + chatbot2.getGreeting());
					} else {
						System.out.println(chanResponse);
					}
					break;
				case 4:
				// Zhou
					String zhouResponse = chatbot4.getResponse(statement);
					// Swapping between bots
					if (zhouResponse.equals("~CALLCHEUNG")) {
						choice = 2;
						System.out.println("Cheung: " + chatbot2.getGreeting());
					} else if (zhouResponse.equals("~CALLFENG")) {
						choice = 1;
						System.out.println("Feng: " + chatbot1.getGreeting());
					} else if (zhouResponse.equals("~CALLCHAN")) {
						choice = 3;
						System.out.println("Chan: " + chatbot3.getGreeting());
					} else {
						System.out.println(zhouResponse);
					}
					break;
			}
			statement = in.nextLine();
		}
		System.out.println("Bye!");
		in.close();
	}

}