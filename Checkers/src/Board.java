import java.util.ArrayList;

public class Board {

	private ArrayList<String> board;
	private String BLACKPIECES = "p";
	private String WHITEPIECES = "w";
	

	/**
	 * Construtor para uma Board
	 */
	public Board() {
		board = new ArrayList<String>(64);
		initializeBoard();
	}

	/**
	 * Inicializa a board com as pecas nas posicoes iniciais. Todos os espacos
	 * restantes sao preenchidos com -
	 */
	private void initializeBoard() {
		// espacos em branco e redefinicao do tamanho do arrayLis
		for (int i = 0; i < 64; i++)
			board.add(i, "-");

		// pecas pretas
		for (int i = 1; i <= 8; i += 2)
			board.set(i, BLACKPIECES);
		for (int i = 8; i < 16; i += 2)
			board.set(i, BLACKPIECES);
		for (int i = 17; i < 24; i += 2)
			board.set(i, BLACKPIECES);

		// pecas brancas
		for (int i = 40; i < 48; i += 2)
			board.set(i, WHITEPIECES);
		for (int i = 49; i < 56; i += 2)
			board.set(i, WHITEPIECES);
		for (int i = 56; i < 64; i += 2)
			board.set(i, WHITEPIECES);
	}

	/**
	 * Imprime uma board assim como um sistema de orientacao necessario para a
	 * interacao com o jogo
	 */
	public void printBoard() {
		int index = 0, line = 1;

		System.out.println("\n\tA  B  C  D  E  F  G  H");
		System.out.println("----------------------------------");

		for (String s : board) {
			if (index % 8 == 0) {
				if (index != 0)
					System.out.println();
				System.out.print(line++ + "   |	");
			}
			System.out.print(s + "  ");
			index++;
		}
		System.out.println("\n");
	}

	private void swap(Integer actPos, Integer nxtPos) {
		String tmp = board.get(actPos);
		board.set(nxtPos.intValue(), tmp);
		board.set(actPos, "-");
	}

	/**
	 * Metdodo que realiza operacoes na board caso as acoes dadas sejam validas.
	 * Essas operacoes passam por comer pecas que respeitem as condicoes para
	 * tal, mover pecas de uma posicao para a outra e promover uma peca a dama
	 * caso esta chega a outra extremidade.
	 * 
	 * @param actPos - posicao atual da peca
	 * @param nxtPos - posicao para onde se quer mover a peca
	 */
	public void play(Integer actPos, Integer nxtPos) {

		if (validateMovements(actPos, nxtPos)) {

			eat(actPos, nxtPos);

			swap(actPos, nxtPos);

			promotion();
		}

		else
			System.out.println("Movimento inválido.");
	}

	/**
	 * Verifica que os movimentos pretendidos para as pecas pretas e brancas sao validos
	 * @param actPos
	 * @param nxtPos
	 * @return
	 */
	private boolean validateMovements(Integer actPos, Integer nxtPos) {
		return validateWhiteMovements(actPos, nxtPos) || validateBlackMovements(actPos, nxtPos); // CONFIRMAR QUE ESTA CORRETO - o OR - TODO
	}

