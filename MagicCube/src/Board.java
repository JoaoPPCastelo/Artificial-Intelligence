import java.util.ArrayList;
import java.util.Random;


public class Board {

	private int n;
	private int magicSum;
	private ArrayList<Integer> board = new ArrayList<Integer>();
	
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
	public int getHorizontalSum(int line) {
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
	public int getVerticalSum(int collumn) {
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
	public int getBackDiagonalSum() {
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
	public int getForwardDiagonalSum() {
		int sum = 0;
		for (int i = 9; i < n*n - n+1; i+=n-1) {
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
	private boolean evaluateBoard() {
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
		for (int i = 1; i <= n*n; i++) {
			System.out.print("\t" + board.get(i-1));
			if (i % n == 0 && i != 0) {
				System.out.print("\t => " + magicSum);
				System.out.println();
			}
		}
		for (int i = 0; i < n+2; i++) {
			System.out.print(magicSum + "\t");
		}
		System.out.println();
	}

	/**
	 * Resolver o problema procurando uma board que cumpre os requisitos impostos
	 */
	public void solve() {
		// TODO Auto-generated method stub
		
		
		// se a board for solucao, sair
		
		
		// caso contrario, continuar
		
	}
}
