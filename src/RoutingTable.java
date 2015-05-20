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

	private Hashtable<Integer, Event> hashTable;
	
	/**
	 * Creates a new routingTable and initiates a empty hashtable.
	 */
	public RoutingTable() {
		hashTable = new	Hashtable<Integer, Event>();
	}
	
	/**
	 * Sync two different routingtables to share information about events.
	 * @param nodeRoutingTable
	 */
	public void syncEvents(RoutingTable nodeRoutingTable){
		
        Hashtable<Integer, Event> nodeHashTable = nodeRoutingTable.getEventTable();

		if(nodeHashTable.isEmpty()) {
            nodeRoutingTable.setEventTable(new Hashtable<Integer, Event>(hashTable));
            return;
		}
		
		for(int agentKey : nodeHashTable.keySet()) {
            if(!nodeHashTable.containsKey(agentKey)) {
                nodeHashTable.put(agentKey, nodeHashTable.get(agentKey));
            }
		}

        for(int nodeKey : nodeHashTable.keySet()) {
            if(!hashTable.containsKey(nodeKey)) {
                hashTable.put(nodeKey, nodeHashTable.get(nodeKey));
            }
        }
	}
	
	/**
	 * See which routingtable that has the shortest path to an event 
	 * and saves it over the longer path.
	 * @param nodeRoutingTable
	 */
    public void findShortestPath(RoutingTable nodeRoutingTable) {
        for(int agentKey : hashTable.keySet()) {
        	for(int nodeKey : nodeRoutingTable.hashTable.keySet()) {
        		
        		Event agentEvent = hashTable.get(agentKey);
        		Event nodeEvent = nodeRoutingTable.hashTable.get(nodeKey);
        		
        		if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() < nodeEvent.getDistance()) {
                    Event copyAgentEvent = new Event(agentEvent);
                   // nodeEvent.setPosition(agentEvent.getPosition());
                    nodeRoutingTable.hashTable.put(nodeKey, copyAgentEvent);
                
        		} else if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() > nodeEvent.getDistance()) {
        			Event copyNodeEvent = new Event(nodeEvent);
                  //  agentEvent.setPosition(nodeEvent.getPosition());
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
    	
    	Hashtable<Integer, Event> nodeEventTable = nodeRoutingTable.getEventTable();
        Position previousNodePosition = previousNode.getMyPosition();
        
        for(int  agentKey : hashTable.keySet()) {
            for(int nodeKey : nodeRoutingTable.hashTable.keySet()) {
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
    public Hashtable<Integer,Event> getEventTable(){
        return hashTable;
    }

    /**
     * Sets the objects hashtable to the given hashtable.
     * @param hashTable
     */
    public void setEventTable(Hashtable<Integer, Event> hashTable) {
        this.hashTable = hashTable;
    }

    /**
     * Copies the given hashtables objects into new objects.
     */
	public void deepCopyHashtable() {
		Hashtable<Integer, Event> copy = new Hashtable<Integer, Event>(hashTable.size());
	    
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
