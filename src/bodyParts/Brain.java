package bodyParts;

import java.util.ArrayList;
import java.util.HashMap;

public class Brain {
	private HashMap<String, ArrayList<String>> memory = new HashMap<>();
	
	public void addMemoryType(String memoryType) {
		memory.put(memoryType, new ArrayList<String>());
	}
	
	public void addToMemory(String location, ArrayList<String> things) {
		ArrayList<String> memoryLocation = memory.get(location);
		for (String thing : things) {
			memoryLocation.add(thing);
		}
	}
}
