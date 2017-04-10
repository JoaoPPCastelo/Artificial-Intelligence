import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Test;

public class MainTest {
	
	public String runAI(String input) {
				
		AStar a = new AStar();
		Iterator<AStar.Node> it = a.solve(new Cubo(input));
		String result = "";
		
		if (it == null) 
			result = "no solution found";
		else {
			while(it.hasNext()) {
				AStar.Node node = it.next();
				if (!it.hasNext())
					result = Integer.toString(node.getG());
			}
		}
		return result;
	}

	@Test
	public void testA() {
		String initialConfig = "GGBWWYOWYYRWWYOWYYRWWYOWYYRBBG";
		String result = runAI(initialConfig);	
		assertEquals("1", result);
	}

	@Test
	public void testB() {
		String initialConfig = "GBGWYWOYWYRYWYRWYWOWYWOYWYRBGB";
		String result = runAI(initialConfig);	
		assertEquals("2", result);
	}
	
	@Test
	public void testC() {
		String initialConfig = "GGGWYWOYWYRYWYOWYWRWYWOYWYRBBB";
		String result = runAI(initialConfig);	
		assertEquals("6", result);
	}
	
	@Test
	public void testD() {
		String initialConfig = "GGBWWYOWYYRWWYOWYYRWWYOWYYRBBG";
		String result = runAI(initialConfig);	
		assertEquals("1", result);
	}
		
	@Test	
	public void testE() {
		String initialConfig = "GGBWWYOWYYRWYYRWWYOWYYRWWYOGBB";
		String result = runAI(initialConfig);	
		assertEquals("2", result);
	}
	
	@Test
	public void testF() {
		String initialConfig = "BBBYYYOWWWRYYYOWWWRYYYOWWWRGGG";
		String result = runAI(initialConfig);	
		assertEquals("0", result);
	}
	
	@Test
	public void testG() {
		String initialConfig = "BGBWWWRYYYOWYWRYWYOWWWRYYYOGBG";
		String result = runAI(initialConfig);	
		assertEquals("3", result);
	}
	
	@Test
	public void testH() {
		String initialConfig = "BGBWWWRYYYOWYWRYWYOWWWRYYYOGBG";
		String result = runAI(initialConfig);	
		assertEquals("3", result);
	}
	
	@Test
	public void testI() {
		String initialConfig = "GGBWYWRYWYRYYWRYWWOWYWOYWYOGBB";
		String result = runAI(initialConfig);	
		assertEquals("4", result);
	}
	
//	@Test
//	public void testJ() {
//		String initialConfig = "BGBWYWRYWYOYYWRYWWOYYYOWWWRGBB";
//		String result = runAI(initialConfig);	
//		assertEquals("no solution found", result);
//	}
//	
//	@Test
//	public void testK() {
//		String initialConfig = "BBGYWYRWYWOYWYRWYWOWWWOYYYRGGB";
//		String result = runAI(initialConfig);	
//		assertEquals("no solution found", result);
//	}
}