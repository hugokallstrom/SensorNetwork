import java.util.*;

/**
 * Message Interface that initializes methods that will be used by Agent and 
 * Query.
 * @author Viktor Lindblad
 */
public interface Message {
	public boolean canMove();
	public Stack<Node> getPathTaken();
	public void addToPath(Node n);
	public Position handleEvents(RoutingTable routingTable);
}
