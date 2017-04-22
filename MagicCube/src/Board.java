import java.util.ArrayList;
import java.util.Random;

public class Board {

	private int N;
	private int magicSum;
	private int fitness;
	private ArrayList<Integer> board = new ArrayList<Integer>();
	
	/**
	 * Construtor para uma board gerada aleatoriamente de tamanho N e com uma determinada soma magica
	 * @param n - tamanho da board
	 * @param magicSum - soma desejada para cada linha, coluna e diagonal
	 */
	public Board(int n, int magicSum) {
		this.N = n;
		this.magicSum = magicSum;
		generateBoard();
		this.fitness = computeFitness();
	}
	
	/**
	 * Construtor para uma board definida de tamanho N e com uma determinada soma magica
	 * @param n - tamanho da board
	 * @param magicSum - soma desejada para cada linha, coluna e diagonal
	 * @param board - representacao da board
	 */
	public Board(int n, int magicSum, ArrayList<Integer> board) {
		this.N = n;
		this.magicSum = magicSum;
		this.board = board;
		this.fitness = computeFitness();
	}
	
	/**
	 * Metodo que retorna o fitness de uma board
	 * @return fitness da board
	 */
	public int getFitness() {
		return this.fitness;
	}
	
	/**
	 * Metodo que retorna a representacao de uma board
	 * @return representacao da board
	 */
	public ArrayList<Integer> getRepresentation() {
		return this.board;
	}
	
	/**
	 * Obter a soma de uma linha
	 * @param line - numero da linha em questao
	 * @return soma de todos os valores que compoem essa linha
	 */
	private int getHorizontalSum(int line) {
		int sum = 0;
		for (int i = line * N; i < line*N + N; i++) {
			sum += board.get(i);
		}
		return sum;
	}
	
	/**
	 * Obter a soma de uma coluna
	 * @param collumn - numero da coluna em questao
	 * @return soma de todos os valores que compoem essa coluna
	 */
	private int getVerticalSum(int collumn) {
		int sum = 0;
		for (int i = collumn; i < N*N; i+=N) {
			sum += board.get(i);
		}
		return sum;
	}
	
	/**
	 * Obter a soma da diagonal \
	 * @return soma de todos os valores que compoem essa diagonal
	 */
	private int getBackDiagonalSum() {
		int sum = 0;
		for (int i = 0; i < N*N; i+=N+1) {
			sum += board.get(i);
		}
		return sum;
	}
	
	/**
	 * Obter a soma da diagonal /
	 * @return soma de todos os valores que compoem essa diagonal
	 */
	private int getForwardDiagonalSum() {
		int sum = 0;
		for (int i = N-1; i < N*N - N+1; i+=N-1) {
			sum += board.get(i);
		}
		return sum;
	}
		
	/**
	 * Gerar uma nova board com numeros aleatorios e nao repetidos
	 */
	public void generateBoard() {

		Random randomGenerator = new Random();

//		for (int i = 1; i <= N*N; i++) {
//			board.add(i);
//		}
//		
//		for (int i = board.size() - 1; i >= 0; i--) {
//			int index = randomGenerator.nextInt(i+1);
//			int x = board.get(index);
//			board.add(index, board.get(i));
//			board.add(i, x);
//		}
		
		while (board.size() < N*N) {
			int random = randomGenerator.nextInt(N*N) + 1;
			if (!board.contains(random)) 
				board.add(random);
		}
		
		System.out.println("Board created: " + board.toString());
		
	}
	
	/**
	 * Imprimir uma board de tamanho N
	 */
	public void printBoard() {
		
		int line = 0;
		
		for (int i = 1; i <= N*N; i++) {
			System.out.print("\t" + board.get(i-1));
			if (i % N == 0 && i != 0) {
				System.out.print("\t => " + getHorizontalSum(line++));
				System.out.println();
			}
		}
		
		System.out.print(getForwardDiagonalSum() + "\t");
		
		for (int i = 0; i < N; i++) {
			System.out.print(getVerticalSum(i) + "\t");
		}
		
		System.out.println(getBackDiagonalSum());
	}
	
	/**
	 * Calcula o fitness da board e retorna o valor obtido.
	 * Sao usados como fitness os valores das somas das linhas, das colunas e das diagonais.
	 */
	public int computeFitness() {
		
		int _fitness = 0;
		
		// linhas
		for (int i = 0; i < N; i++) {
			_fitness += Math.abs(magicSum - getHorizontalSum(i));
		}
		
		// colunas
		for (int i = 0; i < N; i++) {
			_fitness += Math.abs(magicSum - getVerticalSum(i));
		}
		
		//  diagonal /
		_fitness += Math.abs(magicSum - getForwardDiagonalSum());
		
		// diagonal \
		_fitness += Math.abs(magicSum - getBackDiagonalSum());
		
		return _fitness;
	}
//	
//	/**
//	 * Metodo que seleciona os N/2 melhores candidatos
//	 */
//	public void select() {
//		
//		ArrayList<Integer> temp = new ArrayList<Integer>();
//		ArrayList<Integer> a = new ArrayList<Integer>();
//
////		for (int i = 0; i < N; i++) {
////			temp.add(Math.abs(fitness.get(i) - magicSum));
////		}
//		
//		if (Main.DEBUG) {
//			for (Integer t : temp)
//				System.out.println("temp = " + t + " ");
//		}
//		
//		for (int i = 0; i < N/2; i++) {
//			int min = Collections.min(temp);
//			
//			if (Main.DEBUG)
//				System.out.println("min = " + min);
//			
//			a.add(temp.indexOf(min));
//			
//			if (Main.DEBUG)
//				System.out.println("--> " + temp.indexOf(min));
//			
//			temp.set(temp.indexOf(min), 10000000);
//
//		}
//		
//		if (Main.DEBUG) {
//			for (int i = 0; i < N; i++) {
//				System.out.println(getHorizontalSum(i));
//			}
//			
//			for (Integer index : a) {
//				for (int i = index * N; i < index*N + N; i++) {
//					System.out.print(board.get(i) + " ");
//				}
//				System.out.println();
//			}
//		}
//	}
}
