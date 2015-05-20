/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

public class RoutingTable {

	private Hashtable<Integer, Event> hashTable;
	
	public RoutingTable() {
		hashTable = new	Hashtable<Integer, Event>();
	}
	
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
	
    public void findShortestPath(RoutingTable nodeRoutingTable) {
        for(int agentKey : hashTable.keySet()) {
        	for(int nodeKey : nodeRoutingTable.hashTable.keySet()) {
        		Event agentEvent = hashTable.get(agentKey);
        		Event nodeEvent = nodeRoutingTable.hashTable.get(nodeKey);
        		if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() < nodeEvent.getDistance()) {
                    Event copyAgentEvent = new Event(agentEvent);
                    nodeRoutingTable.hashTable.put(nodeKey, copyAgentEvent);
                } else if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() > nodeEvent.getDistance()) {
        			Event copyNodeEvent = new Event(nodeEvent);
        			hashTable.put(agentKey, copyNodeEvent);
        		}
        	}
        }
    }

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

    public void incrementEventDistances() {
        for(int eventKey : hashTable.keySet()) {
        	Event agentEvent = hashTable.get(eventKey);
        	agentEvent.incrementDistance();
        }
    }

    public void addEvent(Event event) {
        hashTable.put(event.getEventId(),event);
    }

    public Event getEvent(int requestedEventId) {
    	return hashTable.get(requestedEventId);
        
    }

    public Hashtable<Integer,Event> getEventTable(){
        return hashTable;
    }

    public void setEventTable(Hashtable<Integer, Event> hashTable) {
        this.hashTable = hashTable;
    }

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
