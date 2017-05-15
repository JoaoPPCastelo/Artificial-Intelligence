import java.util.ArrayList;
import java.util.Collections;

public class Board {
	private int N;
	private int fitness;
	private int magicSum;
	private ArrayList<Integer> board;

	/**
	 * Construtor para uma board.
	 * @param a - ArrayList com o conjunto de valores que compoe a board
	 */
	public Board(ArrayList<Integer> a) {
		this.board = a;
		N = (int) Math.sqrt(a.size());
		magicSum = (int) (((Math.pow(N, 3) + N)) / 2);
		computeFitness();
	}

	/**
	 * Metodo que retorna o fitness de uma board
	 * @return fitness da board
	 */
	public int getFitness() {
		return this.fitness;
	}
	
	/**
	 * Imprimir uma board de tamanho N
	 */
	public void printBoard() {
				
		for (int i = 1; i <= N*N; i++) {
			System.out.print("\t" + board.get(i-1));
			if (i % N == 0 && i != 0) {
				System.out.print("\t => " + magicSum);
				System.out.println();
			}
		}
		
		System.out.print(magicSum + "\t");
		
		for (int i = 0; i < N; i++) 
			System.out.print(magicSum + "\t");
		
		System.out.println(magicSum);
	}
	
	/**
	 * Calcula o fitness da board e retorna o valor obtido.
	 * Sao usados como fitness os valores das somas das linhas, das colunas e das diagonais.
	 */
	private void computeFitness() {
		int _fitness = 0;
		int rowSum = 0;
		int colSum = 0;
		
		for (int i = 0; i < N; i++) {
			// linhas
			rowSum += Math.abs(magicSum - getHorizontalSum(i));
			// colunas
			colSum += Math.abs(magicSum - getVerticalSum(i));
		}
		
		_fitness += rowSum;
		_fitness += colSum;
		
		// diagonal /
		_fitness += Math.abs(magicSum - getForwardDiagonalSum());
		
		// diagonal \
		_fitness += Math.abs(magicSum - getBackDiagonalSum());
		
		this.fitness = _fitness;
	}
	
	/**
	 * Obter a soma de uma linha
	 * @param line - numero da linha em questao
	 * @return soma de todos os valores que compoem essa linha
	 */
	private int getHorizontalSum(int line) {
		int sum = 0;
		int start = line*N;
		int limit = line*N+N;
		for (int i = start; i < limit; i++) {
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
		int limit = N*N;
		for (int i = collumn; i < limit; i+=N) {
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
		int limit = N*N;
		for (int i = 0; i < limit; i+=N+1) {
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
		int limit = N*N-N+1;
		for (int i = N - 1; i < limit; i += N-1) {
			sum += board.get(i);
		}
		return sum;
	}
	
	/**
	 * Metodo que retorna um ArrayList com os valores que compoe uma board
	 * @return representacao de uma board
	 */
	public ArrayList<Integer> getRepresentation() {
		return this.board;
	}

	/**
	 * Metodo que retorna uma copia de uma board
	 * @return copia de uma board
	 */
	public Board copy() {
		return new Board(new ArrayList<Integer>(board));
	}
	
	/**
	 * Metodo que realiza o crossover entre duas boards
	 * @param b2 - segunda board envolvida no crossover
	 * @param min - indice minimo da porcao da board que nao ira sofrer alteracao
	 * @param max - indice maximo da porcao da board que nao ira sofrer alteracao
	 * @return ArrayList com duas novas boards criadas a partir do crossover
	 */
	public ArrayList<Board> crossover(Board b2, int min, int max) {
		
		ArrayList<Board> result = new ArrayList<Board>();
		
		// child1
		ArrayList<Integer> a = new ArrayList<Integer>(this.getRepresentation());
		
		// child2
		ArrayList<Integer> b = new ArrayList<Integer>(b2.getRepresentation());
		
		ArrayList<Integer> sela = new ArrayList<Integer>(b2.getRepresentation().subList(min, max));
		ArrayList<Integer> selb = new ArrayList<Integer>(this.getRepresentation().subList(min, max));

		for (int i = min; i < max; i++) {
			a.remove(min);
			b.remove(min);
		}

		for (int i = 0; i < a.size(); i++) {
			Integer ia = a.get(i);
			Integer ib = find(ia, sela, selb);
			if (ia != ib) {
				a.set(i, ib);
				b.set(b.indexOf(ib), ia);
			}
		}

		a.addAll(min, sela);
		b.addAll(min, selb);
		
		result.add(new Board(a));
		result.add(new Board(b));
		
		return result;
	}
	
	/**
	 * Metodo auxiliar para procurar indices de valores das porcoes selecionadas de um arrayList
	 * @param value - valor a ser procurado
	 * @param sela - porcao do filho 1
	 * @param selb - porcao do filho 2
	 * @return - valor pretendido
	 */
	private Integer find(Integer value, ArrayList<Integer> sela, ArrayList<Integer> selb) {
		int x = sela.indexOf(value);
		if (x == -1)
			return value;
		return find(selb.get(x), sela, selb);
	}

	/**
	 * Metodo que realiza uma mutacao na board. Troca os valores dos indices a e b
	 * @param a - valor a ser trocado com b
	 * @param b - valor a ser trocado com a
	 */
	public void mutation(int a, int b) {
		Collections.swap(board, a, b);
		computeFitness();
	}
}
