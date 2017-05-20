import java.util.ArrayList;
public class Board 
{	private ArrayList<String> board;
		private String BLACKPIECE = "b";
	private String WHITEPIECE = "w";
	private String BLANK = " ";
	private String BLACKDAMA = "B";
	private String WHITEDAMA = "W";	
	/**
	 * Construtor para uma Board
	 */
	public Board() {
				board = new ArrayList<String>(64);
		initializeBoard();
	}	
	/**	 * Construtor para uma board onde e dada uma representacao da mesma	 * @param al - ArrayList com representacao da board	 */
	public Board(ArrayList<String> al) {
				board = new ArrayList<String>(64);
		board.addAll(al);
	}
		/**	 * Obter a representacao de uma board	 * @return ArrayList com a board	 */
	public ArrayList<String> getBoardRepresentation() {
				return board;
	}
	/**
	 * Inicializa a board com as pecas nas posicoes iniciais. Todos os espacos
	 * restantes sao preenchidos com -
	 */
	private void initializeBoard() {
				// espacos em branco e redefinicao do tamanho do arrayList
		for (int i = 0; i < 64; i++)
			board.add(i, BLANK);

		// pecas pretas
		for (int i = 1; i <= 8; i += 2)
			board.set(i, BLACKPIECE);
		for (int i = 8; i < 16; i += 2)
			board.set(i, BLACKPIECE);
		for (int i = 17; i < 24; i += 2)
			board.set(i, BLACKDAMA);
		// pecas brancas
		for (int i = 40; i < 48; i += 2)
			board.set(i, WHITEDAMA);
		for (int i = 49; i < 56; i += 2)
			board.set(i, WHITEDAMA);
		for (int i = 56; i < 64; i += 2)
			board.set(i, WHITEDAMA);
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
	 * @param actPos - posicao atual da peca
	 * @param nxtPos - posicao para onde se quer mover a peca
	 */
	public int move(Integer actPos, Integer nxtPos) {
				if (validateMovements(actPos, nxtPos)) {
			eat(actPos, nxtPos);
			swap(actPos, nxtPos);
						// nao interessa promover quando as pecas estao no meio da board
			if (nxtPos < 8 || nxtPos > 55)
				promotion();
		}		else 
			if (Game.isUserPlaying) {
				System.out.println("Movimento invalido.");
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
		return (Game.isUserPlaying) ? validateWhiteMovements(actPos, nxtPos) : validateBlackMovements(actPos, nxtPos);
	}
	/**
	 * Validacao dos movimentos das pecas brancas
	 * @param index - indice atual da peca
	 * @param newIndex - indice para onde se quer mover a peca
	 * @return true se o movimento respeita as regras do jogo, false caso contrario
	 */
	private boolean validateWhiteMovements(int index, int newIndex) 
	{
		boolean isValid = false;
		String piece = board.get(index);
		String newPiece = board.get(newIndex);

		// pecas simples. apenas se movem uma casa
		if (piece.equals(WHITEPIECE) && newPiece.equals(BLANK)) {
			// as pecas da esquerda apenas se podem mover para a direita
			if (index % 8 == 0) {
				// pecas brancas so podem subir V
				if (newIndex == index - 7)
					isValid = true;
				
				// pecas brancas que comem V
				else if ((newIndex == index - 14 && (board.get(index - 7).equals(BLACKPIECE) || board.get(index - 7).equals(BLACKDAMA)))						|| (newIndex == index + 14 && (board.get(index + 7).equals(BLACKPIECE) || board.get(index + 7).equals(BLACKDAMA)))) {
					isValid = true;					
				}
			}
			// as pecas da direita apenas se podem mover para a esqureda
			else if (index % 8 == 7) {
				// brancas sobem V
				if (newIndex == index - 9)
					isValid = true;
				// pecas brancas que comem V
				else if ((newIndex == index - 18 && (board.get(index - 9).equals(BLACKPIECE) || board.get(index - 9).equals(BLACKDAMA)))						||(newIndex == index + 18 && (board.get(index + 9).equals(BLACKPIECE) || board.get(index + 9).equals(BLACKDAMA)))) {
					isValid = true;					
				}
			}
			// pecas no meio da board
			else {
				// brancas sobem V
				if ((newIndex == index - 7 || newIndex == index - 9))
					isValid = true;
				// brancas que comem V
				else if ((newIndex == index - 14 && (board.get(index - 7).equals(BLACKPIECE) || board.get(index - 7).equals(BLACKDAMA)))
					|| (newIndex == index - 18 && (board.get(index - 9).equals(BLACKPIECE) || board.get(index - 9).equals(BLACKDAMA))					|| (newIndex == index + 14 && (board.get(index + 7).equals(BLACKPIECE) || board.get(index + 7).equals(BLACKDAMA)))					|| (newIndex == index + 18 && (board.get(index + 9).equals(BLACKPIECE) || board.get(index + 9).equals(BLACKDAMA))))) {
					isValid = true;					
				}
			}
		}
		// A peca que atingir a oitava casa adversaria, parando ali, sera
		// promovida a "dama", peca de movimentos mais amplos que a simples
		// peca. Assinala-se a dama sobrepondo, a pedra promovida, outra da
		// mesma cor.
		else if (piece == WHITEDAMA && newPiece == BLANK) {
			// A dama pode mover-se para tras e para frente em diagonal uma casa
			// de cada vez, diferente das outras pecas, que movimentam-se apenas
			// para frente em diagonal. A dama pode tambem tomar outra peca pela
			// frente ou por tras em diagonal.
			if (((newIndex == index - 7 && !((index%8)==7))  || (newIndex == index - 9 && !((index%8)==0)) || (newIndex == index + 7 && !((index%8)==0))|| (newIndex == index + 9&& !((index%8)==7))))				isValid = true;			    int count = 0;			    for (int i = index + 7; i < newIndex; i += 7) {			     if (board.get(i).equals(BLACKPIECE) || board.get(i).equals(BLACKDAMA)) 			      count++;			    			     if (count > 1)			      isValid = false;			    }			    count = 0;			    for (int i = newIndex + 7; i < index; i += 7) {				     if (board.get(i).equals(BLACKPIECE) || board.get(i).equals(BLACKDAMA)) 				      count++;				    				     if (count > 1)				      isValid = false;				    }			    if ((Math.abs(newIndex - index) % 7 == 0) && count == 1)			    	 isValid = true;			     count = 0;			    for (int i = index + 9; i < newIndex; i += 9) {			     if (board.get(i).equals(BLACKPIECE) || board.get(i).equals(BLACKDAMA)) 			      count++;			    			     if (count > 1)			      isValid = false;			    }			    count = 0;			    for (int i = newIndex + 9; i < index; i += 9) {				     if (board.get(i).equals(BLACKPIECE) || board.get(i).equals(BLACKDAMA)) 				      count++;				    				     if (count > 1)				      isValid = false;				    }			    if ((Math.abs(newIndex - index) % 9 == 0) && count == 1 && !(index % 8 == 0))			    	 isValid = true;			   			// brancas que comem V			    else if ((newIndex == index + 14 && !(index%8==0) && (board.get(index + 7).equals(BLACKPIECE) || board.get(index + 7).equals(BLACKDAMA)))						|| (newIndex == index + 18 && !(index%8==7) &&(board.get(index + 9).equals(BLACKPIECE) || board.get(index + 9).equals(BLACKDAMA)))						|| (newIndex == index - 14 && !(index%8==7) && (board.get(index - 7).equals(BLACKPIECE) || board.get(index - 7).equals(BLACKDAMA)))						|| (newIndex == index - 18 && !(index%8==0) &&(board.get(index - 9).equals(BLACKPIECE) || board.get(index - 9).equals(BLACKDAMA))))					isValid = true;			}			return isValid;
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
		if (piece.equals(BLACKPIECE) && newPiece.equals(BLANK)) {
			// A peca movimenta-se em diagonal, sobre as casas escuras, para a
			// frente, e uma casa de cada vez.			
			// as pecas da esquerda apenas se podem mover para a direita
			if (index % 8 == 0) {
				// pecas pretas so podem descer V
				if (newIndex == index + 9 )
					isValid = true;
				// pecas pretas que comem 
				else if ((newIndex == index + 18 && !(index%8==7) &&  (board.get(index + 9).equals(WHITEPIECE) || board.get(index + 9).equals(WHITEDAMA)))						|| (newIndex == index - 18 && !(index%8==0) && (board.get(index - 9).equals(WHITEPIECE) || board.get(index - 9).equals(WHITEDAMA))))
					isValid = true;
			}
	
			// as pecas da direita apenas se podem mover para a esqureda
			else if (index % 8 == 7) {
				// pretas descem V
				if (newIndex == index + 7)
					isValid = true;
	
				// pecas pretas que comem 
				else if ((newIndex == index + 14 && !(index%8==0)&& (board.get(index + 7).equals(WHITEPIECE) || board.get(index + 7).equals(WHITEDAMA)))						|| (newIndex == index - 14 && !(index%8==7) && (board.get(index - 7).equals(WHITEPIECE) || board.get(index - 7).equals(WHITEDAMA))))
					isValid = true;
			}
			// pecas no meio da board
			else {
				// pretas descem V
				if ((newIndex == index + 7 || newIndex == index + 9 || newIndex == index - 7 || newIndex == index - 9))
					isValid = true;
				// pretas que comem V
				else if ((newIndex == index + 14 && !(index%8==0) && (board.get(index + 7).equals(WHITEPIECE) || board.get(index + 7).equals(WHITEDAMA)))
						|| (newIndex == index + 18 && !(index%8==7) &&(board.get(index + 9).equals(WHITEPIECE) || board.get(index + 9).equals(WHITEDAMA)))						|| (newIndex == index - 14 && !(index%8==7) && (board.get(index - 7).equals(WHITEPIECE) || board.get(index - 7).equals(WHITEDAMA)))						|| (newIndex == index - 18 && !(index%8==0) &&(board.get(index - 9).equals(WHITEPIECE) || board.get(index - 9).equals(WHITEDAMA))))
					isValid = true;
			}
		}
	
		// A peca que atingir a oitava casa adversaria, parando ali, sera
		// promovida a "dama", peca de movimentos mais amplos que a simples
		// pca. Assinala-se a dama sobrepondo, a pedra promovida, outra da
		// mesma cor.
		else if (piece.equals(BLACKDAMA) && newPiece.equals(BLANK)) {
			if (((newIndex == index - 7 && !((index%8)==7))  || (newIndex == index - 9 && !((index%8)==0)) || (newIndex == index + 7 && !((index%8)==0))|| (newIndex == index + 9 && !((index%8)==7))))				isValid = true;						int count = 0;		    for (int i = index + 7; i < newIndex; i += 7) {		     if (board.get(i).equals(WHITEPIECE) || board.get(i).equals(WHITEDAMA)) 		      count++;		    		     if (count > 1)		      isValid = false;		    }		    count = 0;		    for (int i = newIndex + 7; i < index; i += 7) {			     if (board.get(i).equals(WHITEPIECE) || board.get(i).equals(WHITEDAMA)) 			      count++;			    			     if (count > 1)			      isValid = false;			    }		    if ((Math.abs(newIndex - index) % 7 == 0) && count == 1)		    	 isValid = true;		     count = 0;		    for (int i = index + 9; i < newIndex; i += 9) {		     if (board.get(i).equals(WHITEPIECE) || board.get(i).equals(WHITEDAMA)) 		      count++;		    		     if (count > 1)		      isValid = false;		    }		    count = 0;		    for (int i = newIndex + 9; i < index; i += 9) {			     if (board.get(i).equals(WHITEPIECE) || board.get(i).equals(WHITEDAMA)) 			      count++;			    			     if (count > 1)			      isValid = false;			    }		    if ((Math.abs(newIndex - index) % 9 == 0) && count == 1 && !(index % 8 == 0))		    	 isValid = true;		    			// pretas que comem V		    else if ((newIndex == index + 14 && !(index%8==0) && (board.get(index + 7).equals(WHITEPIECE) || board.get(index + 7).equals(WHITEDAMA)))					|| (newIndex == index + 18 && !(index%8==7) &&(board.get(index + 9).equals(WHITEPIECE) || board.get(index + 9).equals(WHITEDAMA)))					|| (newIndex == index - 14 && !(index%8==7) && (board.get(index - 7).equals(WHITEPIECE) || board.get(index - 7).equals(WHITEDAMA)))					|| (newIndex == index - 18 && !(index%8==0) &&(board.get(index - 9).equals(WHITEPIECE) || board.get(index - 9).equals(WHITEDAMA))))				isValid = true;		}
		return isValid;
	}	
	/**
	 * Verifica se uma peca chegou a extremidade do adversario, e se sim, torna-a numa dama
	 */
	private void promotion() {
		for (int i = 0; i < 8; i++)
			if (board.get(i).equals(WHITEPIECE))
				board.set(i, WHITEDAMA);
		for (int i = 56; i < 64; i++)
			if (board.get(i).equals(BLACKPIECE))
				board.set(i, BLACKDAMA);
	}

	/**
	 * Metodo que verifica se a partir dos movimentos dados pelo utilizador, alguma peca pode ser comida
	 * @param index - indice da peca antes do movimento
	 * @param newIndex - indice para onde a peca sera movida
	 */
	private void eat(int index, int newIndex) 
	{
		String piece = board.get(index);
		String newPiece = board.get(newIndex);		if (piece.equals(BLACKPIECE) && newPiece.equals(BLANK)) {						// preta come branca superior esquerda			if (newIndex == index - 18 && (board.get(index - 9).equals(WHITEPIECE) || board.get(index - 9).equals(WHITEDAMA))) 						board.set(index - 9, BLANK);						// preta come branca superior direita			else if (newIndex == index - 14 && (board.get(index - 7).equals(WHITEPIECE) || board.get(index - 7).equals(WHITEDAMA))) 						board.set(index - 7, BLANK);						// preta come branca inferior esquerda			else if (newIndex == index + 14 && (board.get(index + 7).equals(WHITEPIECE) || board.get(index + 7).equals(WHITEDAMA))) 							board.set(index + 7, BLANK);						// preta come branca inferior esquerda			else if (newIndex == index + 18 && (board.get(index + 9).equals(WHITEPIECE) || board.get(index + 9).equals(WHITEDAMA)))							board.set(index + 9, BLANK);			}				else if (piece.equals(WHITEPIECE) && newPiece.equals(BLANK)) {						// preta come branca cima esquerda			if (newIndex == index - 18 && (board.get(index - 9).equals(BLACKPIECE) || board.get(index - 9).equals(BLACKDAMA)))							board.set(index - 9, BLANK);						// preta come branca cima direita			else if (newIndex == index - 14 && (board.get(index - 7).equals(BLACKPIECE) || board.get(index - 7).equals(BLACKDAMA))) 					board.set(index - 7, BLANK);							// preta come branca abaixo esquerda			else if (newIndex == index + 14 && (board.get(index + 7).equals(BLACKPIECE) || board.get(index + 7).equals(BLACKDAMA))) 				board.set(index + 7, BLANK);	
					// preta come branca abaixo direita			else if (newIndex == index + 18 && (board.get(index + 9).equals(BLACKPIECE) || board.get(index + 9).equals(BLACKDAMA))) 				board.set(index + 9, BLANK);								}				// damas comem		else if ((Math.abs(newIndex - index) % 7 == 0) && piece.equals(WHITEDAMA)) {							for (int i = index + 7; i < newIndex; i += 7) {							if (board.get(i).equals(BLACKPIECE) || board.get(i).equals(BLACKDAMA))					board.set(i, BLANK);			}					for (int i = newIndex + 7; i < index; i += 7) {				if (board.get(i).equals(BLACKPIECE) || board.get(i).equals(BLACKDAMA))					board.set(i, BLANK);			}		}				else if ((Math.abs(newIndex - index) % 9 == 0) && piece.equals(WHITEDAMA)) {					for (int i = index + 9; i < newIndex; i += 9) {						if (board.get(i).equals(BLACKPIECE) || board.get(i).equals(BLACKDAMA))					board.set(i, BLANK);			}					for (int i = newIndex + 9; i < index; i += 9) {						if (board.get(i).equals(BLACKPIECE) || board.get(i).equals(BLACKDAMA))					board.set(i, BLANK);			}		}				else if ((Math.abs(newIndex - index) % 7 == 0) && piece.equals(BLACKDAMA)) {							for (int i = index + 7; i < newIndex; i += 7) {						if (board.get(i).equals(WHITEPIECE) || board.get(i).equals(WHITEDAMA))					board.set(i, BLANK);			}					for (int i = newIndex + 7; i < index; i += 7) {						if (board.get(i).equals(WHITEPIECE)|| board.get(i).equals(WHITEDAMA))					board.set(i, BLANK);			}		}					else if ((Math.abs(newIndex - index) % 9 == 0) && newPiece.equals(BLACKDAMA)) {			for (int i = index + 9; i < newIndex; i += 9) {							if (board.get(i).equals(WHITEPIECE) || board.get(i).equals(WHITEDAMA))					board.set(i, BLANK);			}					for (int i = newIndex + 9; i < index; i += 9) {				if (board.get(i).equals(WHITEPIECE) || board.get(i).equals(WHITEDAMA))					board.set(i, BLANK);			}		}			}			
	
	/**	 * Metodo que percorre a board e calcula todos os movimentos possiveis	 * @param player - jogador que esta a jogar	 * @return ArrayList com todos os movimentos possiveis para um jogador	 */	public ArrayList<String> allValidMovements(boolean player) 	{			String color = player ? WHITEPIECE : BLACKPIECE;		String dama = player ? WHITEDAMA : BLACKDAMA;		ArrayList<String> possibleMovent = new ArrayList<String>();		// linhas		for(int j = 0; j < 8; j++) {			// colunas			for (int i = 0; i < 8; i++) {				String piece = board.get(j*8 + i);				if (piece == color || piece == dama)					possibleMovent.addAll(validMovements(j*8 + i, player));			}		}		return possibleMovent;	}		/**	 * Metodo que calcula os movimentos possiveis que a AI pode fazer	 * @param index - indice atual da peca	 * @param player - jogador que esta a jogar	 * @return ArrayList com movimentos possiveis	 */	public ArrayList<String> validMovements(int index, boolean player) 	{		ArrayList<String> movements = new ArrayList<String>();		String piece = board.get(index);		if (piece == WHITEPIECE || piece == BLACKPIECE) {			int temp = piece == BLACKPIECE ? 1 : -1;			int newRow = (int) Math.floor(index/8) + temp;			if (newRow >= 0 || newRow < 8) {				int newCol = (index%8) + 1;				if (newCol < 8 && board.get(newRow*8 + newCol) == BLANK) {					movements.add(index + " " + (newRow*8 + newCol));				}				newCol = (index%8) - 1;				if (newCol >= 0 && board.get(newRow*8 + newCol) == BLANK) {					movements.add(index + " " + (newRow*8 + newCol));				}				}		}		// se for dama		else {			int newRow = (int) (Math.floor(index/8) + 1);					if (newRow < 8) {				int newCol = (index%8) + 1;				if (newCol < 8 && board.get(newRow*8 + newCol) == BLANK) {					movements.add(index + " " + (newRow*8 + newCol));				}								newCol = (index%8) - 1;				if (newCol >= 0 && board.get(newRow*8 + newCol) == BLANK) {					movements.add(index + " " + (newRow*8 + newCol));				}					}						newRow = (int) (Math.floor(index/8) - 1);			if (newRow >= 0) {				int newCol = (index%8) + 1;				if (newCol < 8 && board.get(newRow*8 + newCol) == BLANK) {					movements.add(index + " " + (newRow*8 + newCol));				}								newCol = (index%8) - 1;				if (newCol >= 0 && board.get(newRow*8 + newCol) == BLANK) {					movements.add(index + " " + (newRow*8 + newCol));				}					}		}		movements.addAll(getValidSkipMovements(index, player));		return movements;	}	/**	 * Metodo que calcula os movimentos em que e podera ser possivel comer uma peca do adversario	 * @param index - indice da peca	 * @param player - jogador em questao	 * @return ArrayList como os movimentos possiveis encontrados	 */	public ArrayList<String> getValidSkipMovements(int index, boolean player) 	{		ArrayList<String> movements = new ArrayList<String>();		ArrayList<Integer> possibilities = new ArrayList<Integer>();				String piece = board.get(index);		int row = (int) Math.floor(index/8);		int col = index%8;		if (player && piece == WHITEPIECE) {					possibilities.add(fromPoint2Index(row-2, col+2));			possibilities.add(fromPoint2Index(row-2, col-2));		} 		else if (!player && piece == BLACKPIECE) {			possibilities.add(fromPoint2Index(row+2, col+2));			possibilities.add(fromPoint2Index(row+2, col-2));		}		else if (piece == WHITEDAMA || piece == BLACKDAMA) {			possibilities.add(fromPoint2Index(row+2, col+2));			possibilities.add(fromPoint2Index(row+2, col-2));			possibilities.add(fromPoint2Index(row-2, col+2));			possibilities.add(fromPoint2Index(row-2, col-2));		}		for (int i = 0; i < possibilities.size(); i++) {			Integer pos = possibilities.get(i);			if(pos > 0 && pos < 64 && board.get(pos) == BLANK && isOpponent(player, midleIndex(index, pos)))				movements.add(index + " " + pos);					}		return movements;	}	/**	 * Calcula o indice que se encontra no meio de dois indices	 * @param start - indice inicial	 * @param end - indice final	 * @return indice da peca do meio	 */	private int midleIndex(int start, int end) 	{		int startCol = start%8;		int startRow = start/8;		int endCol = end%8;		int endRow = end/8;				return fromPoint2Index((startRow + endRow)/2, (startCol + endCol)/2);		}		/**	 * Verifica se uma determinada peca pertence ao adversario	 * @param player - jogador (true para humano, false para a AI)	 * @param index - indice da peca em questao	 * @return true se a peca do index pertence ao adversario	 */	private boolean isOpponent(boolean player, int index) 	{		String piece = board.get(index);		if (!player && (piece == WHITEPIECE || piece == WHITEDAMA))			return true;		if (player && (piece == BLACKPIECE || piece == BLACKDAMA))			return true;		return false;	}		/**	 * Metodo que converte linhas e colunas num indice que corresponde a posicao do ArrayList	 * @param row - linha	 * @param col - coluna	 * @return - indice correspondente no ArrayList	 */	private int fromPoint2Index (int row, int col) {		return row*8 + col;	}	/**	 * Verifica quando nao existem pecas de uma determinada cor. Caso aconteca, o jogo termina	 */	public boolean gameOver() 	{		return ((!board.contains(BLACKPIECE) && !board.contains(BLACKDAMA)) 				|| (!board.contains(WHITEPIECE) && !board.contains(WHITEDAMA)));	}	/**	 * Conta o numero de damas brancas existentes na board	 * @return numero de damas brancas existentes na board	 */	public double getWhiteDamas() 	{		int count = 0;		for (String s : board) {			if (s == WHITEDAMA)				count++;		}		return count;	}	/**	 * Conta o numero de pecas brancas existentes na board	 * @return numero de pecas brancas existentes na board	 */	public double getWhitePieces() 	{		int count = 0;		for (String s : board) {			if (s == WHITEPIECE)				count++;		}		return count;	}	/**	 * Conta o numero de damas pretas existentes na board	 * @return numero de damas pretas existentes na board	 */	public double getBlackDamas() 	{		int count = 0;			for (String s : board) {			if (s == BLACKDAMA)				count++;		}		return count;	}	/**	 * Conta o numero de pecas pretas existentes na board	 * @return numero de pecas pretas existentes na board	 */	public double getBlackPieces() 	{			int count = 0;			for (String s : board) {			if (s == BLACKPIECE)				count++;		}		return count;	}
}
