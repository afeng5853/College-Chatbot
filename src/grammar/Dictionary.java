package grammar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/*
	Noun                        	N
	Plural                      	p
	Noun Phrase						h
	Verb (usu participle)		    V
	Verb (transitive)   		  	t
	Verb (intransitive)  			i
	Adjective            	        A
	Adverb               	     	v
	Conjunction             		C
	Preposition             		P
	Interjection            		!
	Pronoun                  	  	r
	Definite Article      		 	D
	Indefinite Article    		 	I
	Nominative              	 	o
	
 */

/**
 * A dictionary for parts of speech, to be used after done with basic capabilities
 * This version:
 * @author Alex Feng
 * @version October 2017
 */
public class Dictionary {
	private final HashMap<String, String> dict;
	
	public Dictionary() throws IOException, FileNotFoundException {
		// reads the text file
		BufferedReader in = new BufferedReader(new FileReader("src/word list/dictionary.txt"));
		
		// init variables
		String str;
		dict = new HashMap<>();
		
		// while not an empty line
		while ((str = in.readLine()) != null) {
			int seperatorIdx = str.indexOf("×"); // ? seperates the word and part of speech
			
			String word = str.substring(0, seperatorIdx).toLowerCase(); // lowercase to match words
			String partOfSpeech = str.substring(seperatorIdx + 1);
			
			dict.put(word, partOfSpeech);
		}
		
		in.close();
	}
	
	public final boolean contains(String word) {
		return dict.containsKey(word);
	}
	
	public final String getPartOfSpeech(String word) {
		return dict.get(word);
	}
}
