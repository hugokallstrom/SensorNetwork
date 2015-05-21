import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

/**
 * Created by hugo on 5/21/15.
 */
public class SyncronizeTest {

    private ArrayList<Node> nodes;
    private Event event;
    private Node node1;
    private Node node2;
    private Node node3;
    private Node node4;

    @Before
    public void setUp() {
        Constants.agentChance = 1;
        node1 = new Node(new Position(0,0));
        node2 = new Node(new Position(0,1));
        node3 = new Node(new Position(1,0));
        node4 = new Node(new Position(1,1));

        nodes = new ArrayList<Node>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);

        node1.setNeighbours(findNeighbours(node1.getMyPosition()));
        node2.setNeighbours(findNeighbours(node2.getMyPosition()));
        node3.setNeighbours(findNeighbours(node3.getMyPosition()));
        node4.setNeighbours(findNeighbours(node4.getMyPosition()));

        event = new Event(100, 1, new Position(0,0));
        node1.receiveEvent(event);
    }

    @Test
    public void testReceiveAgent() {
        handleAndSendMessage(node1, node2);
        Message message = node2.getMessageInQueue();
        node2.handleMessage(message);
        assertEquals(node2.getRoutingTable().getEvent(100).getDistance(), 1);
    }

    @Test
    public void testPositionUpdate() {
        handleAndSendMessage(node1, node2);
        handleAndSendMessage(node2, node3);
        handleAndSendMessage(node3, node4);
        Message message = node4.getMessageInQueue();
        node4.handleMessage(message);
        assertEquals(node4.getRoutingTable().getEvent(100).getPosition(), node3.getMyPosition());
        assertEquals(node4.getRoutingTable().getEvent(100).getDistance(), 3);
    }

    @Test
    public void testUpdatePath() {
        handleAndSendMessage(node1, node2);
        handleAndSendMessage(node2, node3);
        handleAndSendMessage(node3, node4);

        Event event = new Event(101, 2, new Position(1,0));
        node3.receiveEvent(event);
        handleAndSendMessage(node3, node1);
        handleAndSendMessage(node1, node4);

        node4.getMessageInQueue();
        Message message = node4.getMessageInQueue();
        node4.handleMessage(message);


        assertEquals(node4.getRoutingTable().getEvent(100).getPosition(), node1.getMyPosition());
        assertEquals(node4.getRoutingTable().getEvent(100).getDistance(), 1);
    }

    public void handleAndSendMessage(Node sender, Node receiver) {
        sender.setAvailable();
        Message message = sender.getMessageInQueue();
        sender.handleMessage(message);
        receiver.setAvailable();
        sender.sendMessageToNode(receiver);
    }

    private ArrayList<Node> findNeighbours(Position myPosition) {
        ArrayList<Node> neighbours = new ArrayList<Node>();
        for(Node node : nodes) {
            Position position = node.getMyPosition();
            if(position.isNeighbour(myPosition)) {
                neighbours.add(node);
            }
        }
        return neighbours;
    }
}
