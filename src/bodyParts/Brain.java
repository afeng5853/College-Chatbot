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
	
	public String getLastMemory(String location) {
		ArrayList<String> currentMemory = memory.get(location);
		return memory.get(location).get(currentMemory.size() - 1); 
	}
	
	public void deleteLastMemory(String location) {
		ArrayList<String> currentMemory = memory.get(location);
		currentMemory.remove(currentMemory.size()-1);
	}
}
