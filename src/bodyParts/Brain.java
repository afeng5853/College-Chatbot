package bodyParts;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An abstracted away HashMap with utility functions
 * @author alex feng
 */
public class Brain {
	private HashMap<String, ArrayList<String>> memory = new HashMap<>();
	
	/**
	 * Adds a new key to the hashmap with an empty ArrayList
	 * @param memoryType the name of the key
	 */
	public void addMemoryType(String memoryType) {
		memory.put(memoryType, new ArrayList<String>());
	}
	
	/**
	 * Adds a value to the array list returned at the key location
	 * @param location key of HashMap
	 * @param thing object or value to add at key array
	 */
	public void addToMemory(String location, String thing) {
		ArrayList<String> memoryLocation = memory.get(location);
		memoryLocation.add(thing);
	}
	
	/**
	 * returns the array at key location
	 * @param location the key to the HashMap
	 * @return returns the array at the key location
	 */
	public ArrayList<String> getMemory(String location) {
		return memory.get(location); 
	}
	
	/**
	 * gets the last value of the array at the key location
	 * @param location the key to the HashMap
	 * @return returns the last value of the array at the key location
	 */
	public String getLastMemory(String location) {
		ArrayList<String> currentMemory = memory.get(location);
		return memory.get(location).get(currentMemory.size() - 1); 
	}
	
	/**
	 * deletes the last value of the array at the key location
	 * @param location the key to the HashMap
	 */
	public void deleteLastMemory(String location) {
		ArrayList<String> currentMemory = memory.get(location);
		currentMemory.remove(currentMemory.size()-1);
	}
}
