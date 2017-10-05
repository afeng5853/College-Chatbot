/*


 *TODO: check which adjectives are modifying which noun
 * 		 determine subject vs object
 */

package grammar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SentenceParser {
	private final Dictionary dict;
	private final String sentence;
	
	// will have each part of speech be a key and a corresponding array
	private String partOfSpeeches = "";
	
	public SentenceParser (String sentence) throws FileNotFoundException, IOException {
		dict = new Dictionary();
		this.sentence = sentence;
		setPartsOfSpeech();
	}
	

	public final String getPartsOfSpeech() {
		return partOfSpeeches;
	}
	
	private final void setPartsOfSpeech() {
		// lowercased so we can match with words in the text files
		String lowerCaseSentence = sentence.toLowerCase();
		
		// builds a word up until a non letter is detected
		StringBuilder currentWord = new StringBuilder();
		StringBuilder partOfSpeechBuild = new StringBuilder();
		boolean startOfWord = false;
		for (int i = 0; i < lowerCaseSentence.length(); i++) {
			char currentChar = lowerCaseSentence.charAt(i);
			
			// indicates the start of a word
			if (Character.isLetter(currentChar) && !startOfWord) {
				startOfWord = true;
			}
			
			// building the word
			if (startOfWord && Character.isLetter(currentChar)) {
				currentWord.append(currentChar);
			}
			
			// indicates the end of a word
			if ((!Character.isLetter(currentChar) || i == lowerCaseSentence.length() - 1) && startOfWord) {
				startOfWord = false;
				String currentPartOfSpeech = dict.getPartOfSpeech(currentWord.toString());
				if (currentPartOfSpeech == null) {
					partOfSpeechBuild.append("e");
				} else {
					partOfSpeechBuild.append(currentPartOfSpeech);
				}
				currentWord.setLength(0); // resets the StringBuilder
			}
			//building parts of speech equivalent sentence
			if (!Character.isLetter(currentChar)) {
				partOfSpeechBuild.append(currentChar);
			}
		}
		partOfSpeeches = partOfSpeechBuild.toString();
		
	}
	
	// methods below should be part of another class
	public final String getWord(int idx) {
		StringBuilder after = new StringBuilder();
		// get characters after
		for (int i = idx; i < sentence.length(); i++) {
			char currentChar = sentence.charAt(i);
			if (Character.isLetter(currentChar)) {
				after.append(currentChar);
			} else {
				break;
			}
		}
		StringBuilder before = new StringBuilder();
		// get characters before
		for (int i = idx - 1; i >= 0; i--) {
			char currentChar = sentence.charAt(i);
			if (Character.isLetter(currentChar)) {
				before.append(currentChar);
			} else {
				break;
			}
		}
		return before.reverse().toString() + after.toString();
	}
	
	public final String getPreviousWord(int idx) {
		for (int i = idx - 1; i >= 0; i--) {
			char currentChar = sentence.charAt(i);
			if (!Character.isLetter(currentChar)) {
				return getWord(i-1);
			}
		}
		return "";
	}

	private final int getPreviousWordIdx(int idx) {
		int count = 0; // two counts indicate the previous word
		for (int i = idx - 1; i >= 0; i--) {
			char currentChar = sentence.charAt(i);
			if (!Character.isLetter(currentChar)) {
				count++;
			}
			if (count == 2) {
				return i + 1;
			}
			if (count == 1 && i == 0) {
				return 0;
			}
		}
		return -1;
	}
	
	public final ArrayList<Integer> getModifiers(int idx) {
		ArrayList<Integer> modifiers = new ArrayList<>();
		
		//ArrayList<Integer> nounList = partsOfSpeech.get("noun");
		ArrayList<Integer> adjectiveList = partsOfSpeech.get("adjective");
		ArrayList<Integer> adverbList = partsOfSpeech.get("adverb");
		
		int previousWord = getPreviousWordIdx(idx);
		while (true) {
			// if no more previous words
			if (previousWord == -1) {
				break;
			}
			if (//nounList.contains(previousWord) || 
				adjectiveList.contains(previousWord) ||
				adverbList.contains(previousWord)) {
				modifiers.add(previousWord);
			} else {
				break;
			}
			previousWord = getPreviousWordIdx(previousWord);
		}
		
		return modifiers;
	}
	
	public static ArrayList<String> getWords(String sentence) {
		StringBuilder currentWord = new StringBuilder();
		ArrayList<String> wordList = new ArrayList<>();
		boolean startOfWord = false;
		for (int i = 0; i < sentence.length(); i++) {
			char currentChar = sentence.charAt(i);
			
			// indicates the start of a word
			if (Character.isLetter(currentChar) && !startOfWord) {
				startOfWord = true;
			}
			
			// building the word
			if (startOfWord && Character.isLetter(currentChar)) {
				currentWord.append(currentChar);
			}
			
			// indicates the end of a word
			if ((!Character.isLetter(currentChar) || i == sentence.length() - 1) && startOfWord) {
				startOfWord = false;
				wordList.add(currentWord.toString());
				currentWord.setLength(0); // resets the StringBuilder
			}
		}
		return wordList;
	}
}
