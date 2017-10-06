package colleges;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

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
	
	public ArrayList<String> getColleges() {
		int collegeIdx = sentence.indexOf("College");
		ArrayList<ArrayList<String>> potentialColleges = findSubsequentTitleCasedWords();
		ArrayList<String> colleges = new ArrayList<>();
		for (ArrayList<String> potentialCollege : potentialColleges) {
			String college = joinArrayList(potentialCollege);
			String collegeLowerCased = college.toLowerCase();
			// special case (generalize later	)
			if (collegeLowerCased.equals("mit")) {
				colleges.add(college);
			} else if (collegeList.indexOf(collegeLowerCased) != -1) {
				colleges.add(college);
			}
		}
		return colleges;
	}
	
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
