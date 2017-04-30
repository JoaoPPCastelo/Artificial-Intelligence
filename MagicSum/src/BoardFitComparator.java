import java.util.Comparator;

class BoardFitComparator implements Comparator<Board> {
	
	public int compare(Board b1, Board b2) {
		
		if (b1.getFitness() > b2.getFitness())
			return -1;
		else if (b1.getFitness() == b2.getFitness())
			return 0;
		else
			return 1;
	}
}