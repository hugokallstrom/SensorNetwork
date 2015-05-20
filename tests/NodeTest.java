import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class NodeTest {

    private Node node;
    private Node neighbour;
    private RoutingTable routingTable;
    private Event event;

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
        node.setNeighbours(neighbours);
        neighbour.setNeighbours(neighbours2);
        node.setAgentChance(1);
        neighbour.setAgentChance(1);
        event = new Event(123, 1, node.getMyPosition());
        node.receiveEvent(event);
        routingTable = node.getRoutingTable();
    }

    @Test
    public void testSender() throws Exception {
        node.setSender(true);
        assertEquals(node.isSender(), true);
    }

    @Test
    public void testAvailable() throws Exception {
        node.setAvailable();
        assertEquals(node.isAvailable(), true);
    }

    @Test
    public void testGetMyPosition() throws Exception {
        Position pos = node.getMyPosition();
        assertEquals(1, pos.getX());
        assertEquals(0, pos.getY());
    }


    @Test
    public void testCreateQuery() throws Exception {
        node.createQuery(event.getEventId());
        LinkedBlockingQueue messageQueue = (LinkedBlockingQueue) node.getQueue();
        assertEquals(2, messageQueue.size());
    }

    @Test
    public void testAvailabilityAfterReceive() throws Exception {
        neighbour.createQuery(event.getEventId());
        neighbour.handleMessage();
        assertEquals(false, node.isAvailable());

    }

    @Test
    public void testMessageReceived() throws Exception {
        assertEquals(1, node.getQueue().size());
        neighbour.createQuery(event.getEventId());
        neighbour.handleMessage();
        assertEquals(2, node.getQueue().size());
    }

    @Test
    public void testQueryPathTaken() throws Exception {
        neighbour.createQuery(event.getEventId());
        neighbour.handleMessage();
        // Remove agent from queue
        node.getQueue().remove();
        Message query = (Message) node.getQueue().poll();
        Stack<Node> pathTaken = query.getPathTaken();
        assertEquals(2, pathTaken.size());

        // receiving nodes pos
        Node node = pathTaken.pop();
        Position pos = node.getMyPosition();
        assertEquals(1, pos.getX());
        assertEquals(0, pos.getY());

        // sending node pos
        Node node2 = pathTaken.pop();
        pos = node2.getMyPosition();
        assertEquals(1, pos.getX());
        assertEquals(1, pos.getY());
    }

    @Test
    public void testQueryReply() throws Exception {
        neighbour.createQuery(event.getEventId());
        neighbour.handleMessage();
        neighbour.setAvailable();
        node.setAvailable();

        // Remove agent from queue
        node.getQueue().remove();
        assertEquals(1, node.getQueue().size());
        node.handleMessage();
        node.handleMessage();
        assertEquals(0, node.getQueue().size());
        neighbour.handleMessage();
    }

}