/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;
public interface Message {
	
	public boolean canMove();
	public Stack<Node> getPathTaken();
	public void addToPath(Node n);
	public Position syncEvents(RoutingTable routingTable);
}
