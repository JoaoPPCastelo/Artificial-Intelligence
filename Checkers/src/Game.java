import java.util.Scanner;

public class Game {
	
	static Scanner sc;
	static Board b;
	static Positions pos;
	static AI ai;
	
	// variavel que controla as jogadas do utilizador.
	public static boolean isUserPlaying = true;
	
	
	
	
	public Game() {
		sc = new Scanner(System.in);
		b = new Board();	
		pos = new Positions();
		ai = new AI(6);
	}
	
		
	public static void play() {
		
		
		while(true) {
			
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
				
				b.move(actPos, nxtPos);
			}
			
			// AI playing
			else {
				
				// TODO escolher jogada e retornar string com posicao inicial e final
				ai.makeMovement(b, pos);
				
				// TODO mostrar a jogada da AI
				System.out.println("A jogada do computador: ");
							
			}
			
		}

	}
		
		public boolean isGameOver() {
			return b.gameOver();
		}
}
