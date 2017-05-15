import java.util.Scanner;

/*
 * TODO
 *  - gerar board inicial - DONE
 *  - imprimir boards - DONE
 *  - receber instruçoes do utilizador - DONE
 *  - validar movimentos - DONE
 *  	- separar por cores - TODO
 *  - realizar movimentos - DONE
 *  - promover pecas a damas - DONE
 *  - comer pecas - DONE
 *  - criar classe/metodo play - TODO
 *  - implementar AI - TODO
 *  - human player joga com uma cor definida. (sugestao)
 *  - ver quem ganha e quem perde - TODO
 *  - simplificar o metodo eat - TODO - talvez retornar a peca a comer no validateMovements
 */

public class Main {
	
	private static void justPlay(Scanner sc, Board b, Positions pos) 
	{
		b.printBoard();
		
		System.out.print("A sua jogada: ");
		
		String[] input = sc.nextLine().split(" ");
		String actualPosition = input[0].toLowerCase();
		String nextPosition = input[1].toLowerCase();
		
		Integer actPos = pos.getPosition(actualPosition);
		Integer nxtPos = pos.getPosition(nextPosition);
					
		b.play(actPos, nxtPos);
	}

	public static void main(String[] args) 
	{	
		System.out.println("Bem-vindo a este divertidíssimo jogo de DAMAS! Pronto para jogar?");
		System.out.println("Para realizar um movimento introduza a posição atual e a posição de destino. Ex: A2 B1");
		
		Scanner sc = new Scanner(System.in);
		Board b = new Board();	
		Positions pos = new Positions();
			
		while(!b.gameOver()) {
			justPlay(sc, b, pos);
		}
		
		if (b.gameOver()) {
			System.out.println("Ops. Parece que terminou... Divertiu-se?");
			sc.close();
		}
	}
}
