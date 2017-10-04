package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class TextFileReader {
	private final ArrayList<String> dict;
	
	public TextFileReader(String dir) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader(dir)); // reads the text file
		String str;
		
		ArrayList<String> list = new ArrayList<>(); // will contains all of the corresponding part of speech words
		
		// while not an empty line
		while ((str = in.readLine()) != null) {
			list.add(str);
		}
		
		this.dict = list;
		in.close();
	}
	
	public final boolean contains(String word) {
		return dict.contains(word);
	}
}
