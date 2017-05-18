import java.util.ArrayList;
import java.util.Collection;

public class Board 
{
	private ArrayList<String> board;
	private String BLACKPIECE = "b";
	private String WHITEPIECE = "w";
	private String BLANK = " ";
	
	/**
	 * Construtor para uma Board
	 */
	public Board() {
		board = new ArrayList<String>(64);
		initializeBoard();
	}
	
	public Board(ArrayList<String> al) {
		board = new ArrayList<String>(64);
		board.addAll(al);
	}
	
	public ArrayList<String> getBoardRepresentation() {
		return board;
	}

	/**
	 * Inicializa a board com as pecas nas posicoes iniciais. Todos os espacos
	 * restantes sao preenchidos com -
	 */
	private void initializeBoard() {
		// espacos em branco e redefinicao do tamanho do arrayLis
		for (int i = 0; i < 64; i++)
			board.add(i, BLANK);

		// pecas pretas
		for (int i = 1; i <= 8; i += 2)
			board.set(i, BLACKPIECE);
		for (int i = 8; i < 16; i += 2)
			board.set(i, BLACKPIECE);
		for (int i = 17; i < 24; i += 2)
			board.set(i, BLACKPIECE);

		// pecas brancas
		for (int i = 40; i < 48; i += 2)
			board.set(i, WHITEPIECE);
		for (int i = 49; i < 56; i += 2)
			board.set(i, WHITEPIECE);
		for (int i = 56; i < 64; i += 2)
			board.set(i, WHITEPIECE);
	}

	/**
	 * Imprime uma board assim como um sistema de orientacao necessario para a
	 * interacao com o jogo
	 */
	public void printBoard() {
		int index = 0, line = 1;

		System.out.println("\n\tA     B     C     D     E     F     G     H");
		System.out.println();

		for (String s : board) {
			if (index % 8 == 0) {
				if (index != 0)
					System.out.println();
				System.out.print("     |-----|-----|-----|-----|-----|-----|-----|-----|");
				System.out.println();
				System.out.print(" " + line++ + "   |  ");
			}
			System.out.print(s + "  |  ");
			index++;
		}
		System.out.print("\n     |-----|-----|-----|-----|-----|-----|-----|-----|");
		System.out.println("\n");
	}

	/**
	 * Move uma peca da posicao actPos para a posicao nxtPos
	 * @param actPos - posicao atual da peca
	 * @param nxtPos - nova posicao da peca
	 */
	private void swap(Integer actPos, Integer nxtPos) {
		String tmp = board.get(actPos);
		board.set(nxtPos.intValue(), tmp);
		board.set(actPos, BLANK);
	}

	/**
	 * Metodo que realiza operacoes na board caso as acoes dadas sejam validas.
	 * Essas operacoes passam por comer pecas que respeitem as condicoes para
	 * tal, mover pecas de uma posicao para a outra e promover uma peca a dama
	 * caso esta chegue a outra extremidade.
	 * 
	 * @param actPos - posicao atual da peca
	 * @param nxtPos - posicao para onde se quer mover a peca
	 */
	public int move(Integer actPos, Integer nxtPos) 
	{
		//System.out.println("move = " + actPos + " " + nxtPos);
		
		if (validateMovements(actPos, nxtPos)) {
			eat(actPos, nxtPos);
			swap(actPos, nxtPos);
			// nao interessa promover quando as pecas estao no meio da board
			if (nxtPos < 8 || nxtPos > 55)
				promotion();
		}
		else
			if (Game.isUserPlaying) {
				System.out.println("Movimento inválido.");
				return 1;
			}
		return 0;
	}

