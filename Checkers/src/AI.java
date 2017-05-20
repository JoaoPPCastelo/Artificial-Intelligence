import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AI 
{
	private int depth;
	private boolean additional_move = false;
	private int skippingIndex = -1;
	
	
	/**
	 * Construtor da AI
	 * @param depth - numero de jogadas que a AI ve a frente
	 */
	public AI(int depth) 
	{
		this.depth = depth;
	}
	
	
	/**
	 * Realiza um movimento da AI escolhido pelo minimax
	 * @param b - board onde o movimento sera feito
	 * @param pos - HashTable para conversao de indices em coordenadas e vice versa
	 * @return string com as cordenadas usadas para efetuar o movimento
	 */
	public String makeMovement(Board b, Positions pos) 
	{
		// escolher um movimento
		String mov = minimaxStart(b, depth);
		
		String[] movs = mov.split(" ");
		
		// efetuar movimento escolhido
		b.move(Integer.parseInt(movs[0]), Integer.parseInt(movs[1]));
		
		if (additional_move == true)
			skippingIndex = Integer.parseInt(movs[1]);
		
		return mov;
	}

	/**
	 * Metodo que escolhe um movimento para ser feito pela AI
	 * @param b - board a ser usada
	 * @param depth - profundidade que sera usada para o calculo das jogadas
	 * @return string com o movimento escolhido
	 */
	private String minimaxStart(Board b, int depth) 
	{
		boolean player = Game.isUserPlaying;
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		
		ArrayList<String> possibleMovements;
		if (skippingIndex == -1)
			possibleMovements = b.allValidMovements(player);
		else {
			possibleMovements = b.getValidSkipMovements(skippingIndex, player);
			skippingIndex = -1;
		}
		
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
		
		return possibleMovements.get(random.nextInt(possibleMovements.size()));
	}

	
	/**
	 * Metodo que simula jogadas de forma a encontrar a melhor jogada a ser feita pela AI
	 * @param b - board a ser usada
	 * @param i - profundidade a usar para o calculo das jogadas
	 * @param alpha
	 * @param beta
	 * @param player - jogador envolvido na simulacao
	 * @return valor
	 */
	private Double minimax(Board b, int depth, double alpha, double beta, boolean player) 
	{
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
				alpha = Math.max(alpha, value);
				
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
				
				value = Math.min(result, value);
				alpha = Math.min(alpha, value);
				
				if (alpha >= beta)
					break;	
			}
		}
		return value;
	}

	
	/**
	 * Calculo de uma pontuacao para um dado jogador
	 * @param b - board a ser usada
	 * @param player - jogador
	 * @return valor que representa a pontuacao de um jogador com base na heuristica usada
	 */
	private Double getScore(Board b, boolean player) 
	{
		double dama = 1;
		
		if (player)
			return b.getWhiteDamas() * dama + b.getWhitePieces() - b.getBlackDamas() * dama - b.getBlackPieces(); 
		else
			return b.getBlackDamas() * dama + b.getBlackDamas() - b.getWhiteDamas() * dama - b.getWhitePieces(); 
	}
}
