import java.util.*;

public class Cubo implements IState {
	private int cost, h;
	private String s;

	/**
	 * Construtor para um cubo que recebe uma string como input. Define o custo como 0 e calcula o custo estimado
	 * @param str - repreentacao do cubo
	 */
	public Cubo(String str) {
		this(str, 0, computeH(new StringBuffer(str)));
	}

	/**
	 * Construtor para um cubo
	 * @param str - representacao do cubo
	 * @param cost - custo real
	 * @param h - custo estimado
	 */
	public Cubo(String str, int cost, int h) {
		s = str;
		this.cost = cost;
		this.h = h;
	}

	public boolean equals(Object that) {
		if (that instanceof Cubo) {
			Cubo b = ((Cubo) that);
			return s.equals(b.s);
		} else
			return false;
	}

	public boolean isGoal(IState that) {
		return this.equals(that);
	}

	public String toString() {
		//return "(" + s + ", " + cost + ", " + h + ")";
		return s;
	}

	/**
	 * @return a list with all configuration states which are reachable from the
	 *         receiver, when the rules of our problem are applied.
	 */
	public List<IState> children() {
		int n, heuristic;
		List<IState> l = new ArrayList<IState>();
		Cubo child;
		
		for (int i = 0; i < s.length(); i++) {
			StringBuffer schild = new StringBuffer(s);
			if (i < 3) {
				n = verticalSwap(schild, i);
				heuristic = computeH(schild);
				child = new Cubo(schild.toString(), n, heuristic);
				l.add(child);
			}
			
			else if (i == 3 || i == 11 || i == 19 ){
				n = horizontalSwap(schild, i);
				heuristic = computeH(schild);
				child = new Cubo(schild.toString(), n, heuristic);
				l.add(child);
			}
		}
		return l;
	}

	/**
	 * Metodo que retorna o custo real
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Metodo que retorna o custo estimado
	 */
	public int getH() {
		return h;
	}
	
	/**
	 * Metodo que realiza troca de duas cores/celulas de um cubo
	 * @param s - representacao do cubo
	 * @param j - indice da celula a ser trocada
	 * @param k - indice da celula a ser trocada
	 */
	private static void swap (StringBuffer s, int j, int k) {
		char c = s.charAt(k);
		char d = s.charAt(j);
		s.setCharAt(k, d);
		s.setCharAt(j, c);
	}
	
	/**
	 * Metodo que faz as trocas verticais num cubo
	 * @param s - representacao do cubo
	 * @param i - identificacao da coluna a ser rodada
	 * @return custo da rotacao
	 */
	private static int verticalSwap(StringBuffer s, int i) {
		
		if (i == 0) {
			// 0 - 27
			swap(s, 0, 27);
			// 3 - 25
			swap(s, 3, 25);			
			// 11 - 17
			swap(s, 11, 17);			
			// 19 - 9
			swap(s, 19, 9);
			// 10 - 26
			swap(s, 10, 26);
		}
		
		else if (i == 1) {
			// 1 - 28
			swap(s, 1, 28);		
			// 4 - 24
			swap(s, 4, 24);			
			// 12 - 16
			swap(s, 12, 16);			
			// 8 - 20
			swap(s, 8, 20);
		}
		
		else if (i == 2) {
			// 2 - 29
			swap(s, 2, 29);			
			// 5 - 23
			swap(s, 5, 23);
			// 13 - 15
			swap(s, 13, 15);			
			// 21 - 7
			swap(s, 21, 7);
			// 6 - 22
			swap(s, 6, 22);
		}
		return 1;
	}
	
	/**
	 * Metodo que faz as trocas horizontais num cubo
	 * @param s - representacao do cubo
	 * @param i - identificacao da linha a ser rodada
	 * @return custo da rotacao
	 */
	private static int horizontalSwap(StringBuffer s, int i) {
		
		if (i == 3) {
			// 0 - 2
			swap(s, 0, 2);			
			// 3 - 7
			swap(s, 3, 7);
			// 4 - 8
			swap(s, 4, 8);			
			// 5 - 9
			swap(s, 5, 9);			
			// 10 - 6
			swap(s, 10, 6);	
		}
		
		else if (i == 11) {
			// 11 - 15
			swap(s, 11, 15);			
			// 12 - 16
			swap(s, 12, 16);		
			// 13 - 17
			swap(s, 13, 17);			
			// 18 - 14
			swap(s, 18, 14);	
		}
		
		else if (i == 19) {
			// 19 - 23
			swap(s, 19, 23);				
			// 20 - 24
			swap(s, 20, 24);			
			// 21 - 25
			swap(s, 21, 25);				
			// 22 - 26
			swap(s, 22, 26);			
			// 27 - 29
			swap(s, 29, 27);
		}
		return 1;
	}

	/**
	 * Metodo que estima o custo para a resolucao do cubo. Verifica a face do topo e a face lateral direita e compara as cores de cada face.
	 * Se para cada facce, existirem cores diferentes, e adicionado o valor 1 a uma variavel que sera retornada como o custo estimado.
	 * Caso a face do topo e a face lateral direita estejam corretas (cada face poossui apenas uma cor), e verificada cada linha da face frontal 
	 * de forma a identificar o custo estimado. Custo maximo estimado = 3
	 * @param s - representacao do cubo
	 * @return inteiro com custo estimado
	 */
	public static int computeH(StringBuffer s) {
		int heuristic = 0;
		if (s.charAt(0) != s.charAt(1) || s.charAt(0) != s.charAt(2)) {
			heuristic++;
		}
		if (s.charAt(6) != s.charAt(14) || s.charAt(6) != s.charAt(22)) {
			heuristic++;
		}
		if (s.charAt(0) == s.charAt(1) && s.charAt(0) == s.charAt(2) && s.charAt(6) == s.charAt(14) && s.charAt(6) == s.charAt(22)) {
			if (s.charAt(3) != s.charAt(4) || s.charAt(3) != s.charAt(5)) {
				heuristic++;
			}
			if (s.charAt(11) != s.charAt(12) || s.charAt(11) != s.charAt(13)) {
				heuristic++;
			}
			if (s.charAt(19) != s.charAt(20) || s.charAt(19) != s.charAt(21)) {
				heuristic++;
			}
		}		
		return heuristic;	
	}
}