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
	
	public SentenceManipulator (String sentence) throws FileNotFoundException, IOException {
		adjDict = new AdjectiveDictionary();
		nounDict = new NounDictionary();
		verbDict = new VerbDictionary();
		adverbDict = new AdverbDictionary();
		this.sentence = sentence;
	}
	
	private final void addToPartOfSpeechMap(HashMap<String, ArrayList<Integer>> map, String word, int idx) {
		// getting the value arrays
		ArrayList<Integer> nounList = map.get("noun");
		ArrayList<Integer> adjectiveList = map.get("adjective");
		ArrayList<Integer> adverbList = map.get("adverb");
		ArrayList<Integer> verbList = map.get("verb");
		
		// if the word is a certain part of speech, add it to the corresponding array with its position index
		if (nounDict.contains(word)) {
			nounList.add(idx);
		} else if (adjDict.contains(word)) {
			adjectiveList.add(idx);
		} else if (verbDict.contains(word)) {
			verbList.add(idx);
		} else if (adverbDict.contains(word)) {
			adverbList.add(idx);
		}
	}
	
	/**
	 * @return returns a HashMap with the key being a part of speech, and the value being an ArrayList of the index parts of speech found in the sentence
	 * @see addToPartOfSpeechMap
	 */
	public final HashMap<String, ArrayList<Integer>> getPartsOfSpeech() {
		HashMap<String, ArrayList<Integer>> partsOfSpeech = new HashMap<>(); // will have each part of speech be a key and a corresponding array
		
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
			
			// indicates the end of a word
			if (!Character.isLetter(currentChar) && startOfWord) {
				startOfWord = false;
				// evaluates the part of speech and adds it to the hashmap
				addToPartOfSpeechMap(partsOfSpeech, currentWord.toString(), i + 1);
				currentWord.setLength(0); // resets the StringBuilder
			}
			
			// building the word
			if (startOfWord) {
				currentWord.append(currentChar);
			}
			
		}
		return partsOfSpeech;
	}
	
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
}
