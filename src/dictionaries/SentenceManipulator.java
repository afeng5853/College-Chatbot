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
	
	/**
	 * 
	 * @return returns a HashMap with the key being a part of speech, and the value being an ArrayList of the parts of speech found in the sentence
	 */
	public final HashMap<String, ArrayList<String>> getPartsOfSpeech() {
		String[] wordList = sentence.split(" "); // an array of the words of the sentence string
		
		HashMap<String, ArrayList<String>> partsOfSpeech = new HashMap<>(); // will have each part of speech be a key and a corresponding array
		
		// initializing key, values of parts of speech to a corresponding array
		partsOfSpeech.put("noun", new ArrayList<String>());
		partsOfSpeech.put("adjective", new ArrayList<String>());
		partsOfSpeech.put("verb", new ArrayList<String>());
		partsOfSpeech.put("adverb", new ArrayList<String>());
		
		// getting the value arrays
		ArrayList<String> nounList = partsOfSpeech.get("noun");
		ArrayList<String> adjectiveList = partsOfSpeech.get("adjective");
		ArrayList<String> adverbList = partsOfSpeech.get("adverb");
		ArrayList<String> verbList = partsOfSpeech.get("verb");
		
		for (int i = 0; i < wordList.length; i++) {
			// removes all punctuation
			String word = wordList[i].replaceAll("[^\\w]", "");
			
			// need lowercased word to have case insensitive checks with the parts of speech text files
			String lowerCasedWord = word.toLowerCase();
			
			//if the word is found in the corresponding part of speech text file, add it to the corresponding part of speech array
			if (nounDict.contains(lowerCasedWord) && !nounList.contains(word)) {
				nounList.add(word);
			} else if (adjDict.contains(lowerCasedWord) && !adjectiveList.contains(word)) {
				adjectiveList.add(word);
			} else if (verbDict.contains(lowerCasedWord) && !verbList.contains(word)) {
				verbList.add(word);
			} else if (adverbDict.contains(lowerCasedWord) && !adverbList.contains(word)) {
				adverbList.add(word);
			}
		}
		return partsOfSpeech;
	}
}
