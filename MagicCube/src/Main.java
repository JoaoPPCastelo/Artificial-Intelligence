
public class Main {
	
	// -----------------------------------------------------
	// -----------------     VARIABLES     -----------------
	// -----------------------------------------------------
	
	// tamanho inicial da board
	private static int N = 3;
	
	// tempo de execucao (minutos)
	private static int TIME = 2;
	
	// debug
	public static boolean DEBUG = true;
	
	final static long NANOSEC_PER_SEC = 1000*1000*1000;
	
	// populacao a ser usada
	private static int nBOARDS = 6;
	
	// numero de mutacoes a serem efetuadas em cada individuo
	public static int MUTATIONS = 1;
	
	// numero de individuos com melhor fitness que sao mantidos 
	// na populacao e usados para reproducao
	public static int ELITE = 4;
	
	
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
				int magicSum = (int) (((Math.pow(N, 3) + N))/2);
					
				MagicSquare ms = new MagicSquare(nBOARDS, N, magicSum);
				
				ms.initializeGame();
				
				// solve the problem
				Board b = ms.solve();
								
				// compute and print the elapsedTime
				long elapsedTime = System.nanoTime() - startIteration;
				double seconds = (double)elapsedTime / 1000000000.0;
				System.out.println("elapsed time: " + seconds + " seconds. Magic square of size " + N + ":");

				// print the solution
				b.printBoard();
				
				// increase the size of the board
				N++;
			}
		}
		
		if (DEBUG) {
			// start the clock for elapsedTime
			long startIteration = System.nanoTime();

			int magicSum = (int) ((N*(Math.pow(N, 2) + 1))/2);
			
			MagicSquare ms = new MagicSquare(nBOARDS, N, magicSum);
			
			System.out.println("1");

			ms.initializeGame();
			
			System.out.println("2");
			
			// solve the problem
			Board b = ms.solve();
			
			System.out.println("3");
						
			// compute and print the elapsedTime
			long elapsedTime = System.nanoTime() - startIteration;
			double seconds = (double)elapsedTime / 1000000000.0;
			System.out.println("elapsed time: " + seconds + " seconds. Magic square of size " + N + ":");
			
			// print the solution
			b.printBoard();

		}
	}
}