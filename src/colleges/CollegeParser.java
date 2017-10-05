package colleges;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import grammar.Dictionary;
import grammar.SentenceParser;

public class CollegeParser {
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
	
	//move to util
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
	
	public ArrayList<ArrayList<String>> getColleges() {
		int collegeIdx = sentence.indexOf("College");
		ArrayList<ArrayList<String>> potentialColleges = findSubsequentTitleCasedWords();
		ArrayList<ArrayList<String>> colleges = new ArrayList<>();
		for (ArrayList<String> potentialCollege : potentialColleges) {
			String potentialCollegeStr = joinArrayList(potentialCollege).toLowerCase();
			// special case (generalize later	)
			if (potentialCollegeStr.equals("mit")) {
				colleges.add(potentialCollege);
			} else if (collegeList.indexOf(potentialCollegeStr) != -1) {
				colleges.add(potentialCollege);
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
}
