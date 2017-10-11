package grammar;

import java.io.FileNotFoundException;
import java.io.IOException;

import utils.TextFileReader;

public class SalutationsDictionary extends TextFileReader {

	public SalutationsDictionary() throws IOException, FileNotFoundException {
		super("src/word list/salutations.txt");
	}

}