	/**
	 * Validacao dos movimentos das pecas brancas
	 * @param index - indice atual da peca
	 * @param newIndex - indice para onde se quer mover a peca
	 * @return true se o movimento respeita as regras do jogo, false caso contrario
	 */
	private boolean validateWhiteMovements(int index, int newIndex) {
		boolean isValid = false;

		// pecas simples. apenas se movem uma casa
		if (board.get(index) == WHITEPIECES) {

			// as pecas da esquerda apenas se podem mover para a direita
			if (index % 8 == 0) {
				
				// pecas brancas so podem subir V
				if (newIndex == index - 7 && board.get(newIndex) == "-" && board.get(index) == WHITEPIECES)
					isValid = true;
				
				// pecas brancas que comem V
				else if ((newIndex == index - 14 && board.get(newIndex) == "-" && board.get(index - 7) == BLACKPIECES && board.get(index) == WHITEPIECES)
						|| (newIndex == index + 18 && board.get(newIndex) == "-" && board.get(index + 9) == BLACKPIECES && board.get(index) == WHITEPIECES))
					isValid = true;
			}

			// as pecas da direita apenas se podem mover para a esqureda
			else if (index % 8 == 7) {
			
				// brancas sobem V
				if (newIndex == index - 9 && board.get(newIndex) == "-" && board.get(index) == WHITEPIECES)
					isValid = true;
			
				// pecas brancas que comem V
				else if ((newIndex == index - 18 && board.get(newIndex) == "-" && board.get(index - 9) == BLACKPIECES && board.get(index) == WHITEPIECES)
						|| (newIndex == index + 14 && board.get(newIndex) == "-" && board.get(index + 7) == BLACKPIECES && board.get(index) == WHITEPIECES))
					isValid = true;
			}
			
			// pecas no meio da board
			else {
			
				// brancas sobem V
				if ((newIndex == index - 7 || newIndex == index - 9) && board.get(newIndex) == "-" && board.get(index) == WHITEPIECES)
					isValid = true;
			
				// brancas que comem V
				else if ((newIndex == index - 14 && board.get(newIndex) == "-" && board.get(index - 7) == BLACKPIECES && board.get(index) == WHITEPIECES)
						|| (newIndex == index - 18 && board.get(newIndex) == "-" && board.get(index - 9) == BLACKPIECES && board.get(index) == WHITEPIECES)
						|| (newIndex == index + 14 && board.get(newIndex) == "-" && board.get(index + 7) == BLACKPIECES && board.get(index) == WHITEPIECES)
						|| (newIndex == index + 18 && board.get(newIndex) == "-" && board.get(index + 9) == BLACKPIECES && board.get(index) == WHITEPIECES))
					isValid = true;
			}
		}
			
		// A peça que atingir a oitava casa adversária, parando ali, será
		// promovida a "dama", peça de movimentos mais amplos que a simples
		// peça. Assinala-se a dama sobrepondo, à pedra promovida, outra da
		// mesma cor.
		else if (board.get(index) == WHITEPIECES.toUpperCase()) {
			
			// A dama pode mover-se para trás e para frente em diagonal uma casa
			// de cada vez, diferente das outras peças, que movimentam-se apenas
			// para frente em diagonal. A dama pode também tomar outra peça pela
			// frente ou por trás em diagonal.
			if ((Math.abs(newIndex - index) % 7 == 0) && board.get(newIndex) == "-") {
				isValid = true;
			
				for (int i = index + 7; i < newIndex; i += 7) {
			
					int count = 0;
			
					if (board.get(i) == BLACKPIECES || board.get(i) == BLACKPIECES.toUpperCase())
						count++;
				
					if (board.get(i) == WHITEPIECES || board.get(i) == WHITEPIECES.toUpperCase() || count > 1)
						isValid = false;
				}
			} 
				
			else if (Math.abs(newIndex - index) % 9 == 0 && board.get(newIndex) == "-") {

				isValid = true;
				
				for (int i = index + 9; i < newIndex; i += 9) {
				
					int count = 0;
					
					if (board.get(i) == BLACKPIECES || board.get(i) == BLACKPIECES.toUpperCase())
						count++;
					
					if (board.get(i) == WHITEPIECES || board.get(i) == WHITEPIECES.toUpperCase() || count > 1)
						isValid = false;
				}
			}
					
		} 
		return isValid;
	}
	
