import java.util.ArrayList;

public class Board {
	
	private ArrayList<String> board = new ArrayList<String>(64);

	/**
	 * Construtor para uma Board
	 */
	public Board() {
		initializeBoard();
	}

	/**
	 * Inicializa a board
	 */
	private void initializeBoard() {
		for (int i = 0; i < 64; i++)
			board.add(i, "-");
		
		// pretas
		for (int i = 1; i <= 8; i+=2)
			board.set(i, "p");
		for (int i = 8; i < 16; i+=2)
			board.set(i, "p");
		for (int i = 17; i < 24; i+=2)
			board.set(i, "p");
		
		// brancas
		for (int i = 40; i < 48; i+=2)
			board.set(i, "b");
		for (int i = 49; i < 56; i+=2)
			board.set(i, "b");	
		for (int i = 56; i < 64; i+=2)
			board.set(i, "b");
		
	}
	
	/**
	 * Imprime a Board
	 */
	public void printBoard() {
		int index = 0;
		int line = 1;
		
		System.out.println("\tA  B  C  D  E  F  G  H");
		System.out.println("----------------------------------");
		
		for (String s : board) {
			if (index % 8 == 0) {
				System.out.println();
				System.out.print(line++ + "   |	");
			}
			System.out.print(s + "  ");
			index++;
		}
	}
	
	/**
	 * Move uma peca
	 * @param actPos - posicao atual da peca
	 * @param nxtPos - posicao para onde se quer mover a peca
	 */
	public void move(Integer actPos, Integer nxtPos) {
		if (validateMovements(actPos, nxtPos)) {
			
			eat(actPos, nxtPos);
			
			String tmp = board.get(actPos);
			board.set(nxtPos.intValue(), tmp);
			board.set(actPos, "-");
			
			
			promotion();
		}
		
		else
			System.out.println("Movimento inválido.");
	}
	
