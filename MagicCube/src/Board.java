import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Board {

	private int N;
	private int magicSum;
	private int fitness;
	private ArrayList<Integer> board = new ArrayList<Integer>();
	private Random random;
	
	/**
	 * Construtor para uma board gerada aleatoriamente de tamanho N e com uma determinada soma magica
	 * @param n - tamanho da board
	 * @param magicSum - soma desejada para cada linha, coluna e diagonal
	 */
	public Board(int n, int magicSum) {
		this.N = n;
		this.magicSum = magicSum;
		this.random = new Random();
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

		for (int i = 1; i <= N*N; i++) {
			board.add(i);
		}
		
		for (int i = board.size() - 1; i >= 0; i--) {
			int index = randomGenerator.nextInt(i+1);
			int x = board.get(index);
			board.set(index, board.get(i));
			board.set(i, x);
		}
		
		if (Main.DEBUG)
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

	public ArrayList<Board> crossover(Board b2, int min, int max) {
		ArrayList<Board> childs = new ArrayList<Board>();
		
		ArrayList<Integer> child1 = this.getRepresentation();
		ArrayList<Integer> child2 = b2.getRepresentation();
		
		ArrayList<Integer> sel1 = new ArrayList<Integer>(child2.subList(min, max));
		ArrayList<Integer> sel2 = new ArrayList<Integer>(child1.subList(min, max));
		
		for (int i = min; i < max; i++) {
			child1.remove(min);
			child2.remove(min);
		}
		
		for (int i = 0; i < child1.size(); i++) {
			Integer ia = child1.get(i);
			Integer ib =  find(ia, sel1, sel2);
			if (ia != ib) {
				child1.set(i, ib);
				child2.set(child2.indexOf(ib), ia);
			}
		}
		
		child1.addAll(min, sel1);
		child2.addAll(min, sel2);
		
		childs.add(new Board(this.N, this.magicSum, child1));
		childs.add(new Board(this.N, this.magicSum, child2));
		
		return childs;
	}

	private Integer find(Integer ia, ArrayList<Integer> sel1, ArrayList<Integer> sel2) {
		int index = sel1.indexOf(ia);
		if (index == -1)
			return index;
		return find(sel2.get(index), sel1, sel2);
	}
	
}