	/**
	 * Validacao dos movimentos das pecas brancas
	 * @param index - indice atual da peca
	 * @param newIndex - indice para onde se quer mover a peca
	 * @return true se o movimento respeita as regras do jogo, false caso contrario
	 */
	private boolean validateBlackMovements(int index, int newIndex) {
	
		boolean isValid = false;
	
		// pecas simples. apenas se movem uma casa
		if (board.get(index) == BLACKPIECES) {
	
			// A peça movimenta-se em diagonal, sobre as casas escuras, para a
			// frente, e uma casa de cada vez.
	
			// as pecas da esquerda apenas se podem mover para a direita
			if (index % 8 == 0) {
	
				// pecas pretas so podem descer V
				if (newIndex == index + 9 && board.get(newIndex) == "-" && board.get(index) == BLACKPIECES)
					isValid = true;
	
				// pecas pretas que comem V
				else if ((newIndex == index + 18 && board.get(newIndex) == "-" && board.get(index + 9) == WHITEPIECES && board.get(index) == BLACKPIECES)
						|| (newIndex == index - 14 && board.get(newIndex) == "-" && board.get(index - 7) == WHITEPIECES && board.get(index) == BLACKPIECES))
					isValid = true;
			}
	
			// as pecas da direita apenas se podem mover para a esqureda
			else if (index % 8 == 7) {
	
				// pretas descem V
				if (newIndex == index + 7 && board.get(newIndex) == "-" && board.get(index) == BLACKPIECES)
					isValid = true;
	
				// pecas pretas que comem V
				else if ((newIndex == index + 14 && board.get(newIndex) == "-" && board.get(index + 7) == WHITEPIECES && board.get(index) == BLACKPIECES)
						|| (newIndex == index - 18 && board.get(newIndex) == "-" && board.get(index - 9) == WHITEPIECES && board.get(index) == BLACKPIECES))
					isValid = true;
			}
	
			// pecas no meio da board
			else {
	
				// pretas descem V
				if ((newIndex == index + 7 || newIndex == index + 9) && board.get(newIndex) == "-" && board.get(index) == BLACKPIECES)
					isValid = true;
	
				// pretas que comem V
				else if ((newIndex == index + 14 && board.get(newIndex) == "-" && board.get(index + 7) == WHITEPIECES && board.get(index) == BLACKPIECES)
						|| (newIndex == index + 18 && board.get(newIndex) == "-" && board.get(index + 9) == WHITEPIECES && board.get(index) == BLACKPIECES)
						|| (newIndex == index - 14 && board.get(newIndex) == "-" && board.get(index - 7) == WHITEPIECES && board.get(index) == BLACKPIECES)
						|| (newIndex == index - 18 && board.get(newIndex) == "-" && board.get(index - 9) == WHITEPIECES && board.get(index) == BLACKPIECES))
					isValid = true;
			}
		}
	
		// A peça que atingir a oitava casa adversária, parando ali, será
		// promovida a "dama", peça de movimentos mais amplos que a simples
		// peça. Assinala-se a dama sobrepondo, à pedra promovida, outra da
		// mesma cor.
		else if (board.get(index) == BLACKPIECES.toUpperCase()) {
	
			if ((Math.abs(newIndex - index) % 7 == 0) && board.get(newIndex) == "-") {
				isValid = true;
	
				for (int i = index + 7; i < newIndex; i += 7) {
	
					int count = 0;
	
					if (board.get(i) == WHITEPIECES || board.get(i) == WHITEPIECES.toUpperCase())
						count++;
	
					if (board.get(i) == BLACKPIECES || board.get(i) == BLACKPIECES.toUpperCase() || count > 1)
						isValid = false;
				}
			}
	
			else if ((Math.abs(newIndex - index) % 9 == 0) && board.get(newIndex) == "-") {
				isValid = true;
	
				for (int i = index + 9; i < newIndex; i += 9) {
	
					int count = 0;

					if (board.get(i) == WHITEPIECES || board.get(i) == BLACKPIECES.toUpperCase())
						count++;

					if (board.get(i) == BLACKPIECES || board.get(i) == BLACKPIECES.toUpperCase() || count > 1)
						isValid = false;
				}
			}
		}
		return isValid;
	}


	/**
	 * Verifica se uma peca chegou a extremidade do adversario, e se sim, torna-a numa dama
	 */
	private void promotion() {
		for (int i = 0; i < 8; i++)
			if (board.get(i) == WHITEPIECES)
				board.set(i, WHITEPIECES.toUpperCase());

		for (int i = 56; i < 64; i++)
			if (board.get(i) == BLACKPIECES)
				board.set(i, BLACKPIECES.toUpperCase());
	}			
				

