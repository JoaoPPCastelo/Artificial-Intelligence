import java.util.*;

public class Main {

	/**
	 * Metodo que recebe input, resolve o problema e imprime o custo da resolucao do cubo ate chegar ao estado final
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		// read initial configuration
		String initialConfig = sc.nextLine() ;
		while (sc.hasNext()) {
			String temp = sc.nextLine();
			initialConfig += temp;
		}
		sc.close();		
				
		AStar a = new AStar();
		Iterator<AStar.Node> it = a.solve(new Cubo(initialConfig));
		
		if (it == null) 
			System.out.println("no solution found");
		else {
			while(it.hasNext()) {
				AStar.Node node = it.next();
				if (!it.hasNext())
					System.out.println(node.getG());
			}
		}
	}
}
