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
			b.generateBoard();
			boards.add(b);
					
		}	
	}
	
	
	// based on: https://commons.apache.org/proper/commons-math/jacoco/org.apache.commons.math4.genetics/CycleCrossover.java.html
	public void crossover(ArrayList<Integer> parent1, ArrayList<Integer> parent2, ArrayList<Integer> child1, ArrayList<Integer> child2) {
				
		int size = N*N;
		
		// the set of all visited indices so far
        final Set<Integer> visitedIndices = new HashSet<Integer>(size);
	
     // the indices of the current cycle
        final List<Integer> indices = new ArrayList<Integer>(size);
		
        // determine the starting index
		Random random = new Random();
		int idx = random.nextInt(size);
		int cycle = 1;
		
		while (visitedIndices.size() < size) {
			
			System.out.println("8");
			
            indices.add(idx);

            Integer item = parent2.get(idx);
            idx = parent1.indexOf(item);

            while (idx != indices.get(0)) {
            	
            	System.out.println("9");
            	
                // add that index to the cycle indices
                indices.add(idx);
                // get the item in the second parent at that index
                item = parent2.get(idx);
                // get the index of that item in the first parent
                idx = parent1.indexOf(item);
            }

            // for even cycles: swap the child elements on the indices found in this cycle
            if (cycle++ % 2 != 0) {
            	
            	System.out.println("10");
            	
                for (int i : indices) {
                	
                	System.out.println("11");
                	
                    Integer tmp = child1.get(i);
                    child1.set(i, child2.get(i));
                    child2.set(i, tmp);
                }
            }

            System.out.println("12");
            
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
		
	}
	
	private void mutation(ArrayList<Integer> al) {
		
		Random random = new Random();
		Stack<Integer> indices = new Stack<Integer>();
		
		for (int i = 0; i < Main.MUTATIONS * 2; i++) {
			int cromossomeIndex = random.nextInt(N*N);
			if (!indices.contains(cromossomeIndex)) {
				indices.add(cromossomeIndex);
			}
		}
		
		while (!indices.isEmpty()) {
			Integer idx = indices.pop();
			Integer idy = indices.pop();
			Integer temp = al.get(idx);
			al.add(idx, al.get(idy));
			al.add(idy, temp);			
		}
	}
	
	
	public Board solve() {
		
		Queue<Board> temp = new PriorityQueue<Board>(11, new BoardComparator());
		
			while (findSolution() == null) {
				
				System.out.println("4");
				
				for (int i = 0; i < Main.ELITE; i+=2) {
					
					System.out.println("5");
					
					Board b1 = boards.poll();
					Board b2 = boards.poll();
					
					b1.printBoard();
					
					b2.printBoard();
					
					
					ArrayList<Integer> parent1 = b1.getRepresentation();
					ArrayList<Integer> parent2 = b2.getRepresentation();
					
					ArrayList<Integer> child1 = new ArrayList<Integer>(parent1);
					ArrayList<Integer> child2 = new ArrayList<Integer>(parent2);
					
					// crossover
					crossover(parent1, parent2, child1, child2);
					
					System.out.println("6");
					
					// mutation
					mutation(child1);
					mutation(child2);
					
					System.out.println("7");
					
					Board c1 = new Board(N, magicSum, child1);
					Board c2 = new Board(N, magicSum, child2);
					
					temp.add(b1);
					temp.add(b2);
					temp.add(c1);
					temp.add(c2);
				}
				boards = temp;
			}
			
			return findSolution();
		}
		
	
	/**
	 * Para cada board, verificar se o 
	 * @param boards
	 * @return 
	 */
	private Board findSolution() {
		for (Board b : boards) {
			if (b.getFitness() == 0) {
				return b;
			}
		}
		return null;
	}	
}
