import java.util.Queue;

public class MagicSquare {

	
	public void initializeGame(int n, Queue<Board> boards, int N, int magicSum ) {
		for (int i = 0; i < n; i++) {
			// initialize, generate a new board and compute it's fitness
			Board b = new Board(N, magicSum);
			b.generateBoard();
			b.computeFitness();
			boards.add(b);
					
		}	
	}
	
	public void printBoard() {
		
	}
	
}
