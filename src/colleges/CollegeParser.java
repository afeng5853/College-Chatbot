package colleges;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import chatbots.ChatBotLevin;
import grammar.Dictionary;
import grammar.SentenceParser;

/**
 * College-related parsing
 * This version:
 * @author Alex Feng
 * @version October 2017
 */
public class CollegeParser{
	private final String sentence;
	private String collegeList;

	/**
	 * @param sentence the sentence to be parsed
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public CollegeParser(String sentence) throws FileNotFoundException, IOException {
		this.sentence = sentence;
		
		BufferedReader in = new BufferedReader(new FileReader("src/colleges/colleges.txt"));
		
		String str;
		StringBuilder build = new StringBuilder();
		while ((str = in.readLine()) != null) {
			build.append(str.toLowerCase() + "|");
		}
		collegeList = build.toString();
		in.close();
	}
	
	//move to utils
	/**
	 * @param A the array list to be joined
	 * @return a string of the array space delimited
	 */
	private String joinArrayList(ArrayList<String> A) {
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < A.size(); i++) {
			if (i == A.size() - 1) {
				build.append(A.get(i));
			} else {
				build.append(A.get(i) + " ");
			}
		}
		return build.toString();
	}
	
	/**
	 * gets the colleges and stores it in memory in collegeList
	 * @see findSubsequentTitleCasedWords
	 */
	public ArrayList<String> getColleges() {
		// finds two word or more colleges
		ArrayList<ArrayList<String>> potentialColleges = findSubsequentTitleCasedWords();
		ArrayList<String> colleges = new ArrayList<>();
		
		// one word colleges
		ChatBotLevin bot = new ChatBotLevin();
		if (bot.findKeyword(sentence, "MIT") != -1) {
			colleges.add("Massachusetts Institute of Technology");
		} else if (bot.findKeyword(sentence, "harvard") != -1) {
			colleges.add("Harvard University");
		} else if (bot.findKeyword(sentence, "yale") != -1) {
			colleges.add("Yale University");
		} else if (bot.findKeyword(sentence, "princeton") != -1) {
			colleges.add("Princeton University");
		} else if (bot.findKeyword(sentence, "columbia") != -1) {
			colleges.add("Columbia University");
		} else if (bot.findKeyword(sentence, "brown") != -1) {
			colleges.add("Brown University");
		} else if (bot.findKeyword(sentence, "NYU") != -1) {
			colleges.add("New York University");
		} else if (bot.findKeyword(sentence, "stony") != -1) {
			colleges.add("Stony Brook");
		} else if (bot.findKeyword(sentence, "binghamton") != -1) {
			colleges.add("SUNY at Binghamton");
		} else if (bot.findKeyword(sentence, "cornell") != -1) {
			colleges.add("Cornell University");
		} else if (bot.findKeyword(sentence, "dartmouth") != -1) {
			colleges.add("Dartmouth University");
		} else if (bot.findKeyword(sentence, "upenn") != -1) {
			colleges.add("University of Pennsylvania");
		} else if (bot.findKeyword(sentence, "uchicago") != -1) {
			colleges.add("University of Chicago");
		} else {
			// process two or more word colleges
			for (ArrayList<String> potentialCollege : potentialColleges) {
				String college = joinArrayList(potentialCollege);
				String collegeLowerCased = college.toLowerCase();
				if (collegeList.indexOf(collegeLowerCased) != -1) {
					colleges.add(college);
				}
			}
		}
		return colleges;
	}
	
	/**
	 * finds subsequent title cased words
	 * @return an array of subsequent title cased words
	 */
	private ArrayList<ArrayList<String>> findSubsequentTitleCasedWords() {
		int count = 0;
		ArrayList<String> wordList = SentenceParser.getWords(sentence);
		ArrayList<String> potentialCollege = new ArrayList<>();
		ArrayList<ArrayList<String>> potentialColleges = new ArrayList<>();
		for(String str: wordList) {
			// generalize special cases later
			if (str.toLowerCase().equals("mit")) {
				ArrayList<String> mit = new ArrayList<>();
				potentialColleges.add(mit);
			} else if (str.charAt(0) == Character.toUpperCase(str.charAt(0)) || str.equals("of") || str.equals("the") || str.equals("at")) {
				if (count == 0 && (str.equals("of") || str.equals("at") || str.equals("the"))) {
					continue;
				}
				count++;
				potentialCollege.add(str);
				// if string is last word
				if (count >= 2 && str.equals(wordList.get(wordList.size() - 1))) {
					potentialColleges.add(potentialCollege);
				}
			} else {
				if (count >= 2) {
					potentialColleges.add(potentialCollege);
				}
				potentialCollege = new ArrayList<>();
				count = 0;
			}
		}
		return potentialColleges;
	}
	
	/**
	 * 
	 * @param college the college requested
	 * @param rowIdx the column index of the csv file
	 * @return the college data at the cell
	 * @throws IOException
	 */
	public static String getCollegeData(String college, int rowIdx) throws IOException {
		CSVReader csvReader = new CSVReader(new FileReader("src/colleges/college data.csv"));
		String returnStr = null;
		String[] row;
		while ((row = csvReader.readNext()) != null) {
			if (row[0].toLowerCase().indexOf(college.toLowerCase()) != -1) {
				String data = row[rowIdx];
				if (data.equals("NULL")) {
					returnStr = null;
				} else {
					returnStr = data;
				}
				break;
			}
		}
		csvReader.close();
		return returnStr;
	}
}
