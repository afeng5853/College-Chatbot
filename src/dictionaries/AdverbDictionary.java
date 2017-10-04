package dictionaries;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AdverbDictionary extends PartsOfSpeechDictionary {

	public AdverbDictionary() throws IOException, FileNotFoundException {
		super("src/word list/adverblist.txt");
	}

}
