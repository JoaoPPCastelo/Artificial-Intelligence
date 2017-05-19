import java.util.Scanner;

public class Game {
	
	static Scanner sc;
	static Board b;
	static Positions pos;
	static AI ai;
	
	// variavel que controla as jogadas do utilizador.
	public static boolean isUserPlaying = true;
	
	/**
	 * 
	 */
	public Game() {
		sc = new Scanner(System.in);
		b = new Board();	
		pos = new Positions();
		// 6 profundidade
		ai = new AI(6);
	}
	
	/**
	 * 	
	 */
	public static void play() 
	{
		// ate game over ou ate o user terminar o programa - DONE
		while(true && !isGameOver()) {
			// imprime a board
			b.printBoard();
			
			// se for o jogador a jogar
			if (isUserPlaying) {
				System.out.print("A sua jogada: ");
				
				String[] input = sc.nextLine().split(" ");
				String actualPosition = input[0].toLowerCase();
				String nextPosition = input[1].toLowerCase();
				
				Integer actPos = pos.getPosition(actualPosition);
				Integer nxtPos = pos.getPosition(nextPosition);
				
				// valida movimento, come, move peca e promove, caso possivel
				int result = b.move(actPos, nxtPos);
				
				if (result == 0)
					isUserPlaying = !isUserPlaying;
			}
			// AI playing
			else {
				// DONE escolher jogada e retornar string com posicao inicial e final e faz o movimento
				String mov = ai.makeMovement(b, pos);
				
				// DONE mostrar a jogada da AI
				String[] movs = mov.split(" ");
				
				String start = pos.getInvertedPositions(Integer.parseInt(movs[0]));
				String end = pos.getInvertedPositions(Integer.parseInt(movs[1]));
				
				System.out.print("A jogada do computador: " + start.toUpperCase() + " " + end.toUpperCase());
				
				isUserPlaying = !isUserPlaying;
			}
		}
	}
		
	public static boolean isGameOver() {
		return b.gameOver();
	}
}
