/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

/**
 * @author Viktor
 * Routingtable uses by node and messages to remember information about
 * different events.
 */

public class RoutingTable {

	private HashMap<Integer, Event> hashTable;

	/**
	 * Creates a new routingTable and initiates a empty hashtable.
	 */
	public RoutingTable() {
		hashTable = new	HashMap<Integer, Event>();
	}
	
	/**
	 * Sync two different routingtables to share information about events.
     * @param nodeRoutingTable
     */
	public void syncEvents(RoutingTable nodeRoutingTable, Node currentNode) {
        HashMap<Integer, Event> nodeHashTable = nodeRoutingTable.getEventTable();

		for(int agentKey : hashTable.keySet()) {
            if(!nodeHashTable.containsKey(agentKey)) {
                Event copyAgentEvent = new Event(hashTable.get(agentKey));
                hashTable.get(agentKey).setPosition(currentNode.getMyPosition());
                nodeRoutingTable.getEventTable().put(agentKey, copyAgentEvent);
            }
        }

        for(int nodeKey : nodeHashTable.keySet()) {
            if(!hashTable.containsKey(nodeKey)) {
                Event copyNodeEvent = new Event(nodeHashTable.get(nodeKey));
                copyNodeEvent.setPosition(currentNode.getMyPosition());
                hashTable.put(nodeKey, copyNodeEvent);
            }
        }
	}
	
	/**
	 * See which routingtable that has the shortest path to an event 
	 * and saves it over the longer path.
     * @param nodeRoutingTable
     */
    public void findShortestPath(RoutingTable nodeRoutingTable, Node currentNode) {
        for(int agentKey : hashTable.keySet()) {
            Event agentEvent = hashTable.get(agentKey);
            Event nodeEvent = nodeRoutingTable.getEventTable().get(agentKey);
            if(nodeRoutingTable.getEventTable().containsKey(agentKey)) {
                if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() < nodeEvent.getDistance()) {
                    Event copyAgentEvent = new Event(agentEvent);
                    hashTable.get(agentKey).setPosition(currentNode.getMyPosition());
                    nodeRoutingTable.getEventTable().put(agentKey, copyAgentEvent);
                } else if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() > nodeEvent.getDistance()) {
                    Event copyNodeEvent = new Event(nodeEvent);
                    copyNodeEvent.setPosition(currentNode.getMyPosition());
                    hashTable.put(agentKey, copyNodeEvent);
                }
            }
        }
    }

    /**
     * Change the previous position that the event had.
     * @param previousNode
     * @param nodeRoutingTable
     */
    public void changeEventPosition(Node previousNode, RoutingTable nodeRoutingTable) {
        HashMap<Integer, Event> nodeEventTable = nodeRoutingTable.getEventTable();
        Position previousNodePosition = previousNode.getMyPosition();

        for(int  agentKey : hashTable.keySet()) {
            for(int nodeKey : nodeRoutingTable.getEventTable().keySet()) {
            	Event agentEvent = hashTable.get(agentKey);
        		Event nodeEvent = nodeEventTable.get(nodeKey);

        		if(agentKey == nodeKey && (nodeEvent.getDistance() != 0 && agentEvent.getDistance() != 0)) {
                    agentEvent.setPosition(previousNodePosition);
                    nodeEvent.setPosition(previousNodePosition);
                }
            }
        }
    }

    /**
     * Increment the distance to an event as a messages moves.
     */
    public void incrementEventDistances() {
        for(int eventKey : hashTable.keySet()) {
        	Event agentEvent = hashTable.get(eventKey);
        	agentEvent.incrementDistance();
        }
    }

    /**
     * Adds an event to the routingtable which saves it into the hashtable.
     * @param event
     */
    public void addEvent(Event event) {
        hashTable.put(event.getEventId(),event);
    }

    /**
     * Looking up the event from the given key.
     * @param requestedEventId
     * @return Event.
     */
    public Event getEvent(int requestedEventId) {
    	return hashTable.get(requestedEventId);
        
    }

    /**
     * Returns the objects hashtable. The different objects that uses 
     * the hashtable is node and messages.
     * @return Hashtable
     */
    public HashMap<Integer,Event> getEventTable(){
        return hashTable;
    }

    /**
     * Sets the objects hashtable to the given hashtable.
     * @param hashTable
     */
    public void setEventTable(HashMap<Integer, Event> hashTable) {
        this.hashTable = hashTable;
    }

    /**
     * Copies the given hashtables objects into new objects.
     */
	public void deepCopyHashtable() {
        HashMap<Integer, Event> copy = new HashMap<Integer, Event>(hashTable.size());
		for(int originalKey : hashTable.keySet()) {
	    	Event copyEvent = new Event(hashTable.get(originalKey));
	    	copy.put(originalKey, copyEvent);
	    }
	    hashTable = copy;
	}
    
    @Override
    public String toString() {
        String out = "";
        for(int eventKey : hashTable.keySet()) {
        	Event event = hashTable.get(eventKey);
        	out += event.toString();
        }
        return out;
    }
}
