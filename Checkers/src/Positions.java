import java.util.Hashtable;

public class Positions {
	Hashtable<String, Integer> coordinates = new Hashtable<String, Integer>();
	Hashtable<Integer, String> invertedCoordinates = new Hashtable<Integer, String>();

	public Positions() {
		int position = 0, index = 0 ;
		
		for (int i = 1; i <= 8; i++) {
			String string1 = "a" + String.valueOf(i);
			String string2 = "b" + String.valueOf(i);
			String string3 = "c" + String.valueOf(i);
			String string4 = "d" + String.valueOf(i);
			String string5 = "e" + String.valueOf(i);
			String string6 = "f" + String.valueOf(i);
			String string7 = "g" + String.valueOf(i);
			String string8 = "h" + String.valueOf(i);
			
			coordinates.put(string1, position++);	
			coordinates.put(string2, position++);
			coordinates.put(string3, position++);
			coordinates.put(string4, position++);
			coordinates.put(string5, position++);
			coordinates.put(string6, position++);
			coordinates.put(string7, position++);
			coordinates.put(string8, position++);
			
			invertedCoordinates.put(index++, string1);
			invertedCoordinates.put(index++, string2);
			invertedCoordinates.put(index++, string3);
			invertedCoordinates.put(index++, string4);
			invertedCoordinates.put(index++, string5);
			invertedCoordinates.put(index++, string6);
			invertedCoordinates.put(index++, string7);
			invertedCoordinates.put(index++, string8);
		}		
	}
	
	public Integer getPosition(String input) {
		return coordinates.get(input);
	}
	
	public String getInvertedPositions(Integer i) {
		return invertedCoordinates.get(i);
	}
}
