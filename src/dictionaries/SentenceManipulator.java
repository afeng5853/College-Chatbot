/*
 * TODO: check which adjectives are modifying which noun
 * 		 determine subject vs object
 */

package dictionaries;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SentenceManipulator {
	private final AdjectiveDictionary adjDict;
	private final NounDictionary nounDict;
	private final VerbDictionary verbDict;
	private final AdverbDictionary adverbDict;
	private final String sentence;
	// will have each part of speech be a key and a corresponding array
	private HashMap<String, ArrayList<Integer>> partsOfSpeech = new HashMap<>();
	
	public SentenceManipulator (String sentence) throws FileNotFoundException, IOException {
		adjDict = new AdjectiveDictionary();
		nounDict = new NounDictionary();
		verbDict = new VerbDictionary();
		adverbDict = new AdverbDictionary();
		this.sentence = sentence;
		setPartsOfSpeech();
	}
	
	private final void addToPartOfSpeechMap(HashMap<String, ArrayList<Integer>> map, String word, int idx) {
		// getting the value arrays
		ArrayList<Integer> nounList = map.get("noun");
		ArrayList<Integer> adjectiveList = map.get("adjective");
		ArrayList<Integer> adverbList = map.get("adverb");
		ArrayList<Integer> verbList = map.get("verb");
		
		// if the word is a certain part of speech, add it to the corresponding array with its position index
		// NOTE: order might change to get best chance of correct part of speech
		if (adjDict.contains(word)) {
			adjectiveList.add(idx);
		} else if (nounDict.contains(word)) {
			nounList.add(idx);
		} else if (adverbDict.contains(word)) {
			adverbList.add(idx);
		} else if (verbDict.contains(word)) {
			verbList.add(idx);
		}
	}

	public final HashMap<String, ArrayList<Integer>> getPartsOfSpeech() {
		return partsOfSpeech;
	}
	
	private final void setPartsOfSpeech() {
		// initializing key, values of parts of speech to a corresponding array
		partsOfSpeech.put("noun", new ArrayList<Integer>());
		partsOfSpeech.put("adjective", new ArrayList<Integer>());
		partsOfSpeech.put("verb", new ArrayList<Integer>());
		partsOfSpeech.put("adverb", new ArrayList<Integer>());
		
		// lowercased so we can match with words in the text files
		String lowerCaseSentence = sentence.toLowerCase();
		
		// builds a word up until a non letter is detected
		StringBuilder currentWord = new StringBuilder();
		boolean startOfWord = false;
		for (int i = 0; i < lowerCaseSentence.length(); i++) {
			char currentChar = lowerCaseSentence.charAt(i);
			
			// indicates the start of a word
			if (Character.isAlphabetic(currentChar) && !startOfWord) {
				startOfWord = true;
			}
			
			// building the word
			if (startOfWord && Character.isLetter(currentChar)) {
				currentWord.append(currentChar);
			}
						
			// indicates the end of a word
			if ((!Character.isLetter(currentChar) || i == lowerCaseSentence.length() - 1) && startOfWord) {
				startOfWord = false;
				// evaluates the part of speech and adds it to the HashMap
				addToPartOfSpeechMap(partsOfSpeech, currentWord.toString(), i - currentWord.length());
				currentWord.setLength(0); // resets the StringBuilder
			}
			
			
			
		}
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
}
