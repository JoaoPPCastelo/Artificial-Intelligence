import java.util.Scanner;

public class Game {
	
	static Scanner sc;
	static Board b;
	static Positions pos;
	static AI ai;
	
	// variavel que controla as jogadas do utilizador.
	public static boolean isUserPlaying = true;
	
	
	/**
	 * Construtor do jogo
	 */
	public Game() {
		sc = new Scanner(System.in);
		b = new Board();	
		pos = new Positions();
		// 6 jogadas a frente
		ai = new AI(6);
	}
	
	
	/**
	 * Metodo que controla o jogo. Enquanto o jogo nao terminar, controla quem joga, recebe input do jogador,
	 * converte o input em indices e chama o metodo que realiza o movimento. 	
	 */
	public void play() 
	{
		// ate game over ou ate o user terminar o programa
		while(!isGameOver()) {
			
			// imprime a board
			b.printBoard();
			
			// se for o jogador a jogar
			if (isUserPlaying) {
				
				System.out.print("A sua jogada: ");
				
				String userInput = sc.nextLine();
				String[] input = userInput.split(" ");
				
				// caso o jogador tenha dado um mau input, voltar a pedir
				while (userInput.length() != 5 && input.length != 2) {
					
					System.out.println("Input invalido. Por favor introduza o movimento desejado. Ex: A2 B1");
					
					userInput = sc.nextLine();
					input = userInput.split(" ");
				}
				
				String actualPosition = input[0].toLowerCase();
				String nextPosition = input[1].toLowerCase();
				
				Integer actPos = pos.getPosition(actualPosition);
				Integer nxtPos = pos.getPosition(nextPosition);
				
				// valida movimento, come, move peca e promove, caso possivel
				int result = b.move(actPos, nxtPos);
				
				// se a jogada for valida, e a vez da AI
				if (result == 0)
					isUserPlaying = !isUserPlaying;
			}
			
			// AI playing
			else {
				
				// escolher jogada, retornar string com posicao inicial e final e faz o movimento
				String mov = ai.makeMovement(b, pos);
				
				// mostrar a jogada da AI
				String[] movs = mov.split(" ");
				
				String start = pos.getInvertedPositions(Integer.parseInt(movs[0]));
				String end = pos.getInvertedPositions(Integer.parseInt(movs[1]));
				
				System.out.print("A jogada do computador: " + start.toUpperCase() + " " + end.toUpperCase());
				
				isUserPlaying = !isUserPlaying;
			}
		}
	}
	
	
	/**
	 * Verifica se o jogo terminou
	 * @return true se o jogo terminou, false caso contrario
	 */
	public boolean isGameOver() {
		return b.gameOver();
	}
}
