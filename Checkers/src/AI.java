import java.util.ArrayList;
import java.util.List;

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

	@SuppressWarnings("unused")
	private String minimaxStart(Board b, int depth) {
		// TODO Auto-generated method stub
		
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
			bTemp.
		}
		
		
		return null;
	}

	// TODO lista com moves possiveis
}
