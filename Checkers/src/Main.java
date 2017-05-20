
public class Main {
	
	public static void main(String[] args) 
	{	
		System.out.println("Bem-vindo a este divertidíssimo jogo de DAMAS! Pronto para jogar?");
		System.out.println("Para realizar um movimento introduza a posição atual e a posição de destino. Ex: A2 B1");
		
		Game game = new Game();
			
		while(!game.isGameOver()) {
			game.play();
		}
		
		if (game.isGameOver()) {
			System.out.println("Ops. Parece que terminou... Divertiu-se?");
		}
	}
}
