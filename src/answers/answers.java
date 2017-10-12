package answers;
/*
 * Supposed to be a hashmap for answers. WIP
 */
import java.util.HashMap;

public class answers {
	String[] reading = {"B","B","C","D","B"};
	String[] grammar = {"D","B","A","C","B"}; 
	String[] math1 = {"D","C","B","A","B"}; 
	String[] math2 = {"D","B","D","B","107"};  
	
	HashMap<String, String[]> answers = new HashMap<String, String[]>();
	public void generateAnswers()
	{
		answers.put("grammar", grammar);
		answers.put("math1", math1);
		answers.put("math2", math2);
	}
}
