package dictionaries;

import java.io.FileNotFoundException;
import java.io.IOException;

import utils.TextFileReader;

public class AdjectiveDictionary extends TextFileReader {

	public AdjectiveDictionary() throws IOException, FileNotFoundException {
		super("src/word list/adjectivelist.txt");
	}

}
