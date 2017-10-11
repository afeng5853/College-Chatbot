package grammar;

import java.io.FileNotFoundException;
import java.io.IOException;

import utils.TextFileReader;

public class GreetingsDictionary extends TextFileReader {

	public GreetingsDictionary() throws IOException, FileNotFoundException {
		super("src/word list/greetings.txt");
	}

}
