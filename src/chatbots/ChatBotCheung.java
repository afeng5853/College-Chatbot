package chatbots;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import bodyParts.Brain;
import chatbots.ChatBotBase;
import chatbots.Emotion;
import grammar.FinanceDictionary;
import grammar.SentenceParser;;

/**
 * A chatbot that helps you with college finance. 
 * Features include a financial aid calculator, which calculates your Expected Family Contribution (EFC)
 * and Cost of Attendance (COA) to provide an estimate of the need-based aid you may receive.
 * Also provides information about confusing financial terms and ways to afford college.
 * This version:
 * @author Raymond Cheung
 * @version September 2017
 */

public class ChatBotCheung extends ChatBotBase implements Emotion
{
	private Brain brain;
	String response = "";

	// Defined terms
	public String [] terms = {"fafsa",
			"css",
			"work",
			"reimbursement",
			"tutition",
			"scholarships",
			"room and board",
			"loans",
			"grants", 
			"efc",
			"src",
	};
	public String [] termsDefined = {"FAFSA (Free Application for Federal Student Aid): You've probably heard of the FAFSA, but do you know what it is and just how important it can be for you and your family? Filling out the FAFSA is one of the first steps in the financial aid process, and determines the amount that you or your family will be contributing to your postsecondary education. The results of the FAFSA determine student grants, work-study, and loan amounts. We recommend that everyone fills out the FAFSA; it only takes about an hour to complete, and you may be surprised with the results.",
			"The CSS/Financial Aid PROFILE (often written as CSS PROFILE), short for the College Scholarship Service PROFILE, is an application distributed by the College Board in the United States allowing college students to apply for financial aid. ... Each CSS PROFILE costs a fee, varying from year to year.",
			"Work-study/work award: The Federal Work Study program provides funds to eligible students (see FAFSA above) for part-time employment to help finance the costs of postsecondary education. In most cases, the school or employer has to pay up to 50 percent of the student's wages, with the federal government covering the rest. You could be employed by the college itself; or by a federal, state, or local public agency; a private nonprofit organization; or a private for-profit organization.",
			"Tuition reimbursement: Tuition reimbursement, also sometimes called \\\"tuition assistance,\\\" is increasing in popularity. Some employers will refund you the cost of your tuition if you're studying a work-related area. Tuition reimbursement can cover as little as one or two courses, or can cover up to the entire cost of your education.",
			"Tuition: College tuition is the \"sticker price\" of your education, and does not include room and board, textbooks, or other fees. Colleges often calculate tuition based on the cost of one credit, or \"unit.\" For example, a college may charge $350 per credit for an undergraduate class. Many times colleges will simplify this by providing a flat fee for tuition; you're often required to take a minimum amount of credits and cannot exceed a maximum amount of credits. \"True cost\" is a little misleading, since there are other costs on top of tuition.",
			"Scholarships: There really isn't much difference between a scholarship and a grant, though the general consensus is that scholarships are primarily awarded for academic merit (good grades) or for something you have accomplished (volunteer work or a specific project); however, there are many need-based scholarships out there, as well. Like grants, scholarships don't have to be repaid.\n Visit https://www.fastweb.com/ for scholarship opportunities!",
			"Room and board: Everyone needs to sleep and eat. If you plan to do it on campus, those fees are part of your total cost of attendance.",
			"Loans: If scholarships and grants don't cover the entire cost of your tuition, you may have to take out a student loan to make up the difference. Federal student loans don't have to be paid while you're in college, and there are also a variety of loan forgiveness programs out there post-graduation. The rates and terms are generally more flexible than private loans.",
			"Grants: Did someone say free money? Unlike loans, grants­­­­—which can come from the state or federal government, from the college itself, or from private sources—provide money for college that doesn't have to be paid back. We'll take this opportunity here to remind you again to fill out the FAFSA; many grants determine eligibility by looking at your FAFSA results.",
			"Expected Family Contribution (EFC): This is the measure of your family's financial strength, and how much of your college costs it should plan to cover. This is calculated based on a specific formula, which considers taxed and untaxed income, assets, and benefits, as well as the size of your family and the number of family members attending college during the year. Your expected family contribution is calculated based on your FAFSA results.",
			"https://www.usnews.com/education/blogs/the-scholarship-coach/2012/07/19/12-college-financial-aid-terms-defined",
	};
	
