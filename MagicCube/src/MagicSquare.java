import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class MagicSquare {
	
	private int nBOARDS;
	private int N;
	private int magicSum;
	private Queue<Board> boards;
	
	Random random = new Random();

	/**
	 * Construtor
	 * @param nBOARDS - numero de boards que compoem a populacao
	 * @param n - tamanho da board
	 * @param magicSum - soma magica pretendida
	 */
	public MagicSquare(int nBOARDS, int N, int magicSum) {
		this.nBOARDS = nBOARDS;
		this.N = N;
		this.magicSum = magicSum;
		boards = new PriorityQueue<Board>(11, new BoardComparator());

	}

	/**
	 * Cria n boards que correspondem a populacao a ser utilizada
	 * @param boards - priorityQueue onde sao guardadas as boards
	 */
	public void initializeGame() {
		for (int i = 0; i < nBOARDS; i++) {
			// initialize and generate a new board 
			Board b = new Board(N, magicSum);
			boards.add(b);
		}	
	}
	
		
	private ArrayList<Board> pmxCrossover(Board b1, Board b2) {
			
		int a = (int) Math.ceil(((N*N) - 1) * random.nextDouble());
		int b = (int) Math.ceil(((N*N) - 1) * random.nextDouble());
		
		ArrayList<Board> childs = b1.crossover(b2, Math.min(a, b), Math.max(a, b));
	
		return childs;
		
	}
	
	/**
	 * Metodo que efetua mutacoes numa board. 
	 * O numero de mutacoes a serem efetuadas e definido na main, 
	 * na variavel MUTATIONS
	 * @param board - board onde sera feita a mutacao
	 */
	private void mutation(ArrayList<Integer> board) {
		
		Stack<Integer> indices = new Stack<Integer>();
		
		for (int i = 0; i < Main.MUTATIONS * 2; i++) {
			while (indices.size() < Main.MUTATIONS * 2) {
				int cromossomeIndex = random.nextInt(N*N);
				if (!indices.contains(cromossomeIndex)) {
					indices.add(cromossomeIndex);
				}
			}
		}
		
		while (!indices.isEmpty()) {
			Integer idx = indices.pop();
			Integer idy = indices.pop();
			Integer temp = board.get(idx);
			board.add(idx, board.get(idy));
			board.add(idy, temp);			
		}
		
	}
	
	/**
	 * Para cada board, verificar se o fitness corresponde a 0 (board com solucao)
	 * @return uma board caso senja encontrada solucao, null caso nao tenha sido encontrada solucao
	 */
	private Board findSolution() {
		for (Board b : boards) {
			if (b.getFitness() == 0) {
				return b;
			}
		}
		return null;
	}	
	
	
	/**
	 * 
	 * @return
	 */
	public Board solve() {
				
			while (findSolution() == null) {
							
				for (int i = 0; i < Main.ELITE; i+=2) {
										
					Board b1 = boards.poll();
					Board b2 = boards.poll();
					
					ArrayList<Board> childs = new ArrayList<Board>();
					
					// crossover
					if (Main.pCrossover >= random.nextDouble()) {
						childs = pmxCrossover(b1, b2);
					}
					else {
						childs.add(b1);
						childs.add(b2);
					}
					
					// para o cyclecrossover
					ArrayList<Integer> child1 = new ArrayList<Integer>(childs.get(0).getRepresentation());
					ArrayList<Integer> child2 = new ArrayList<Integer>(childs.get(1).getRepresentation());
										
					// mutation
					if (Main.pMutation >= random.nextDouble() ) {
						mutation(child1);
						childs.set(0, new Board(this.N, this.magicSum, child1));
						
					}
					
					if (Main.pMutation >= random.nextDouble() ) {
						mutation(child2);
						childs.set(1, new Board(this.N, this.magicSum, child2));

					}
					
					boards.add(childs.get(0));
					boards.add(childs.get(1));
				}
			}
			return findSolution();
		}

}
