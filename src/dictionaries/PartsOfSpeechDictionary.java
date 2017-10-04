package dictionaries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class PartsOfSpeechDictionary {
	private final ArrayList<String> dict;
	
	public PartsOfSpeechDictionary(String dir) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader(dir));
		String str;
		
		ArrayList<String> list = new ArrayList<>();
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
