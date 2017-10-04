package colleges;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import utils.TextFileReader;

public class CollegeParser extends TextFileReader{
	private final String sentence;

	public CollegeParser(String sentence) throws FileNotFoundException, IOException {
		super("colleges.txt");
		this.sentence = sentence;
	}
	
	private ArrayList<String> getColleges() {
		int collegeIdx = sentence.indexOf("College");
		//ToDo
		return null;
	}
}
