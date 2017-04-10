import java.util.List;

public interface IState {
	/**
	 @return the children of the receiver.
	 */
	 List<IState> children();
	 /**
	 @return true if the receiver equals the argument l; return false otherwise.
	 */
	 boolean isGoal(IState l);

	/**
	 @return the cost from the receiver to its father.
	 */
	int getCost(); 
	
	int getH();
}
