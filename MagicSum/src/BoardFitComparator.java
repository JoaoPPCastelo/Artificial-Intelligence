import java.util.Comparator;

class BoardFitComparator implements Comparator<Board> {
	
	public int compare(Board b1, Board b2) {
		int b1_fitness = b1.getFitness();
		int b2_fitness = b2.getFitness();
		if (b1_fitness > b2_fitness)
			return -1;
		else if (b1_fitness == b2_fitness)
			return 0;
		else
			return 1;
	}
}