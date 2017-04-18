import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Board {

	private int n;
	private int magicSum;
	private ArrayList<Integer> board = new ArrayList<Integer>();
	private ArrayList<Integer> fitness = new ArrayList<Integer>();
	
	/**
	 * Construtor para uma board de tamanho N e com uma determinada soma magica
	 * @param n - tamanho da board
	 * @param magicSum - soma desejada para cada linha, coluna e diagonal
	 */
	public Board(int n, int magicSum) {
		this.n = n;
		this.magicSum = magicSum;
	}
	
	/**
	 * Obter a soma de uma linha
	 * @param line - numero da linha em questao
	 * @return soma de todos os valores que compoem essa linha
	 */
	private int getHorizontalSum(int line) {
		int sum = 0;
		for (int i = line * n; i < line*n + n; i++) {
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
		for (int i = collumn; i < n*n; i+=n) {
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
		for (int i = 0; i < n*n; i+=n+1) {
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
		for (int i = n-1; i < n*n - n+1; i+=n-1) {
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
		for(int i = 0; i < n; i++){
			if (getHorizontalSum(i) != magicSum ||
					getVerticalSum(i) != magicSum)
				return false;
		}
		if ((getBackDiagonalSum() != magicSum) || (getForwardDiagonalSum() != magicSum))
			return false;
			
		return true;
	}
	
	/**
	 * Gerar uma nova board com numeros aleatorios e nao repetidos
	 */
	public void generateBoard() {
		Random randomGenerator = new Random();
		while (board.size() < n*n) {
			int random = randomGenerator.nextInt(n*n) + 1;
			if (!board.contains(random)) 
				board.add(random);	
		}
	}
	
	/**
	 * Imprimir uma board de tamanho N
	 */
	public void printBoard() {
//		for (int i = 1; i <= n*n; i++) {
//			System.out.print("\t" + board.get(i-1));
//			if (i % n == 0 && i != 0) {
//				System.out.print("\t => " + magicSum);
//				System.out.println();
//			}
//		}
//		for (int i = 0; i < n+2; i++) {
//			System.out.print(magicSum + "\t");
//		}
//		System.out.println();
		
		int line = 0;
		
		for (int i = 1; i <= n*n; i++) {
			System.out.print("\t" + board.get(i-1));
			if (i % n == 0 && i != 0) {
				System.out.print("\t => " + getHorizontalSum(line++));
				System.out.println();
			}
		}
		
		System.out.print(getForwardDiagonalSum() + "\t");
		
		for (int i = 0; i < n; i++) {
			System.out.print(getVerticalSum(i) + "\t");
		}
		
		System.out.println(getBackDiagonalSum());
	}
	
	/**
	 * Calcula o fitness da board e guarda esses valores num arrayList.
	 * Sao usados como fitness os valores das somas das linhas, das colunas e das diagonais.
	 */
	public void computeFitness() {
		// linhas
		for (int i = 0; i < n; i++) {
			fitness.add(getHorizontalSum(i));
		}
		
		// colunas
		for (int i = 0; i < 0; i++) {
			fitness.add(getVerticalSum(i));
		}
		
		//  diagonal /
		fitness.add(getForwardDiagonalSum());
		
		// diagonal \
		fitness.add(getBackDiagonalSum());
	}
	
	/**
	 * Metodo que seleciona os n/2 melhores candidatos
	 */
	public void select() {
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> a = new ArrayList<Integer>();

		for (int i = 0; i < n; i++) {
			temp.add(Math.abs(fitness.get(i) - magicSum));
		}
		
		if (Main.DEBUG) {
			for (Integer t : temp)
				System.out.println("temp = " + t + " ");
		}
		
		for (int i = 0; i < n/2; i++) {
			int min = Collections.min(temp);
			
			if (Main.DEBUG)
				System.out.println("min = " + min);
			
			a.add(temp.indexOf(min));
			
			if (Main.DEBUG)
				System.out.println("--> " + temp.indexOf(min));
			
			temp.set(temp.indexOf(min), 10000000);

		}
		
		if (Main.DEBUG) {
			for (int i = 0; i < n; i++) {
				System.out.println(getHorizontalSum(i));
			}
			
			for (Integer index : a) {
				for (int i = index * n; i < index*n + n; i++) {
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
			
			
			fitness.clear();
			
		}	
	}
}
