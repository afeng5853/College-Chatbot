package dictionaries;

import java.io.FileNotFoundException;
import java.io.IOException;

import utils.TextFileReader;

public class FinanceDictionary extends TextFileReader {

	public FinanceDictionary() throws IOException, FileNotFoundException {
		super("src/word list/financelist.txt");
	}

}