	/**
	 * Verifica que os movimentos pretendidos
	 * @param index - posicao atual da peca
	 * @param newIndex - posicao desejada para a peca
	 * @return true se o movimento e valido, falso caso contrario
	 */
	private boolean validateMovements(int index, int newIndex) {
		
		boolean isValid = false;
		
		// pecas simples. apenas se movem uma casa
		if (board.get(index) == "b" || board.get(index) == "p") {
			
			// A peça movimenta-se em diagonal, sobre as casas escuras, para a frente, e uma casa de cada vez.
			
			// as pecas da esquerda apenas se podem mover para a direita
			if (index%8 == 0) {
				// pecas pretas so podem descer V
				if (board.get(newIndex) == "-" && newIndex == index+9 && board.get(index) == "p")
					isValid = true;
				//pecas brancas so podem subir V
				else if (board.get(newIndex) == "-" && newIndex == index-7 && board.get(index) == "b" )
					isValid = true;
				// pecas pretas que comem V
				else if ((board.get(newIndex) == "-" && board.get(index+9) == "b" && board.get(index) == "p" && newIndex == index+18) ||
						(board.get(newIndex) == "-" && board.get(index-7) == "b" && board.get(index) == "p" && newIndex == index-14))
					isValid = true;
				// pecas brancas que comem V
				else if ((board.get(newIndex) == "-" && board.get(index-7) == "p" && board.get(index) == "b" && newIndex == index-14) ||
						(board.get(newIndex) == "-" && board.get(index+9) == "p" && board.get(index) == "b" && newIndex == index+18))
					isValid = true;
			}
			
			//as pecas da direita apenas se podem mover para a esqureda
			else if (index%8 == 7) {
				// pretas descem V
				if (board.get(newIndex) == "-" && newIndex == index+7 && board.get(index) == "p") 
					isValid = true;
				// brancas sobem V
				else if (board.get(newIndex) == "-" && newIndex == index-9 && board.get(index) == "b")
					isValid = true;
				// pecas pretas que comem V
				else if ((board.get(newIndex) == "-" && board.get(index+7) == "b" && board.get(index) == "p" && newIndex == index+14) ||
						(board.get(newIndex) == "-" && board.get(index-9) == "b" && board.get(index) == "p" && newIndex == index-18))
					isValid = true;
				// pecas branccas que comem V
				else if ((board.get(newIndex) == "-" && board.get(index-9) == "p" && board.get(index) == "b" && newIndex == index-18) ||
						(board.get(newIndex) == "-" && board.get(index+7) == "p" && board.get(index) == "b" && newIndex == index+14))
					isValid = true;
			}
			
			// pecas no meio da board
			else {
				// pretas descem V
				if (board.get(newIndex) == "-" && (newIndex == index+7 || newIndex == index+9) && board.get(index) == "p")
					isValid = true;
				// brancas sobem V
				else if  (board.get(newIndex) == "-" && (newIndex == index-7 || newIndex == index-9) && board.get(index) == "b")
					isValid = true;
				// pretas que comem V
				else if ((board.get(newIndex) == "-" && board.get(index+7) == "b" && board.get(index) == "p" && newIndex == index+14) ||
						(board.get(newIndex) == "-" && board.get(index+9) == "b" && board.get(index) == "p" && newIndex == index+18) ||
						(board.get(newIndex) == "-" && board.get(index-7) == "b" && board.get(index) == "p" && newIndex == index-14) ||
						(board.get(newIndex) == "-" && board.get(index-9) == "b" && board.get(index) == "p" && newIndex == index-18))
					isValid = true;
				// brancas que comem V
				else if ((board.get(newIndex) == "-" && board.get(index-7) == "p" && board.get(index) == "b" && newIndex == index-14) || 
						(board.get(newIndex) == "-" && board.get(index-9) == "p" && board.get(index) == "b" && newIndex == index-18) ||
						(board.get(newIndex) == "-" && board.get(index+7) == "p" && board.get(index) == "b" && newIndex == index+14) ||
						(board.get(newIndex) == "-" && board.get(index+9) == "p" && board.get(index) == "b" && newIndex == index+18))
					isValid = true;
			}
		}
		
		// A peça que atingir a oitava casa adversária, parando ali, será promovida a "dama", peça de movimentos mais amplos que a simples peça. Assinala-se a dama sobrepondo, à pedra promovida, outra da mesma cor.
		else if (board.get(index) == "B") {
			// A dama pode mover-se para trás e para frente em diagonal uma casa de cada vez, diferente das outras peças, que movimentam-se apenas para frente em diagonal. A dama pode também tomar outra peça pela frente ou por trás em diagonal.
			if (board.get(newIndex) == "-" && (Math.abs(newIndex - index)%7 == 0)) {
				isValid = true;
				for(int i =index+7;i<newIndex;i+=7){
					  int count = 0;
					if(board.get(i)=="p" || board.get(i)=="P")
						count++;
					if(board.get(i)=="b"|| board.get(i)=="B" || count>1)
						isValid=false;
				}
			}
			else if (board.get(newIndex) == "-" && (Math.abs(newIndex - index)%9 == 0)){
				isValid = true;
				for(int i =index+9;i<newIndex;i+=9){
					  int count = 0;
					if(board.get(i)=="p" || board.get(i)=="P")
						count++;
					if(board.get(i)=="b"|| board.get(i)=="B" || count>1)
						isValid=false;
				}
				
			}
			
		}
	else if(board.get(index) == "P"){
		if (board.get(newIndex) == "-" && (Math.abs(newIndex - index)%7 == 0)) {
			isValid = true;
			for(int i =index+7;i<newIndex;i+=7){
				  int count = 0;
				if(board.get(i)=="b" || board.get(i)=="B")
					count++;
				if(board.get(i)=="p"|| board.get(i)=="P" || count>1)
					isValid=false;
			}
		}
		else if (board.get(newIndex) == "-" && (Math.abs(newIndex - index)%9 == 0)){
			isValid = true;
			for(int i =index+9;i<newIndex;i+=9){
				  int count = 0;
				if(board.get(i)=="b" || board.get(i)=="B")
					count++;
				if(board.get(i)=="p"|| board.get(i)=="P" || count>1)
					isValid=false;
			}
			
		}
	}
		return isValid;
	}
	
