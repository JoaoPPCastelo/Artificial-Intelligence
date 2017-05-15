import java.util.ArrayList;
import java.util.Collections;
import java.util.SplittableRandom;

public class Population {
	private int boardLength;
	private int populationSize;
	private SplittableRandom generator;
	BoardFitComparator comparator = new BoardFitComparator();
	private ArrayList<Board> population =new ArrayList<Board>();
	
	/**
	 * Construtor para uma nova populacao
	 * @param populationSize - numero de elementos que compoem uma populacao
	 * @param boardLength - numero de elementos que compoem uma board
	 */
	public Population (int populationSize, int boardLength) {
		generator = new SplittableRandom();
		this.boardLength = boardLength;
		this.populationSize = populationSize;
		generateBoards();
		
	}	
	
	/**
	 * Gerar populationSize diferentes boards de forma aleatoria
	 */
	private void generateBoards() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		for(int i = 1; i < boardLength+1; i++)
			temp.add(i);
		
		for(int i = 0; i < populationSize; i++){
			Collections.shuffle(temp);
			population.add(new Board(new ArrayList<Integer>(temp)));
		}
		
	}

	/**
	 * Metodo que seleciona duas boards para crossover
	 * @param index - indice das boards a usar
	 */
	public void PMX_crossover(int index) {
		
		int a = (int) ((boardLength-1)*generator.nextDouble());
		int b = (int) ((boardLength-1)*generator.nextDouble());
		
		Board b1 = population.get(index-1);
		Board b2 = population.get(index);
		
		ArrayList<Board> result = b1.crossover(b2, Math.min(a, b), Math.max(a, b));
		
		population.set(index-1,result.get(0));
		population.set(index,result.get(1));
	}
	
	/**
	 * Metodo que obtem uma board onde ira ocorrer uma mutacao, trocando os indices calculados e atribuidos a a e b
	 * @param index - indice da board que ira sofrer mutacao
	 */
	public void mutation(int index) {
		
		Board board = population.get(index).copy();
		
		int a = (int) ((boardLength)*generator.nextDouble());
		int b = (int) ((boardLength)*generator.nextDouble());
		
		board.mutation(a, b);
		
		population.set(index, board);
	}
	
	/**
	 * Metodo que realiza um torneio no conjunto de boards que fazem parte da populacao.
	 * No torneio sao selecionados as boards com melhor fitness, usando o comparador inicializado acima.
	 * @param elite
	 */
	public void tornment(int elite) {
		ArrayList<Board> temp = new ArrayList<Board>();
		
		for(int i = 0; i < elite; i++){
			ArrayList<Board> permut = new ArrayList<Board>(population);
			
			for(int j = 0; j < this.populationSize-1; j++)
				Collections.swap(permut, j, (int)(j+generator.nextDouble()*(populationSize-j)));
			
			for(int j = 0; j < populationSize; j += elite)
				temp.add(Collections.max(permut.subList(j, j+elite), comparator));
		}
		population = new ArrayList<Board>(temp);
	}
	
	/**
	 * Metodo que procura uma solucao para o problema.
	 * @param elite - numero de elementos da populacao que compoem a elite
	 * @param pMutation - probabilidade de ocorrer mutacao
	 * @param pCrossover - probabilidade de ocorrer crossover
	 * @return - board em que todas as linhas, colunas e diagonais, na sua soma correspondem a soma magica pretendida
	 */
	public Board solve(int elite, double pMutation, double pCrossover) {
		
		while(true){
			
			tornment(elite);
			
			for(int i = 1; i < populationSize; i += 2) 
				if(generator.nextDouble() <= pCrossover)			
					PMX_crossover(i);
			
			for(int i = 0; i < populationSize; i++)
				if(generator.nextDouble() <= pMutation)
					mutation(i);
			
			for(Board b : population)
				if(b.getFitness() == 0)
					return b;
		}
	}	
}
