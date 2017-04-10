
public class Main {
	
	// tamanho inicial da board
	private static int N = 10;
	
	// tempo de execucao (minutos)
	private static int TIME = 2;
	
	// debug
	public static boolean DEBUG = true;

	public static void main(String[] args) {
		
		
		if (DEBUG) {
			int magicSum = (int) ((N*(Math.pow(N, 2) + 1))/2);
			Board b = new Board(N, magicSum);
			b.generateBoard();
			b.printBoard();
//			System.out.println();
//			System.out.println("Horizontal sum = " + b.getHorizontalSum(9));
//			System.out.println("Vertical sum = " + b.getVerticalSum(1));
//			System.out.println("Back Diagonal sum = " + b.getBackDiagonalSum());
//			System.out.println("Forward Diagonal sum = " + b.getForwardDiagonalSum());
		}
		

		if (!DEBUG) {
			final long NANOSEC_PER_SEC = 1000l*1000*1000;
	
			long startTime = System.nanoTime();
			while ( (System.nanoTime() - startTime) < TIME * 60 * NANOSEC_PER_SEC){
				
				int magicSum = (int) ((N*(Math.pow(N, 2) + 1))/2);
								
				Board b = new Board(N, magicSum);
				b.generateBoard();
				
				// correr o programa
				b.solve();
				
				// print the final board
				b.printBoard();
				
				// aumentar o tamanho da board
				N++;
			}
		}
	}

}
