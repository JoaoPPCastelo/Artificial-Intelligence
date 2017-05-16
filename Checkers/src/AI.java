import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AI {
	
	private int depth;
	
	public AI(int depth) {
		this.depth = depth;
	}
	
	public String makeMovement(Board b, Positions pos) {
				
		// TODO escolher um mov
		String mov = minimaxStart(b, depth);
		
		String[] movs = mov.split(" ");
		Integer actPos = Integer.getInteger(movs[0]);
		Integer nxtPos = Integer.getInteger(movs[1]);
		
		// DONE efetuar mov
		b.move(actPos, nxtPos);	
		
		return mov;
	}

	/**
	 * 
	 * @param b
	 * @param depth
	 * @return
	 */
	private String minimaxStart(Board b, int depth) {
		// TODO Auto-generated method stub
		
		boolean player = Game.isUserPlaying;
		
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		
		ArrayList<String> possibleMovements;
		
		possibleMovements = b.allValidMovements();
		
		// skiping point????
		
		ArrayList<Double> scores = new ArrayList<Double>();
		
		if (possibleMovements.isEmpty())
			return null;
		
		Board bTemp = null;
		for (String s : possibleMovements) {
			bTemp = new Board(b.getBoardRepresentation());
			String[] index = s.split(" ");
			bTemp.move(Integer.getInteger(index[0]), Integer.getInteger(index[1]));
			scores.add(minimax(bTemp, depth-1, alpha, beta, !player));
		}
				
		double maxScore = Collections.max(scores);
		
		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i) < maxScore) {
				scores.remove(i);
				possibleMovements.remove(i);
				i--;
			}
		}
			
		Random random = new Random();
		
		return possibleMovements.get(random.nextInt(possibleMovements.size()));
	}

	// TODO lista com moves possiveis
}
