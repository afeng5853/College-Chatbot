package dictionaries;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NounDictionary extends PartsOfSpeechDictionary {

	public NounDictionary() throws IOException, FileNotFoundException {
		super("src/word list/nounlist.txt");
	}

}
