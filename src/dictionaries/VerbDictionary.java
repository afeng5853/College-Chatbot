package dictionaries;

import java.io.FileNotFoundException;
import java.io.IOException;

public class VerbDictionary extends PartsOfSpeechDictionary {

	public VerbDictionary() throws IOException, FileNotFoundException {
		super("src/word list/verblist.txt");
	}

}
