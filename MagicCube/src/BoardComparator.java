import java.util.Comparator;

public class BoardComparator implements Comparator<Board> {
	public int compare(Board b1,Board b2) {
		int fitness1 = b1.getFitness();
		int fitness2 = b2.getFitness();
		if (fitness1 > fitness2) 
			return 1;
		else if (fitness1 == fitness2) 
			return 0;
		else 
			return -1;
	}
}
