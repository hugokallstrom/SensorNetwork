import java.util.*;

/** 
 * Routing table uses by node and messages to remember information about
 * different events.
 * @author Viktor Lindblad
 */
public class RoutingTable {

	private HashMap<Integer, Event> eventTable;

	/**
	 * Creates a new routingTable and initiates a empty hash table.
	 */
	public RoutingTable() {
		eventTable = new	HashMap<Integer, Event>();
	}
	
	/**
	 * Sync two different routing tables to share information about events.
     * @param nodeRoutingTable
     */
	public void syncEvents(RoutingTable nodeRoutingTable, Node currentNode) {
        HashMap<Integer, Event> nodeEventTable = nodeRoutingTable.getEventTable();

		for(int agentKey : eventTable.keySet()) {
            if(!nodeEventTable.containsKey(agentKey)) {
                Event copyAgentEvent = new Event(eventTable.get(agentKey));
                eventTable.get(agentKey).setPosition(currentNode.getMyPosition());
                nodeRoutingTable.getEventTable().put(agentKey, copyAgentEvent);
            }
        }

        for(int nodeKey : nodeEventTable.keySet()) {
            if(!eventTable.containsKey(nodeKey)) {
                Event copyNodeEvent = new Event(nodeEventTable.get(nodeKey));
                copyNodeEvent.setPosition(currentNode.getMyPosition());
                eventTable.put(nodeKey, copyNodeEvent);
            }
        }
	}
	
	/**
	 * See which routing table that has the shortest path to an event 
	 * and saves it over the longer path.
     * @param nodeRoutingTable
     */
    public void findShortestPath(RoutingTable nodeRoutingTable, Node currentNode) {
        for(int agentKey : eventTable.keySet()) {
            Event agentEvent = eventTable.get(agentKey);
            Event nodeEvent = nodeRoutingTable.getEventTable().get(agentKey);
            if(nodeRoutingTable.getEventTable().containsKey(agentKey)) {

                if(agentEvent.getDistance() < nodeEvent.getDistance()) {
                    Event copyAgentEvent = new Event(agentEvent);
                    eventTable.get(agentKey).setPosition(currentNode.getMyPosition());
                    nodeRoutingTable.getEventTable().put(agentKey, copyAgentEvent);

                } else if(agentEvent.getDistance() > nodeEvent.getDistance()) {
                    Event copyNodeEvent = new Event(nodeEvent);
                    copyNodeEvent.setPosition(currentNode.getMyPosition());
                    eventTable.put(agentKey, copyNodeEvent);
                }
            }
        }
    }

    /**
     * Increment the distance to an event as a messages moves.
     */
    public void incrementEventDistances() {
        for(int eventKey : eventTable.keySet()) {
        	Event agentEvent = eventTable.get(eventKey);
        	agentEvent.incrementDistance();
        }
    }

    /**
     * Adds an event to the routing table which saves it into the hash table.
     * @param event
     */
    public void addEvent(Event event) {
        eventTable.put(event.getEventId(), event);
    }

    /**
     * Looking up the event from the given key.
     * @param requestedEventId
     * @return Event.
     */
    public Event getEvent(int requestedEventId) {
    	return eventTable.get(requestedEventId);
    }

    /**
     * Returns the objects hash table. The different objects that uses 
     * the hash table is node and messages.
     * @return Hash table
     */
    public HashMap<Integer,Event> getEventTable(){
        return eventTable;
    }

    /**
     * Sets the objects hash table to the given hash table.
     * @param hashTable
     */
    public void setEventTable(HashMap<Integer, Event> hashTable) {
        this.eventTable = hashTable;
    }

    @Override
    public String toString() {
        String out = "";
        for(int eventKey : eventTable.keySet()) {
        	Event event = eventTable.get(eventKey);
        	out += event.toString();
        }
        return out;
    }
}
