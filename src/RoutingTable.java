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
	public void syncEvents(RoutingTable nodeRoutingTable, Node currentNode, Node previousNode){
        Hashtable<Integer, Event> nodeHashTable = nodeRoutingTable.getEventTable();


		if(nodeHashTable.isEmpty()) {
			
            nodeRoutingTable.setEventTable(new Hashtable<Integer, Event>(hashTable));
            
            return;
		}
		
		for(int agentKey : hashTable.keySet()) {
            if(!nodeHashTable.containsKey(agentKey)) {
                Event copyAgentEvent = new Event(hashTable.get(agentKey));
                /*if(copyAgentEvent.getDistance() != 0) {
                    hashTable.get(agentKey).setPosition(currentNode.getMyPosition());
                }*/
                nodeRoutingTable.getEventTable().put(agentKey, copyAgentEvent);
            }
        }

        for(int nodeKey : nodeHashTable.keySet()) {
            if(!hashTable.containsKey(nodeKey)) {
                Event copyNodeEvent = new Event(nodeHashTable.get(nodeKey));
                /*if(copyNodeEvent.getDistance() != 0) {
                    copyNodeEvent.setPosition(currentNode.getMyPosition());
                }*/
                hashTable.put(nodeKey, copyNodeEvent);
            }
        }
	}
	
	/**
	 * See which routingtable that has the shortest path to an event 
	 * and saves it over the longer path.
     * @param nodeRoutingTable
     * @param previousNode
     */
    public void findShortestPath(RoutingTable nodeRoutingTable, Node currentNode, Node previousNode) {
        for(int agentKey : hashTable.keySet()) {
        	for(int nodeKey : nodeRoutingTable.getEventTable().keySet()) {
        		Event agentEvent = hashTable.get(agentKey);
        		Event nodeEvent = nodeRoutingTable.getEventTable().get(nodeKey);
        		
        		if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() < nodeEvent.getDistance()) {
                   /* System.out.println("Copied event");
                    System.out.println("node had this distance"+nodeEvent.getDistance());
                    System.out.println("Agent had this distance"+agentEvent.getDistance());
                    System.out.println("node ID: "+ nodeKey);
                    System.out.println("Agent ID: "+ agentKey);
        			System.out.println("Node event position :"+nodeEvent.getPosition().toString());
        			System.out.println("Agent event position :"+agentEvent.getPosition().toString());*/

        			Event copyAgentEvent = new Event(agentEvent);
        			Event copyNodeEvent = new Event(nodeEvent);
        			/*
        			System.out.println("node ID: "+ nodeKey);
                    System.out.println("Agent ID: "+ agentKey);*/
                    //nodeEvent.setPosition(agentEvent.getPosition());
                    nodeRoutingTable.getEventTable().put(nodeKey, copyAgentEvent);
                    
                   /* System.out.println("Node event have position: "+nodeEvent.getPosition().toString());
        			System.out.println("agent event have position: "+agentEvent.getPosition().toString());
        			System.out.println("copied event have position: "+copyAgentEvent.getPosition().toString());
        			System.out.println("node now have this distance"+copyAgentEvent.getDistance());
                    System.out.println("Agent now have this distance"+agentEvent.getDistance());
                    System.out.println("node ID: "+ nodeKey);
                    System.out.println("Agent ID: "+ agentKey);*/
                

        		} else if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() > nodeEvent.getDistance()) {
        			/*System.out.println("Copied event");
        			System.out.println("node had this distance"+nodeEvent.getDistance());
                    System.out.println("Agent had this distance"+agentEvent.getDistance());
                    System.out.println("node ID: "+ nodeKey);
                    System.out.println("Agent ID: "+ agentKey);
                    System.out.println("Node event position :"+nodeEvent.getPosition().toString());
        			System.out.println("Agent event position :"+agentEvent.getPosition().toString());*/
                    
                    Event copyNodeEvent = new Event(nodeEvent);
                    //agentEvent.setPosition(nodeEvent.getPosition());
        			hashTable.put(agentKey, copyNodeEvent);
                    /*System.out.println("node now have this distance"+nodeEvent.getDistance());
                    System.out.println("Agent now have this distance"+copyNodeEvent.getDistance());
                    System.out.println("node ID: "+ nodeKey);
                    System.out.println("Agent ID: "+ agentKey);
        			System.out.println("Node event have position: "+nodeEvent.getPosition().toString());
        			System.out.println("agent event have position: "+agentEvent.getPosition().toString());
        			System.out.println("copied event have position: "+copyNodeEvent.getPosition().toString());*/
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
