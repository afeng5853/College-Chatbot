package bodyParts;

import java.util.ArrayList;
import java.util.HashMap;

public class Brain {
	private HashMap<String, ArrayList<String>> memory = new HashMap<>();
	
	public void addMemoryType(String memoryType) {
		memory.put(memoryType, new ArrayList<String>());
	}
	
	public void addToMemory(String location, String thing) {
		ArrayList<String> memoryLocation = memory.get(location);
		memoryLocation.add(thing);
	}
	
	public ArrayList<String> getMemory(String location) {
		return memory.get(location);
	}
}