	/**
	 * Verifica quando o jogo terminou....
	 */
	public boolean gameOver() {
		return ((!board.contains("p") && !board.contains("P")) || (!board.contains("b") && !board.contains("B")));
	}
	
	/**
	 * Promove uma peca a dama
	 */
	private void promotion() {
		for (int i = 0; i < 8; i++) 
			if (board.get(i) == "b")
				board.set(i, "B");
		
		for (int i = 56; i < 64; i++) 
			if (board.get(i) == "p")
				board.set(i, "P");
	}
	
	private void eat(int index, int newIndex) {
							
		if (board.get(newIndex) == "-"  && board.get(index) == "p" && newIndex == index-14 && board.get(index-7) == "b") {
			System.out.println("hello1");
			board.set(index-7, "-");
		}
	
		// pecas pretas que comem V
		else if (board.get(newIndex) == "-" && board.get(index+7) == "b" && board.get(index) == "p" && newIndex == index+14) {
			System.out.println("hello2");
			board.set(index+7, "-");
		}
		
		// pecas brancas que comem V
		else if (board.get(newIndex) == "-" && board.get(index) == "b" && newIndex == index-14  && board.get(index-7) == "p") {
			System.out.println("hello3");
			board.set(index-7, "-");
		}
		
		else if	(board.get(newIndex) == "-" && board.get(index+7) == "p" && board.get(index) == "b" && newIndex == index+14) {
			System.out.println("hello4");
			board.set(index+7, "-");
		}
		
		else if	(board.get(index) == "p" && board.get(newIndex) == "-"  && newIndex == index-18 && board.get(index-9) == "b") {
			System.out.println("hello5");
			board.set(index-9, "-");
		}
		
		// pecas pretas que comem V
		else if (board.get(index) == "p" && board.get(newIndex) == "-" && board.get(index+9) == "b"  && newIndex == index+18) {
			System.out.println("hello6");
			board.set(index+9, "-");
		}
		
		// pecas branccas que comem V
		else if (board.get(newIndex) == "-" && board.get(index) == "b" && newIndex == index-18 && board.get(index-9) == "p" ) {
			System.out.println("hello7");
			board.set(index-9, "-");
		}
		
		else if	(board.get(newIndex) == "-" && board.get(index+9) == "p" && board.get(index) == "b" && newIndex == index+18) {
			System.out.println("hello8");
			board.set(index+9, "-");
		}	
		
		else if (board.get(index) == "B" && (Math.abs(newIndex - index)%7 == 0)) {
			for(int i =index+7;i<newIndex;i+=7){
				if(board.get(i)=="p" || board.get(i)=="P")
					board.set(i, "-");
			}
			
		}
		else if (board.get(index) == "B" && (Math.abs(newIndex - index)%9 == 0)){
			System.out.println("hello9");
			for(int i =index+9;i<newIndex;i+=9){
				System.out.println("hello for");
				if(board.get(i)=="p" || board.get(i)=="P")
					board.set(i, "-");
			}
			
		}
		
		else if (board.get(index) == "P" && (Math.abs(newIndex - index)%7 == 0)) {
			for(int i =index+7;i<newIndex;i+=7){
				if(board.get(i)=="b" || board.get(i)=="B")
					board.set(i, "-");
			}
			
		}
		else if (board.get(newIndex) == "P" && (Math.abs(newIndex - index)%9 == 0)){
			for(int i =index+9;i<newIndex;i+=9){
				if(board.get(i)=="b" || board.get(i)=="B")
					board.set(i, "-");
			}
			
		}
	}
}
