import java.util.Hashtable;

public class Positions {
	Hashtable<String, Integer> coordinates = new Hashtable<String, Integer>();
	
	public Positions() {
		int position = 0;
		
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
		}		
	}
	
	public Integer getPosition(String input) {
		return coordinates.get(input);
	}
	
}
