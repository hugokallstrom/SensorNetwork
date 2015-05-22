import java.util.*;

/**
 * Message Interface that initialises methods that will be used by Agent and Query.
 *
 * File:        Message.java
 * @author      ViktorLindblad
 * Date:        2015-05-22
 */

public interface Message {

	public boolean canMove();
	public Stack<Node> getPathTaken();
	public void addToPath(Node n);
	public Position handleEvents(RoutingTable routingTable);
}
