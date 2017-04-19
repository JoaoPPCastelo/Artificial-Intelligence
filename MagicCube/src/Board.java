import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Board {

	private int N;
	private int magicSum;
	private int fitness;
	private ArrayList<Integer> board = new ArrayList<Integer>();
	
	/**
	 * Construtor para uma board de tamanho N e com uma determinada soma magica
	 * @param n - tamanho da board
	 * @param magicSum - soma desejada para cada linha, coluna e diagonal
	 */
	public Board(int n, int magicSum) {
		this.N = n;
		this.magicSum = magicSum;
		this.fitness = 0;
	}
	
	public int getFitness() {
		return this.fitness;
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
	 * Verifica se a board em questao e soluccao.
	 * Verififica se existe alguma linha ou coluna cuja soma nao corresponda a soma magica. Caso esse
	 *  caso aconteca, e retornado false. Caso todas as linhas e colunas apresentem uma soma igual a
	 *  magicSum, e verificado se a soma das diagonais e diferente da magicSum. Caso seja, e retornado false,
	 *  caso contrario, todas as linhas, colunas e diagonais verificam a magicSum e entao e retornado true.   
	 * @return true se a board e solucao, false caso nao seja
	 */
	private boolean isSolution() {
		for(int i = 0; i < N; i++){
			if (getHorizontalSum(i) != magicSum ||
					getVerticalSum(i) != magicSum)
				return false;
		}
		if ((getBackDiagonalSum() != magicSum) || (getForwardDiagonalSum() != magicSum))
			return false;
			
		return true;
	}
	
	
	// TODO procurar + eficiente
	/**
	 * Gerar uma nova board com numeros aleatorios e nao repetidos
	 */
	public void generateBoard() {
		
		long startIteration = System.nanoTime();
		
		Random randomGenerator = new Random();

		for (int i = 1; i <= N*N; i++) {
			board.add(i);
		}
		
		for (int i = board.size() - 1; i >= 0; i--) {
			int index = randomGenerator.nextInt(i+1);
			int x = board.get(index);
			board.add(index, board.get(i));
			board.add(i, x);
		}
		
		long elapsedTime = System.nanoTime() - startIteration;
		double seconds = (double)elapsedTime / 1000000000.0;
		System.out.println("elapsed time to generate : " + seconds);
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
		
		System.out.println("Fitness = " + this.fitness);
	}
	
	/**
	 * Calcula o fitness da board e guarda esses valores num arrayList.
	 * Sao usados como fitness os valores das somas das linhas, das colunas e das diagonais.
	 */
	public void computeFitness() {
		
		int _fitness = 0;
		
		// linhas
		for (int i = 0; i < N; i++) {
			_fitness += Math.abs(magicSum - getHorizontalSum(i));
		}
		
		// colunas
		for (int i = 0; i < 0; i++) {
			_fitness += Math.abs(magicSum - getVerticalSum(i));
		}
		
		//  diagonal /
		_fitness += Math.abs(magicSum - getForwardDiagonalSum());
		
		// diagonal \
		_fitness += Math.abs(magicSum - getBackDiagonalSum());
		
		this.fitness = _fitness;
	}
	
	/**
	 * Metodo que seleciona os N/2 melhores candidatos
	 */
	public void select() {
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> a = new ArrayList<Integer>();

//		for (int i = 0; i < N; i++) {
//			temp.add(Math.abs(fitness.get(i) - magicSum));
//		}
		
		if (Main.DEBUG) {
			for (Integer t : temp)
				System.out.println("temp = " + t + " ");
		}
		
		for (int i = 0; i < N/2; i++) {
			int min = Collections.min(temp);
			
			if (Main.DEBUG)
				System.out.println("min = " + min);
			
			a.add(temp.indexOf(min));
			
			if (Main.DEBUG)
				System.out.println("--> " + temp.indexOf(min));
			
			temp.set(temp.indexOf(min), 10000000);

		}
		
		if (Main.DEBUG) {
			for (int i = 0; i < N; i++) {
				System.out.println(getHorizontalSum(i));
			}
			
			for (Integer index : a) {
				for (int i = index * N; i < index*N + N; i++) {
					System.out.print(board.get(i) + " ");
				}
				System.out.println();
			}
		}
	}

	/**
	 * Resolve o problema procurando uma board que possua uma solucao
	 */
	public void solve() {
		
		// corre enquanto nao encontrar solucao
		while (!isSolution()) {
			
			computeFitness();
			
			// solve the problem
			
			
		}	
	}
}