	/**
	 * Metodo que verifica se a partir dos movimentos dados pelo utilizador, alguma peca pode ser comida - TODO - deve de haver uma melhor forma....
	 * @param index - indice da peca antes do movimento
	 * @param newIndex - indice para onde a peca sera movida
	 */
	private void eat(int index, int newIndex) {

		if (newIndex == index - 14 && board.get(newIndex) == "-" && board.get(index) == BLACKPIECES && board.get(index - 7) == WHITEPIECES) 
			board.set(index - 7, "-");
		
		else if (newIndex == index + 14 && board.get(newIndex) == "-" && board.get(index + 7) == WHITEPIECES && board.get(index) == BLACKPIECES) 
			board.set(index + 7, "-");
		
		else if (newIndex == index - 14 && board.get(newIndex) == "-" && board.get(index) == WHITEPIECES && board.get(index - 7) == BLACKPIECES) 
			board.set(index - 7, "-");
		
		else if (newIndex == index + 14 && board.get(newIndex) == "-" && board.get(index + 7) == BLACKPIECES && board.get(index) == WHITEPIECES) 
			board.set(index + 7, "-");

		else if (newIndex == index - 18 && board.get(newIndex) == "-" && board.get(index) == BLACKPIECES  && board.get(index - 9) == WHITEPIECES) 
			board.set(index - 9, "-");
		
		
		// pecas pretas que comem V
		else if (newIndex == index + 18 && board.get(newIndex) == "-" && board.get(index) == BLACKPIECES && board.get(index + 9) == WHITEPIECES) 
			board.set(index + 9, "-");
		
		// pecas branccas que comem V
		else if ( newIndex == index - 18 && board.get(newIndex) == "-" && board.get(index) == WHITEPIECES && board.get(index - 9) == BLACKPIECES) 
			board.set(index - 9, "-");
		
		else if (newIndex == index + 18 && board.get(newIndex) == "-" && board.get(index + 9) == BLACKPIECES && board.get(index) == WHITEPIECES) 
			board.set(index + 9, "-");
		
		// damas comem
		else if ((Math.abs(newIndex - index) % 7 == 0) && board.get(index) == WHITEPIECES.toUpperCase()) {

			for (int i = index + 7; i < newIndex; i += 7) {
		
				if (board.get(i) == BLACKPIECES || board.get(i) == BLACKPIECES.toUpperCase())
					board.set(i, "-");
			}
		}

		else if ((Math.abs(newIndex - index) % 9 == 0) && board.get(index) == WHITEPIECES.toUpperCase()) {
		
			for (int i = index + 9; i < newIndex; i += 9) {

				if (board.get(i) == BLACKPIECES || board.get(i) == BLACKPIECES.toUpperCase())
					board.set(i, "-");
			}
		}
		
		else if ((Math.abs(newIndex - index) % 7 == 0) && board.get(index) == BLACKPIECES.toUpperCase()) {

			for (int i = index + 7; i < newIndex; i += 7) {
		
				if (board.get(i) == WHITEPIECES || board.get(i) == WHITEPIECES.toUpperCase())
					board.set(i, "-");
			}
		}

		else if ((Math.abs(newIndex - index) % 9 == 0) && board.get(newIndex) == BLACKPIECES.toUpperCase()) {

			for (int i = index + 9; i < newIndex; i += 9) {

				if (board.get(i) == WHITEPIECES || board.get(i) == WHITEPIECES.toUpperCase())
					board.set(i, "-");
			}
		}
	}
	
	/**
	 * Verifica quando nao existem pecas de uma determinada cor. Caso aconteca, o jogo termina
	 */
	public boolean gameOver() {
		return ((!board.contains(BLACKPIECES) && !board.contains(BLACKPIECES.toUpperCase())) 
				|| (!board.contains(WHITEPIECES) && !board.contains(WHITEPIECES.toUpperCase())));
	}
}
