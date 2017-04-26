import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class MagicSquare {
	
	private int nBOARDS;
	private int N;
	private int magicSum;
	private Queue<Board> boards;
	private Random random;

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
		random = new Random();
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
	
	/**
	 * 	
	 * @param b1
	 * @param b2
	 * @return
	 */
	private ArrayList<Board> pmxCrossover(Board b1, Board b2) {
		
		
		int a = (int) Math.ceil(((N*N) - 1) * random.nextDouble());
		int b = (int) Math.ceil(((N*N) - 1) * random.nextDouble());
		
		ArrayList<Board> childs = b1.crossover(b2, Math.min(a, b), Math.max(a, b));
	
		return childs;
		
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
				
				ArrayList<Board> childs = new ArrayList<Board>();
							
				for (int i = 0; i < Main.ELITE; i+=2) {
										
					Board b1 = boards.poll();
					Board b2 = boards.poll();
					
					// crossover
					//if (Main.pCrossover >= random.nextDouble()) {
						childs.addAll(pmxCrossover(b1, b2));
					//}
				
					// manter os pais
					childs.add(b1);
					childs.add(b2);
				}
				
				
				//System.out.println("childs = " + childs.size());

				
				// adicionar os pais e filhos
				for (int i = 0; i < childs.size(); i++) {
					boards.add(childs.get(i));
				}
				
				childs.clear();
				
				// manter apenas os x melhores
				List<Board> temp = new ArrayList<Board>(boards);
				
				//System.out.println("boards = " + boards.size());
				//System.out.println("temp = " + temp.size());
				
				temp = temp.subList(0, Main.nBOARDS);
				boards.clear();
				boards.addAll(temp);
				//temp.clear();
				
				if (findSolution() != null)
					break;
				
				for(Board b : boards) {
					if (Main.pMutation >= random.nextDouble() ) {
						b.mutation();
					}
				}
			}
			return findSolution();
		}

}
