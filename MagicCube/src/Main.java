
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
	
	final static long NANOSEC_PER_SEC = 1000l*1000*1000;
	
	
	// -----------------------------------------------------
	// -------------------     MAIN     --------------------
	// -----------------------------------------------------
	public static void main(String[] args) {
				
		if (!DEBUG) {
			
			// run the program for TIME minutes
			long startTime = System.nanoTime();
			while ( (System.nanoTime() - startTime) < TIME * 60 * NANOSEC_PER_SEC){
				
				// start the clock for elapsedTime
				long startIteration = System.nanoTime();
				
				// compute the magic sum
				int magicSum = (int) ((N*(Math.pow(N, 2) + 1))/2);
					
				// initialize and generate a new board
				Board b = new Board(N, magicSum);
				b.generateBoard();
				
				// solve the problem
				//b.solve();
				
				// compute and print the elapsedTime
				long elapsedTime = System.nanoTime() - startIteration;
				System.out.println("elapsed time: " + elapsedTime + " . Magic square of size " + N + ":");
				
				// print the final board
				b.printBoard();
				
				// increase the size of the board
				N++;
			}
		}
		
		if (DEBUG) {
			System.out.println("Quadrado magico de ordem = " + N);
			int magicSum = (int) ((N*(Math.pow(N, 2) + 1))/2);
			Board b = new Board(N, magicSum);
			b.generateBoard();
			b.printBoard();
			
//			System.out.println("----------------------------------------------------");
//			System.out.println("Horizontal sum = " + b.getHorizontalSum(9));
//			System.out.println("Vertical sum = " + b.getVerticalSum(1));
//			System.out.println("Back Diagonal sum = " + b.getBackDiagonalSum());
//			System.out.println("Forward Diagonal sum = " + b.getForwardDiagonalSum());
		}
	}
}