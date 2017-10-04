package dictionaries;

import java.io.FileNotFoundException;
import java.io.IOException;

import utils.TextFileReader;

public class VerbDictionary extends TextFileReader {

	public VerbDictionary() throws IOException, FileNotFoundException {
		super("src/word list/verblist.txt");
	}

}
