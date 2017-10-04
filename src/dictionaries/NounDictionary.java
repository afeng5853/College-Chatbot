package dictionaries;

import java.io.FileNotFoundException;
import java.io.IOException;

import utils.TextFileReader;

public class NounDictionary extends TextFileReader {

	public NounDictionary() throws IOException, FileNotFoundException {
		super("src/word list/nounlist.txt");
	}

}
