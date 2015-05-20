/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

public class RoutingTable {

	//private ArrayList<Event> events;
	private Hashtable<Integer, Event> hashTable;
	
	public RoutingTable() {
		//events = new ArrayList<Event>();
		hashTable = new	Hashtable<Integer, Event>();
	}
	
	public void syncEvents(RoutingTable nodeRoutingTable){
		
		if(nodeRoutingTable.hashTable.isEmpty()){
			nodeRoutingTable.hashTable = new Hashtable<Integer, Event> (hashTable );
		}
		
		for (int agentKey : hashTable.keySet()){
			for (int nodeKey : nodeRoutingTable.hashTable.keySet()){
				Event agentEvent = hashTable.get(agentKey);
        		Event nodeEvent = nodeRoutingTable.hashTable.get(nodeKey);
				
				if(!hashTable.containsKey(nodeKey)){
					hashTable.put(nodeKey, nodeEvent);
				}
				else if(!nodeRoutingTable.hashTable.containsKey(agentKey)){
					nodeRoutingTable.hashTable.put(agentKey, agentEvent);
				}
			}
			
		}
	}
	
    public void findShortestPath(RoutingTable nodeRoutingTable) {
      
        for(int agentKey : hashTable.keySet()){
        	for(int nodeKey : nodeRoutingTable.hashTable.keySet()){
        		
        		Event agentEvent = hashTable.get(agentKey);
        		Event nodeEvent = nodeRoutingTable.hashTable.get(nodeKey);
        	
        		if(nodeEvent.getEventId()==agentEvent.getEventId() && agentEvent.getDistance() < nodeEvent.getDistance()){
        			Event copyAgentEvent = new Event(agentEvent);
        			nodeRoutingTable.hashTable.put(nodeKey, copyAgentEvent);
        		}
        		else if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() > nodeEvent.getDistance()){
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
    /*
    public void setEventList(ArrayList<Event> eventList){
        this.events = eventList;
    }*/

	public Hashtable<Integer, Event> deepCopy(Hashtable<Integer, Event> original) {
	    
		Hashtable<Integer, Event> copy = new Hashtable<Integer, Event>(original.size());
	    
	    for(int originalKey : original.keySet()) {
	    	
	    	Event copyEvent = new Event(original.get(originalKey));
	    	copy.put(originalKey, copyEvent);
	    }
	
	    return copy;
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
