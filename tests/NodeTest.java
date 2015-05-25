import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

/**
 * Tests for the class Node.
 * @author Hugo Källström
 */
public class NodeTest {

    private Node node;
    private Node neighbour;
    private Event event;

    /**
     * Creates two nodes and sets them as
     * neighbors. Adds an event to the first node.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	
        Position pos1 = new Position(1,0);
        Position pos2 = new Position(1,1);
        node = new Node(pos1);
        neighbour = new Node(pos2);
        ArrayList<Node> neighbours = new ArrayList<Node>();
        neighbours.add(neighbour);
        ArrayList<Node> neighbours2 = new ArrayList<Node>();
        neighbours2.add(node);
        node.setNeighbors(neighbours);
        neighbour.setNeighbors(neighbours2);

        event = new Event(123, 1, node.getMyPosition());
        node.receiveEvent(event);
        node.createAgent(event);
    }

    /**
     * Tests setting node as sender
     * @throws Exception
     */
    @Test
    public void testSender() throws Exception {
        node.setSender(true);
        assertEquals(node.isSender(), true);
    }

    /**
     * Tests setting node as available
     * @throws Exception
     */
    @Test
    public void testAvailable() throws Exception {
        node.setAvailable();
        assertEquals(node.isAvailable(), true);
    }

    /**
     * Test get node position
     * @throws Exception
     */
    @Test
    public void testGetMyPosition() throws Exception {
        Position pos = node.getMyPosition();
        assertEquals(1, pos.getX());
        assertEquals(0, pos.getY());
    }

    /**
     * Test create query and get from queue
     * @throws Exception
     */
    @Test
    public void testCreateQuery() throws Exception {
        node.createQuery(event.getEventId());
        LinkedBlockingQueue messageQueue = (LinkedBlockingQueue) node.getQueue();
        assertEquals(2, messageQueue.size());
    }

    /**
     * Tests that a node is unavailable after receiving a message.
     * @throws Exception
     */
    @Test
    public void testAvailabilityAfterReceive() throws Exception {
        neighbour.createQuery(event.getEventId());
        Message message = neighbour.getMessageInQueue();
        Node node = neighbour.handleMessage(message);
        neighbour.sendMessageToNode(node);
        assertEquals(false, node.isAvailable());

    }

    /**
     * Tests if a node can receive a message and add
     * it to its queue.
     * @throws Exception
     */
    @Test
    public void testMessageReceived() throws Exception {
        assertEquals(1, node.getQueue().size());
        neighbour.createQuery(event.getEventId());
        Message message = neighbour.getMessageInQueue();
        node = neighbour.handleMessage(message);
        neighbour.sendMessageToNode(node);
        assertEquals(2, node.getQueue().size());
    }

    /**
     * Tests if the query takes the right path.
     * @throws Exception
     */
    @Test
    public void testQueryPathTaken() throws Exception {
        neighbour.createQuery(event.getEventId());
        Message message = neighbour.getMessageInQueue();
        node = neighbour.handleMessage(message);
        neighbour.sendMessageToNode(node);
        // Remove agent from queue
        node.getQueue().remove();
        Message query = (Message) node.getQueue().poll();
        Stack<Node> pathTaken = query.getPathTaken();
        assertEquals(2, pathTaken.size());

        // receiving nodes pos
        node = pathTaken.pop();
        Position pos = node.getMyPosition();
        assertEquals(1, pos.getX());
        assertEquals(0, pos.getY());

        // sending node pos
        Node node2 = pathTaken.pop();
        pos = node2.getMyPosition();
        assertEquals(1, pos.getX());
        assertEquals(1, pos.getY());
    }

    /**
     * Tests if a node can resend a query only once.
     * @throws Exception
     */
    @Test
    public void testResendQuery() throws Exception {
        Position pos = new Position(1,2);
        Node dummyNode = new Node(pos);

        neighbour.createQuery(123);
        Message message = neighbour.getMessageInQueue();
        neighbour.handleMessage(message);
        neighbour.sendMessageToNode(dummyNode);
        dummyNode.setAvailable();
        assertEquals(0, neighbour.getQueue().size());

        for(int i = 0; i < 8*Constants.timeToLiveQuery-2; i++) {
            neighbour.checkTimer();
        }

        assertEquals(0, neighbour.getQueue().size());
        neighbour.checkTimer();
        neighbour.checkTimer();
        assertEquals(1, neighbour.getQueue().size());

        message = neighbour.getMessageInQueue();
        neighbour.handleMessage(message);
        neighbour.setAvailable();
        neighbour.sendMessageToNode(dummyNode);
        assertEquals(0, neighbour.getQueue().size());

        for(int i = 0; i < 8*Constants.timeToLiveQuery; i++) {
            neighbour.checkTimer();
        }

        assertEquals(0, neighbour.getQueue().size());
    }

}
