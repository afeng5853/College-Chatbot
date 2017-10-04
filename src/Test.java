import java.io.FileNotFoundException;
import java.io.IOException;

import dictionaries.*;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		NounDictionary test = new NounDictionary();
		AdjectiveDictionary test1 = new AdjectiveDictionary();
		System.out.println(test.contains("name"));
	}

	public static int findKeyword(String sentence, String keyword, int start) {
		//case insensitive
		keyword = keyword.toLowerCase();
		sentence = sentence.toLowerCase();
		
		int currentIdx = sentence.indexOf(keyword, start); //index of keyword
		int lengthOfSentence = sentence.length();
		int lengthOfKeyword = keyword.length();
		while(true) {
			if (currentIdx - 1 < 0
				|| currentIdx + 1 >= lengthOfSentence - lengthOfKeyword
				|| !Character.isLetter(sentence.charAt(currentIdx - 1))
				&& !Character.isLetter(sentence.charAt(currentIdx + lengthOfKeyword))) {
				return currentIdx;
			} else {
				int nextIdx = sentence.indexOf(keyword, currentIdx + 1);
				if (nextIdx == -1) {
					return -1;
					
				}
				currentIdx = nextIdx;
			}
		}
	}
}
