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
	
	
	// NAO ESTA A FUNCIONAR!!!!!!!!!!!!!!!!!!!!!!!!!
	// based on: https://commons.apache.org/proper/commons-math/jacoco/org.apache.commons.math4.genetics/CycleCrossover.java.html
	private void cycleCrossover(ArrayList<Integer> parent1, ArrayList<Integer> parent2, ArrayList<Integer> child1, ArrayList<Integer> child2) {
				
		int size = N*N;
		
		// the set of all visited indices so far
        final Set<Integer> visitedIndices = new HashSet<Integer>(size);
	
        // the indices of the current cycle
        final List<Integer> indices = new ArrayList<Integer>(size);
		
        // determine the starting index
		int idx = random.nextInt(size);
		int cycle = 1;
		
		while (visitedIndices.size() < size) {
				
            indices.add(idx);
            
            Integer item = parent2.get(idx);
            idx = parent1.indexOf(item);

            while (idx != indices.get(0)) {
            	            	
                // add that index to the cycle indices
                indices.add(idx);
                // get the item in the second parent at that index
                item = parent2.get(idx);
                // get the index of that item in the first parent
                idx = parent1.indexOf(item);
            }

            // for even cycles: swap the child elements on the indices found in this cycle
            if (cycle++ % 2 != 0) {
            	            	
                for (int i : indices) {                	
                    Integer tmp = child1.get(i);
                    child1.set(i, child2.get(i));
                    child2.set(i, tmp);
                }
            }
          
            visitedIndices.addAll(indices);
            // find next starting index: last one + 1 until we find an unvisited index
            idx = (indices.get(0) + 1) % size;
            while (visitedIndices.contains(idx) && visitedIndices.size() < size) {
                idx++;
                if (idx >= size) {
                    idx = 0;
                }
            }
            indices.clear();
        }
		
		indices.clear();
		
	}
	
	
	
	
	private void crossover(ArrayList<Integer> parent1, ArrayList<Integer> parent2, ArrayList<Integer> child1, ArrayList<Integer> child2) {
	
		
		int crossoverPoint = random.nextInt((N*N)/2 + 5);
		
		for (int i = 0; i < crossoverPoint; i++) {
			child1.add(parent1.get(i));
			child2.add(parent2.get(i));
		}
		
		for (int i = crossoverPoint; i < N*N; i++) {
			child1.add(parent2.get(i));
			child2.add(parent1.get(i));
		}

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
	
	public Board solve() {
		
		Queue<Board> temp = new PriorityQueue<Board>(11, new BoardComparator());
		
			while (findSolution() == null) {
							
				for (int i = 0; i < Main.ELITE; i+=2) {
										
					Board b1 = boards.poll();
					Board b2 = boards.poll();
					
					ArrayList<Integer> parent1 = b1.getRepresentation();
					ArrayList<Integer> parent2 = b2.getRepresentation();
					
					ArrayList<Integer> child1 = new ArrayList<Integer>();
					ArrayList<Integer> child2 = new ArrayList<Integer>();
					
					// crossover
					//cycleCrossover(parent1, parent2, child1, child2);
					crossover(parent1, parent2, child1, child2);
										
					// mutation
					mutation(child1);
					mutation(child2);
										
					Board c1 = new Board(N, magicSum, child1);
					Board c2 = new Board(N, magicSum, child2);
					
					temp.add(b1);
					temp.add(b2);
					temp.add(c1);
					temp.add(c2);
				}
				boards = new PriorityQueue<Board>(temp);
				temp.clear();
			}
			
			return findSolution();
		}
}
