import java.util.*;

public class AStar {
	public class Node {
		 private IState state;
		 private Node father;
		 private int g, h; 
		
		 public Node(IState l, Node n) {
			 state = l;
			 father = n;
			 g = l.getCost();
			 h = l.getH();
			 if (father != null) 
				 g += father.g;
		 }
		 
		public IState getState() { 
			 return state; 
		}
		
		public String toString() { 
			return "(" + state.toString() + ", " + g + ", " + h + ")"; 
		}
		
		/**
		 * Metodo que retorna o custo do estado atual
		 * @return custo do estado
		 */
		public int getG() {
			return g;
		}

		/**
		 * Metodo que retorna o custo estimado proveniente da heuristica utilizada
		 * @return custo estimado
		 */
		public int getH() {
			return h;
		}

		/**
		 * Metodo que retorna o valor do F (custo real (g) + custo estimado(h)) de um dado estado
		 * @return soma do custo real (g) + custo estimado (h)
		 */
		public int getF() {
			return g+h;
		}
	}
	
	private Node actual;
	
	/**
	 * Metodo que cria sucessores para um dado estado e que os adiciona a uma List caso o no criado nao seja null nem igual ao seu pai
	 * @param n No em questao
	 * @return List com os sucessores gerados
	 */
	final private List<Node> sucessores(Node n) {
		List<Node> sucs = new ArrayList<Node>();
		List<IState> children = n.state.children();
		for(IState e: children) { 
			if (n.father == null || !e.equals(n.father.state)){
				Node nn = new Node(e, n);
				sucs.add(nn);
			}
		}
		// System.out.println("# sucessores: " + sucs.size());
		return sucs;
	}
	
	/**
	 * Metodo que compara um estado com os oito estados finais possiveis.
	 * @param actualState
	 * @return true se o estado atual e final, falso caso contrario.
	 */
	private boolean isGoal(IState actualState) {
		IState goalR0 = new Cubo("GGGWWWOYYYRWWWOYYYRWWWOYYYRBBB");
		IState goalR1 = new Cubo("RRRWWWGYYYBWWWGYYYBWWWGYYYBOOO");
		IState goalR2 = new Cubo("BBBWWWRYYYOWWWRYYYOWWWRYYYOGGG");
		IState goalR3 = new Cubo("OOOWWWBYYYGWWWBYYYGWWWBYYYGRRR");
		IState goalR4 = new Cubo("GGGYYYRWWWOYYYRWWWOYYYRWWWOBBB");
		IState goalR5 = new Cubo("OOOYYYGWWWBYYYGWWWBYYYGWWWBRRR");
		IState goalR6 = new Cubo("BBBYYYOWWWRYYYOWWWRYYYOWWWRGGG");
		IState goalR7 = new Cubo("RRRYYYBWWWGYYYBWWWGYYYBWWWGOOO");
		
		return goalR0.equals(actualState) ||
				goalR1.equals(actualState) || 
				goalR2.equals(actualState) || 
				goalR3.equals(actualState) || 
				goalR4.equals(actualState) || 
				goalR5.equals(actualState) || 
				goalR6.equals(actualState) || 
				goalR7.equals(actualState);
	}

	/**
	 * Metodo que resolve o cubo, adiciona nos criados a uma queue com nos a serem testados e 
	 * os nos que nao sao solucao mas que ja foram testados sao adicionados a uma list
	 * @param s Estado do cubo
	 * @return iterador com o caminho para a resolucao do cubo
	 */
	final public Iterator<Node> solve(IState s) {
		List<Node> fechados = new ArrayList<Node>();
		Queue<Node> abertos = new PriorityQueue<Node>(11, new NodeComparator());
		abertos.add(new Node(s, null));
		// int count = 1;
		
		for(;;) {
			if (abertos.isEmpty()) {
				return null;
			}
			actual = abertos.poll();
			if (isGoal(actual.state)) {
				class IT implements Iterator<Node> {
					private Node last;
					private Stack<Node> stack;
					
					public IT () {
						last = actual;
						stack = new Stack<Node>();
						while(last != null) {
							stack.push(last);
							last = last.father;
						}
					}
					
					public boolean hasNext() { 
						return !stack.empty(); 
					}
					
					public Node next() { 
						return stack.pop(); 
					}
					
					public void remove() {
						throw new UnsupportedOperationException();
					}
				}
				// System.out.println("Branch factor: " + abertos.size() + fechados.size());
				return new IT();
				} 
			else {
				fechados.add(actual);
				boolean contains;
				
				for(Node e: sucessores(actual)) {
					contains = false;
					for (Node f: fechados)
						if (f.state.equals(e.state)) 
							contains = true;
						if (!contains) {
							abertos.add(e);
							// count++;
						}
				 }
				// System.out.println("count: " + count);
			}
		}
	}
}