	/**
	 * Verifica que os movimentos pretendidos para as pecas pretas e brancas sao validos
	 * @param actPos - posicao atual da peca
	 * @param nxtPos - posicao para onde se quer mover a peca
	 * @return
	 */
	private boolean validateMovements(Integer actPos, Integer nxtPos) {
		
		//System.out.println(Game.isUserPlaying);
		
		// return (Game.isUserPlaying) ? validateWhiteMovements(actPos, nxtPos) : validateBlackMovements(actPos, nxtPos);
		
		if (Game.isUserPlaying)
			return validateWhiteMovements(actPos, nxtPos);
		
		else
			return validateBlackMovements(actPos, nxtPos);

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
		if (board.get(index) == WHITEPIECE) 
		{
			// as pecas da esquerda apenas se podem mover para a direita
			if (index % 8 == 0) {
				// pecas brancas so podem subir V
				if (newIndex == index - 7 && board.get(newIndex) == BLANK && board.get(index) == WHITEPIECE)
					isValid = true;
				// pecas brancas que comem V
				else if (
					(newIndex == index - 14 
					&& board.get(newIndex) == BLANK 
					&& board.get(index - 7) == BLACKPIECE 
					&& board.get(index) == WHITEPIECE
					)
					|| (newIndex == index + 18 
					&& board.get(newIndex) == BLANK 
					&& board.get(index + 9) == BLACKPIECE 
					&& board.get(index) == WHITEPIECE
					)
				) {
					isValid = true;					
				}
			}
			// as pecas da direita apenas se podem mover para a esqureda
			else if (index % 8 == 7) {
				// brancas sobem V
				if (newIndex == index - 9 && board.get(newIndex) == BLANK && board.get(index) == WHITEPIECE)
					isValid = true;
			
				// pecas brancas que comem V
				else if (
					(newIndex == index - 18 
					&& board.get(newIndex) == BLANK 
					&& board.get(index - 9) == BLACKPIECE 
					&& board.get(index) == WHITEPIECE
					)
					|| (newIndex == index + 14 
					&& board.get(newIndex) == BLANK 
					&& board.get(index + 7) == BLACKPIECE 
					&& board.get(index) == WHITEPIECE
					)
				) {
					isValid = true;					
				}
			}
			// pecas no meio da board
			else {
				// brancas sobem V
				if ((newIndex == index - 7 || newIndex == index - 9) && board.get(newIndex) == BLANK && board.get(index) == WHITEPIECE)
					isValid = true;
			
				// brancas que comem V
				else if (
					(newIndex == index - 14 
					&& board.get(newIndex) == BLANK 
					&& board.get(index - 7) == BLACKPIECE 
					&& board.get(index) == WHITEPIECE
					)
					|| (newIndex == index - 18 
					&& board.get(newIndex) == BLANK 
					&& board.get(index - 9) == BLACKPIECE 
					&& board.get(index) == WHITEPIECE
					)
					|| (newIndex == index + 14 
					&& board.get(newIndex) == BLANK 
					&& board.get(index + 7) == BLACKPIECE 
					&& board.get(index) == WHITEPIECE
					)
					|| (newIndex == index + 18 
					&& board.get(newIndex) == BLANK 
					&& board.get(index + 9) == BLACKPIECE 
					&& board.get(index) == WHITEPIECE
					)
				) {
					isValid = true;					
				}
			}
		}
		// A peça que atingir a oitava casa adversária, parando ali, será
		// promovida a "dama", peça de movimentos mais amplos que a simples
		// peça. Assinala-se a dama sobrepondo, à pedra promovida, outra da
		// mesma cor.
		else if (board.get(index) == WHITEPIECE.toUpperCase()) {
			// A dama pode mover-se para trás e para frente em diagonal uma casa
			// de cada vez, diferente das outras peças, que movimentam-se apenas
			// para frente em diagonal. A dama pode também tomar outra peça pela
			// frente ou por trás em diagonal.
			if ((Math.abs(newIndex - index) % 7 == 0) && board.get(newIndex) == BLANK) {
				isValid = true;
			
				for (int i = index + 7; i < newIndex; i += 7) {
					int count = 0;
			
					if (board.get(i) == BLACKPIECE || board.get(i) == BLACKPIECE.toUpperCase())
						count++;
				
					if (board.get(i) == WHITEPIECE || board.get(i) == WHITEPIECE.toUpperCase() || count > 1)
						isValid = false;
				}
			} 
			else if (Math.abs(newIndex - index) % 9 == 0 && board.get(newIndex) == BLANK) {
				isValid = true;
				
				for (int i = index + 9; i < newIndex; i += 9) {
					int count = 0;
					
					if (board.get(i) == BLACKPIECE || board.get(i) == BLACKPIECE.toUpperCase())
						count++;
					
					if (board.get(i) == WHITEPIECE || board.get(i) == WHITEPIECE.toUpperCase() || count > 1)
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
	private boolean validateBlackMovements(int index, int newIndex) 
	{		
		boolean isValid = false;
		String piece = board.get(index);
		String newPiece = board.get(newIndex);
	
		// pecas simples. apenas se movem uma casa
		if (piece == BLACKPIECE) {
			// A peça movimenta-se em diagonal, sobre as casas escuras, para a
			// frente, e uma casa de cada vez.
	
			// as pecas da esquerda apenas se podem mover para a direita
			if (index % 8 == 0 && newPiece == BLANK) {
				// pecas pretas so podem descer V
				if (newIndex == index + 9)
					isValid = true;
	
				// pecas pretas que comem V
				else if ((newIndex == index + 18 && board.get(index + 9) == WHITEPIECE)
						|| (newIndex == index - 14 && board.get(index - 7) == WHITEPIECE))
					isValid = true;
			}
	
			// as pecas da direita apenas se podem mover para a esqureda
			else if (index % 8 == 7 && newPiece == BLANK) {
				// pretas descem V
				if (newIndex == index + 7)
					isValid = true;
	
				// pecas pretas que comem V
				else if ((newIndex == index + 14 && board.get(index + 7) == WHITEPIECE)
						|| (newIndex == index - 18 && board.get(index - 9) == WHITEPIECE))
					isValid = true;
			}
	
			// pecas no meio da board
			else if (newPiece == BLANK) {
				// pretas descem V
				if ((newIndex == index + 7 || newIndex == index + 9))
					isValid = true;
	
				// pretas que comem V
				else if ((newIndex == index + 14 && board.get(index + 7) == WHITEPIECE)
						|| (newIndex == index + 18 && board.get(index + 9) == WHITEPIECE)
						|| (newIndex == index - 14 && board.get(index - 7) == WHITEPIECE)
						|| (newIndex == index - 18 && board.get(index - 9) == WHITEPIECE))
					isValid = true;
			}
		}
	
		// A peça que atingir a oitava casa adversária, parando ali, será
		// promovida a "dama", peça de movimentos mais amplos que a simples
		// peça. Assinala-se a dama sobrepondo, à pedra promovida, outra da
		// mesma cor.
		else if (piece == BLACKPIECE.toUpperCase()) {
	
			if ((Math.abs(newIndex - index) % 7 == 0) && newPiece == BLANK) {
				isValid = true;
				for (int i = index + 7; i < newIndex; i += 7) {
					int count = 0;
					if (board.get(i) == WHITEPIECE || board.get(i) == WHITEPIECE.toUpperCase())
						count++;
	
					if (board.get(i) == BLACKPIECE || board.get(i) == BLACKPIECE.toUpperCase() || count > 1)
						isValid = false;
				}
			}
	
			else if ((Math.abs(newIndex - index) % 9 == 0) && newPiece == BLANK) {
				isValid = true;
				for (int i = index + 9; i < newIndex; i += 9) {
					int count = 0;
					if (board.get(i) == WHITEPIECE || board.get(i) == BLACKPIECE.toUpperCase())
						count++;

					if (board.get(i) == BLACKPIECE || board.get(i) == BLACKPIECE.toUpperCase() || count > 1)
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
			if (board.get(i) == WHITEPIECE)
				board.set(i, WHITEPIECE.toUpperCase());

		for (int i = 56; i < 64; i++)
			if (board.get(i) == BLACKPIECE)
				board.set(i, BLACKPIECE.toUpperCase());
	}			
				

	/**
	 * Metodo que verifica se a partir dos movimentos dados pelo utilizador, alguma peca pode ser comida - TODO - deve de haver uma melhor forma....
	 * @param index - indice da peca antes do movimento
	 * @param newIndex - indice para onde a peca sera movida
	 */
	private void eat(int index, int newIndex) 
	{
		if (newIndex == index - 14 && board.get(newIndex) == BLANK && board.get(index) == BLACKPIECE && board.get(index - 7) == WHITEPIECE) 
			board.set(index - 7, BLANK);
		
		else if (newIndex == index + 14 && board.get(newIndex) == BLANK && board.get(index + 7) == WHITEPIECE && board.get(index) == BLACKPIECE) 
			board.set(index + 7, BLANK);
		
		else if (newIndex == index - 14 && board.get(newIndex) == BLANK && board.get(index) == WHITEPIECE && board.get(index - 7) == BLACKPIECE) 
			board.set(index - 7, BLANK);
		
		else if (newIndex == index + 14 && board.get(newIndex) == BLANK && board.get(index + 7) == BLACKPIECE && board.get(index) == WHITEPIECE) 
			board.set(index + 7, BLANK);

		else if (newIndex == index - 18 && board.get(newIndex) == BLANK && board.get(index) == BLACKPIECE  && board.get(index - 9) == WHITEPIECE) 
			board.set(index - 9, BLANK);
		
		// pecas pretas que comem V
		else if (newIndex == index + 18 && board.get(newIndex) == BLANK && board.get(index) == BLACKPIECE && board.get(index + 9) == WHITEPIECE) 
			board.set(index + 9, BLANK);
		
		// pecas branccas que comem V
		else if ( newIndex == index - 18 && board.get(newIndex) == BLANK && board.get(index) == WHITEPIECE && board.get(index - 9) == BLACKPIECE) 
			board.set(index - 9, BLANK);
		
		else if (newIndex == index + 18 && board.get(newIndex) == BLANK && board.get(index + 9) == BLACKPIECE && board.get(index) == WHITEPIECE) 
			board.set(index + 9, BLANK);
		
		// damas comem
		else if ((Math.abs(newIndex - index) % 7 == 0) && board.get(index) == WHITEPIECE.toUpperCase()) {
			for (int i = index + 7; i < newIndex; i += 7) {
				if (board.get(i) == BLACKPIECE || board.get(i) == BLACKPIECE.toUpperCase())
					board.set(i, BLANK);
			}
		}

		else if ((Math.abs(newIndex - index) % 9 == 0) && board.get(index) == WHITEPIECE.toUpperCase()) {
			for (int i = index + 9; i < newIndex; i += 9) {
				if (board.get(i) == BLACKPIECE || board.get(i) == BLACKPIECE.toUpperCase())
					board.set(i, BLANK);
			}
		}
		
		else if ((Math.abs(newIndex - index) % 7 == 0) && board.get(index) == BLACKPIECE.toUpperCase()) {
			for (int i = index + 7; i < newIndex; i += 7) {
				if (board.get(i) == WHITEPIECE || board.get(i) == WHITEPIECE.toUpperCase())
					board.set(i, BLANK);
			}
		}

		else if ((Math.abs(newIndex - index) % 9 == 0) && board.get(newIndex) == BLACKPIECE.toUpperCase()) {
			for (int i = index + 9; i < newIndex; i += 9) {
				if (board.get(i) == WHITEPIECE || board.get(i) == WHITEPIECE.toUpperCase())
					board.set(i, BLANK);
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> allValidMovements(boolean player) {
		
		String color = player ? WHITEPIECE : BLACKPIECE;
		
		ArrayList<String> possibleMovent = new ArrayList<String>();
		// linhas
		for(int j = 0; j < 8; j++) {
			// colunas
			for (int i = 0; i < 8; i++) {
				if (board.get(j*8 + i) == color || board.get(j*8 + i) == color.toUpperCase())
					possibleMovent.addAll(validMovements(j*8 + i, player));
			}
		}
		return possibleMovent;
	}
	
	
	/**
	 * 
	 * @param index
	 * @param player 
	 * @return
	 */
	public ArrayList<String> validMovements(int index, boolean player) {
		
		ArrayList<String> movements = new ArrayList<String>();
		
		if (board.get(index) == WHITEPIECE || board.get(index) == BLACKPIECE) {
			
			int temp = board.get(index) == BLACKPIECE ? 1 : -1;
			int newRow = (int) Math.floor(index/8) + temp;
			
			if (newRow >= 0 || newRow < 8) {
				int newCol = (index%8) + 1;
				
				if (newCol < 8 && board.get(newRow*8 + newCol) == BLANK) {
					movements.add(index + " " + (newRow*8 + newCol));
				}
				
				newCol = (index%8) - 1;
				if (newCol >= 0 && board.get(newRow*8 + newCol) == BLANK) {
					movements.add(index + " " + (newRow*8 + newCol));
				}	
			}
		}
		
		// se for dama
		else {
			
			// TODO damas podem andar mais de uma casa
			int newRow = (int) (Math.floor(index/8) + 1);
						
			if (newRow < 8) {
				
				int newCol = (index%8) + 1;
				
				if (newCol < 8 && board.get(newRow*8 + newCol) == BLANK) {
					movements.add(index + " " + (newRow*8 + newCol));
				}
				
				newCol = (index%8) - 1;
				if (newCol >= 0 && board.get(newRow*8 + newCol) == BLANK) {
					movements.add(index + " " + (newRow*8 + newCol));
				}		
			}
			newRow -= 2;
			if (newRow >= 0) {
				
				int newCol = (index%8) + 1;
				
				if (newCol < 8 && board.get(newRow*8 + newCol) == BLANK) {
					movements.add(index + " " + (newRow*8 + newCol));
				}
				
				newCol = (index%8) - 1;
				if (newCol >= 0 && board.get(newRow*8 + newCol) == BLANK) {
					movements.add(index + " " + (newRow*8 + newCol));
				}		
			}
		}
		movements.addAll(getValidSkipMovements(index, player));
		return movements;
	}

	/**
	 * 
	 * @param index
	 * @param player 
	 * @return
	 */
	private ArrayList<String> getValidSkipMovements(int index, boolean player) {
		
		ArrayList<String> movements = new ArrayList<String>();
		ArrayList<Integer> possibilities = new ArrayList<Integer>();
		
		int row = (int) Math.floor(index/8);
		int col = index%8;
		
		//System.out.println("index = " + index + " player = " + player);
		
		if (player && board.get(index) == WHITEPIECE) {		
			//System.out.println("hello1");
			possibilities.add(fromPoint2Index(row-2, col+2));
			possibilities.add(fromPoint2Index(row-2, col-2));
		} 
		else if (!player && board.get(index) == BLACKPIECE) {
			//System.out.println("hello2");
			possibilities.add(fromPoint2Index(row+2, col+2));
			possibilities.add(fromPoint2Index(row+2, col-2));

		}
		else if (board.get(index) == WHITEPIECE.toUpperCase() || board.get(index) == BLACKPIECE.toUpperCase()) {
			//System.out.println("hello3");

			possibilities.add(fromPoint2Index(row+2, col+2));
			possibilities.add(fromPoint2Index(row+2, col-2));
			possibilities.add(fromPoint2Index(row-2, col+2));
			possibilities.add(fromPoint2Index(row-2, col-2));
		}
		
		for (int i = 0; i < possibilities.size(); i++) {
			Integer pos = possibilities.get(i);
			
			if(pos > 0 && pos < 64 && board.get(pos) == BLANK && isOpponent(player, midleIndex(index, pos)))
				movements.add(index + " " + pos);			
		}
		return movements;
	}
	
	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private int midleIndex(int start, int end) {
		
		int startCol = start%8;
		int startRow = start/8;
		
		int endCol = end%8;
		int endRow = end/8;
				
		return fromPoint2Index((startRow + endRow)/2, (startCol + endCol)/2);	
	}
	
	/**
	 * 
	 * @param player
	 * @param index
	 * @return
	 */
	private boolean isOpponent(boolean player, int index) {
		if (!player && (board.get(index) == WHITEPIECE || board.get(index) == WHITEPIECE.toUpperCase()))
				return true;
		if (player && (board.get(index) == BLACKPIECE || board.get(index) == BLACKPIECE.toUpperCase()))
			return true;
		return false;
		
	}
	
	private int fromPoint2Index (int row, int col) {
		return (row)*8+ (col);
	}

	/**
	 * Verifica quando nao existem pecas de uma determinada cor. Caso aconteca, o jogo termina
	 */
	public boolean gameOver() {
		return ((!board.contains(BLACKPIECE) && !board.contains(BLACKPIECE.toUpperCase())) 
				|| (!board.contains(WHITEPIECE) && !board.contains(WHITEPIECE.toUpperCase())));
	}

	public double getWhiteDamas() {

		int count = 0;
		
		for (String s : board) {
			if (s == WHITEPIECE.toUpperCase())
				count++;
		}
		
		return count;
	}

	public double getWhitePieces() {
		
		int count = 0;
		
		for (String s : board) {
			if (s == WHITEPIECE)
				count++;
		}
		
		return count;
	}

	public double getBlackDamas() {
		
		int count = 0;
		
		for (String s : board) {
			if (s == BLACKPIECE.toUpperCase())
				count++;
		}
		
		return count;
	}

	public double getBlackPieces() {
		
		int count = 0;
		
		for (String s : board) {
			if (s == BLACKPIECE)
				count++;
		}
		
		return count;
	}
}
