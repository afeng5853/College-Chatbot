package dictionaries;

import java.io.FileNotFoundException;
import java.io.IOException;

import utils.TextFileReader;

public class AdverbDictionary extends TextFileReader {

	public AdverbDictionary() throws IOException, FileNotFoundException {
		super("src/word list/adverblist.txt");
	}

}
