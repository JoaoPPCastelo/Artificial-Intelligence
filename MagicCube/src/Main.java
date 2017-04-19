import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
	
	// -----------------------------------------------------
	// -----------------     VARIABLES     -----------------
	// -----------------------------------------------------
	
	// tamanho inicial da board
	private static int N = 10;
	
	// tempo de execucao (minutos)
	private static int TIME = 2;
	
	// debug
	public static boolean DEBUG = true;
	
	final static long NANOSEC_PER_SEC = 1000*1000*1000;
	
	private static int nBOARDS = 6;
	
	
	// -----------------------------------------------------
	// -------------------     MAIN     --------------------
	// -----------------------------------------------------
	public static void main(String[] args) {
				
		if (!DEBUG) {
			
			// run the program for TIME minutes
			long startTime = System.nanoTime();
			while ( (System.nanoTime() - startTime) < TIME * 60 * NANOSEC_PER_SEC) {
				
				// start the clock for elapsedTime
				long startIteration = System.nanoTime();
				
				// compute the magic sum
				int magicSum = (int) ((N*(Math.pow(N, 2) + 1))/2);
					
				// initialize and generate a new board
				Board b = new Board(N, magicSum);
				b.generateBoard();
				
				Board b1 = new Board(N, magicSum);
				b1.generateBoard();
				
				Board b2 = new Board(N, magicSum);
				b2.generateBoard();
				
				Board b3 = new Board(N, magicSum);
				b3.generateBoard();
				
				Board b4 = new Board(N, magicSum);
				b4.generateBoard();
				
				Board b5 = new Board(N, magicSum);
				b5.generateBoard();
				
				// solve the problem
				// b.solve();
				
				// compute and print the elapsedTime
				long elapsedTime = System.nanoTime() - startIteration;
				double seconds = (double)elapsedTime / 1000000000.0;
				System.out.println("elapsed time: " + seconds + " seconds. Magic square of size " + N + ":");

				// print the final board
				b.printBoard();
				
				System.out.println();
				
				b1.printBoard();
				
				System.out.println();
				
				b2.printBoard();
				
				System.out.println();
				
				b3.printBoard();
				
				System.out.println();
				
				b4.printBoard();
				
				System.out.println();
				
				b5.printBoard();
				
				System.out.println();
				
				// increase the size of the board
				N++;
			}
		}
		
		if (DEBUG) {
			// start the clock for elapsedTime
			long startIteration = System.nanoTime();

			int magicSum = (int) ((N*(Math.pow(N, 2) + 1))/2);
			
			//Queue<Board> boards = new PriorityQueue<Board>(11, new BoardComparator());
			
			//MagicSquare ms = new MagicSquare();
			
			// ms.initializeGame(nBOARDS, boards, N, magicSum);
			
						
			// initialize and generate a new board
			Board b = new Board(N, magicSum);
			b.generateBoard();
			//b.computeFitness();
//			boards.add(b);
//
//			
//			
			Board b1 = new Board(N, magicSum);
			b1.generateBoard();
			//b1.computeFitness();
//			boards.add(b1);
//			
			Board b2 = new Board(N, magicSum);
			b2.generateBoard();
			//b2.computeFitness();
//			boards.add(b2);
//			
			Board b3 = new Board(N, magicSum);
			b3.generateBoard();
			//b3.computeFitness();
//			boards.add(b3);
//			
			Board b4 = new Board(N, magicSum);
			b4.generateBoard();
			//b4.computeFitness();
//			boards.add(b4);
//			
			Board b5 = new Board(N, magicSum);
			b5.generateBoard();
			//b5.computeFitness();
//			boards.add(b5);
			
			// compute and print the elapsedTime
			long elapsedTime = System.nanoTime() - startIteration;
			double seconds = (double)elapsedTime / 1000000000.0;
			System.out.println("elapsed time: " + seconds + " seconds. Magic square of size " + N + ":");
			
			
//			ArrayList<Integer> mins = new ArrayList<Integer>();
//			mins.add(b.fitness);
//			mins.add(b1.fitness);
//			mins.add(b2.fitness);
//			mins.add(b3.fitness);
//			mins.add(b4.fitness);
//			mins.add(b5.fitness);
//			
			
			// print the final board
//			b.printBoard();
//			
//			System.out.println();
//			
//			b1.printBoard();
//			
//			System.out.println();
//			
//			b2.printBoard();
//			
//			System.out.println();
//			
//			b3.printBoard();
//			
//			System.out.println();
//			
//			b4.printBoard();
//			
//			System.out.println();
//			
//			b5.printBoard();
//			
//			System.out.println();

		

			
			System.out.println("\n------------------------\n");
			
			
			
//			for (int i = 0; i < 3; i++) {
//				boards.poll().printBoard();
//			}
//			
			
			
			//b.select();
			
//			System.out.println("----------------------------------------------------");
//			System.out.println("Horizontal sum = " + b.getHorizontalSum(9));
//			System.out.println("Vertical sum = " + b.getVerticalSum(1));
//			System.out.println("Back Diagonal sum = " + b.getBackDiagonalSum());
//			System.out.println("Forward Diagonal sum = " + b.getForwardDiagonalSum());
		}
	}
}