import java.util.Comparator;

/**
 * Metodo que compara dois nos, de forma a organizar a queue de nos abertos com base no no com menor custo
 */
public class NodeComparator implements Comparator<AStar.Node> {
	public int compare(AStar.Node s1, AStar.Node s2) {
		int Score1 = s1.getF();
		int Score2 = s2.getF();
		if (Score1 > Score2) 
			return 1;
		else if (Score1 == Score2) 
			return 0;
		else 
			return -1;
	}
}
