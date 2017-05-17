import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AI {
	
	private int depth;
	
	public AI(int depth) {
		this.depth = depth;
	}
	
	public String makeMovement(Board b, Positions pos) {
				
		// escolher um mov
		String mov = minimaxStart(b, depth);
		
		//System.out.println("make mov = " + mov);
		
		String[] movs = mov.split(" ");
		
		// efetuar mov
		b.move(Integer.parseInt(movs[0]), Integer.parseInt(movs[1]));	
		
		return mov;
	}

	/**
	 * 
	 * @param b
	 * @param depth
	 * @return
	 */
	private String minimaxStart(Board b, int depth) {
		
		boolean player = Game.isUserPlaying;
		
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		
		ArrayList<String> possibleMovements;
		
		possibleMovements = b.allValidMovements(player);
		
		// skiping point????
		
		ArrayList<Double> scores = new ArrayList<Double>();
		
		if (possibleMovements.isEmpty())
			return null;
		
		Board bTemp = null;
		for (String s : possibleMovements) {
			if (s != null) {
				bTemp = new Board(b.getBoardRepresentation());
				String[] index = s.split(" ");
								
				bTemp.move(Integer.parseInt(index[0]), Integer.parseInt(index[1]));
				scores.add(minimax(bTemp, depth-1, alpha, beta, !player));
			}
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
		
		for (String s : possibleMovements)
			System.out.println(s);
		
		return possibleMovements.get(random.nextInt(possibleMovements.size()));
	}

	/**
	 * 
	 * @param b
	 * @param i
	 * @param alpha
	 * @param beta
	 * @param player
	 * @return
	 */
	private Double minimax(Board b, int depth, double alpha, double beta, boolean player) {
		
		if(depth == 0) 
			return getScore(b, player);
		
		ArrayList<String> possibleMovements = b.allValidMovements(player);
		
		Board bTemp = null;
		
		double value;
		
		if (player) {
			value  = Double.NEGATIVE_INFINITY;
			
			for (int i = 0; i < possibleMovements.size(); i++) {
				
				bTemp = new Board(b.getBoardRepresentation());
				
				String[] index = possibleMovements.get(i).split(" ");
				
				bTemp.move(Integer.parseInt(index[0]), Integer.parseInt(index[1]));
				
				double result = minimax(bTemp, depth - 1, alpha, beta, !player);
				
				value = Math.max(result, value);
				alpha = Math.min(alpha, value);
				
				if (alpha >= beta)
					break;	
			}
		}
		else {
			value = Double.POSITIVE_INFINITY;
			
			for (int i = 0; i < possibleMovements.size(); i++) {
				bTemp = new Board(b.getBoardRepresentation());
				
				String[] index = possibleMovements.get(i).split(" ");
				
				bTemp.move(Integer.parseInt(index[0]), Integer.parseInt(index[1]));
				
				double result = minimax(bTemp, depth - 1, alpha, beta, !player);
				
				value = Math.max(result, value);
				alpha = Math.min(alpha, value);
				
				if (alpha >= beta)
					break;	
			}
		}
		return value;
	}

	/**
	 * 
	 * @param b
	 * @param player
	 * @return
	 */
	private Double getScore(Board b, boolean player) {

		double dama = 1.2;
		
		if (player)
			return b.getWhiteDamas() * dama + b.getWhitePieces() - b.getBlackDamas() * dama - b.getBlackPieces(); 
		else
			return b.getBlackDamas() * dama + b.getBlackDamas() - b.getWhiteDamas() * dama - b.getWhitePieces(); 
		
	}

}