	String purpose = "Would you like help with college finance? I can define confusing financial terms or help estimate your financial aid.";
	
	public ChatBotCheung() {
		brain = new Brain();
		brain.addMemoryType("responses");
	}

	public String getGreeting()
	{
		return "Hi, I am a financial college chatbot." + purpose;
	}
	 
	public String getResponse(String statement) throws FileNotFoundException, IOException
	{
		//http://www.collegegold.com/download/efcworksheetindependent.pdf
		FinanceDictionary finance = new FinanceDictionary();
		if (statement.length() == 0)
		{
			response = "Please type something. I can help you with college finance.";
		}
		else if (findKeyword(statement, "calculator") >= 0) {
			System.out.println(calculateAid() + " is your expected cost!"
					);
		}
		else if (findKeyword(statement, "terms") >= 0 || findKeyword(statement, "define") >= 0) {
			response = "I can define the following terms: " + Arrays.toString(terms);
		}		
		else if (inDictionary(statement.toLowerCase(), finance)){ 
			response = purpose;
		}
		else if (Arrays.asList(terms).contains(statement)){
			response =  termsDefined [Arrays.asList(terms).indexOf(statement)];
		}
		else {
			response = "Invalid entry";
		}
		brain.addToMemory("responses", response);
		ArrayList<String> responses = brain.getMemory("responses");
		String lastResponse = responses.get(responses.size()-1);
		//System.out.println(lastResponse + "test");
		if (lastResponse.equals(purpose) ) {
			response = "Would you like me to estimate your financial need OR define confusing terms?";
		}
		return response;	
	}
	
	/**
	 * Checks to see if any part of the user's response is found in a dictionary.
	 * @param 	statement 	the response of the user
	 * @param 	dict 	    the dictionary that should be referenced
	 * @return 				true if any part of the statement was found in the dictionary
	 */
	
	public boolean inDictionary (String statement, FinanceDictionary dict) throws FileNotFoundException, IOException {
		ArrayList<String> wordList = SentenceParser.getWords(statement);
		for (String word : wordList) {
			if (dict.contains(word)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Estimates the financial aid the user will receive. 
	 * Calculates this by subtracting the EFC from the COA.
	 * @param 	statement 	the response of the user
	 * @return 				the estimated financial aid 
	 */
	
	public int calculateAid () throws FileNotFoundException, IOException {
		Scanner in = new Scanner (System.in);
		int studentIncome = 0;
		int EFC = 0;
		int COA = 0;
		int ASSET = 0;
		int AGE = 0;
		int familyCount = 0;
		int collegeCount = 0;	
		int parentIncome = 0;
		int studentAsset = 0;
		int parentAsset = 0;
		// Assumes unmarried
		System.out.println("What is your age?");
		AGE = Integer.parseInt(in.nextLine());	
		System.out.println("How many people live in your household?");
		familyCount = Integer.parseInt(in.nextLine());
		System.out.println("How many people in your household are in college?");
		collegeCount = Integer.parseInt(in.nextLine());
		System.out.println("A college's cost of attendance (COA) is the total direct and indirect costs of a year of college. What is your COA?");
		COA = Integer.parseInt(in.nextLine());
			System.out.println("What is your income?");
			studentIncome = Integer.parseInt(in.nextLine());
			System.out.println("What is your parent's income?");
			parentIncome = Integer.parseInt(in.nextLine());
			System.out.println("What is your assets?");
			studentAsset = Integer.parseInt(in.nextLine());
			studentAsset = (int) ((studentAsset*.5)/2.3);
			System.out.println("What is your parent's assets?");
			parentAsset = Integer.parseInt(in.nextLine());
		if (studentIncome < 20000) {
			EFC = 0;
		}
		if (studentIncome < 50000) {
			ASSET = 0;
		}
		EFC = (int) ((parentAsset + studentAsset + studentIncome + parentIncome)*.47/collegeCount);
	return COA - EFC;
	}
}