
public class Main {	
	
	// -----------------------------------------------------
	// -----------------     VARIABLES     -----------------
	// -----------------------------------------------------
		
	//tamanho inicial da board
	private static int N = 5;
	
	// probabilidade de ocorrer mutacao
	public static double pMutation = 0.40;
	
	// probabilidade de ocorrer crossover
	public static double pCrossover = 0.10;
	
	// numero de individuos com melhor fitness 
	public static int ELITE = 2;
	
	// tempo de execucao (minutos)
	private static int TIME = 2;
	
	final static long NANOSEC_PER_SEC = 1000*1000*1000;
	
	
	// -----------------------------------------------------
	// -------------------     MAIN     --------------------
	// -----------------------------------------------------
	public static void main(String[] args){
					
		// run the program for approximately TIME minutes
		long startClock = System.nanoTime();
		
		while ((System.nanoTime() - startClock) < TIME * 60 * NANOSEC_PER_SEC) {
			
			if (N > 9) {
				pMutation = 0.42;
				pCrossover = 0.13;	
			}
			
			// start the clock for elapsedTime
			long startTime = System.nanoTime();
			
			// set the board length. ex: for a board of 3, the length is 9
			int boardLength = N*N;
			
			// set the number of elements that makes a population 
			int populationSize = boardLength/2;		

			// creates a new population
			Population p = new Population(populationSize, boardLength);	
				
			// solve the problem
			Board b = p.solve(ELITE, pMutation, pCrossover);
			
			// compute and print the elapsedTime
			long elapsedTime = System.nanoTime() - startTime;
			double seconds = (double)elapsedTime / 1000000000.0;
			
			
			long TotalelapsedTime = System.nanoTime() - startClock;
			double Totalseconds = (double)TotalelapsedTime / 1000000000.0;
			
			System.out.println("elapsed time: " + seconds + " seconds. " + " Total time = " + Totalseconds + " Magic square of size " + N + ":");
			
			// print the board
			//b.printBoard();
			
			// increment the board size
			N++;
		}
		
 	} 
}