package dictionaries;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AdjectiveDictionary extends PartsOfSpeechDictionary {

	public AdjectiveDictionary() throws IOException, FileNotFoundException {
		super("src/word list/adjectivelist.txt");
	}

}
