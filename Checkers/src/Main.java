
/*
 * TODO
 *  - gerar board inicial - DONE
 *  - imprimir boards - DONE
 *  - receber instru�oes do utilizador - DONE
 *  - validar movimentos - DONE
 *  	- separar por cores - DONE
 *  - realizar movimentos - DONE
 *  - promover pecas a damas - DONE
 *  - comer pecas - DONE
 *  - criar classe/metodo play - DONE -> Game.java
 *  - implementar AI - TODO
 *  - human player joga com uma cor definida BRANCAS!!!. - criar variavel de controlo DONE
 *  - ver quem ganha e quem perde - TODO
 *  - simplificar o metodo eat - TODO - talvez retornar a peca a comer no validateMovements
 *  controlar minmax de jogadores sem influenciar turns - DONE?
 *  TODO na AI damas podem andar mais do que uma casa
 */

public class Main {
	
	public static void main(String[] args) 
	{	
		System.out.println("Bem-vindo a este divertid�ssimo jogo de DAMAS! Pronto para jogar?");
		System.out.println("Para realizar um movimento introduza a posi��o atual e a posi��o de destino. Ex: A2 B1");
		
		Game game = new Game();
			
		while(!game.isGameOver()) {
			Game.play();
		}
		
		if (game.isGameOver()) {
			System.out.println("Ops. Parece que terminou... Divertiu-se?");
		}
	}
}
