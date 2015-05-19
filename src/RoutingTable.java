/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

public class RoutingTable {

	private ArrayList<Event> events;
	
	public RoutingTable() {
		events = new ArrayList<Event>();
	}

	public void syncEvents(ArrayList<Event> eventList) {
		
        for(Event event : eventList) {
            if(!events.contains(event)) {
                events.add(event);
            }
        }
		for(Event event : events) {
            if (!eventList.contains(event)) {
                eventList.add(event);
            }
        }
        findShortestPath(eventList);
	}

    private void findShortestPath(ArrayList<Event> nodeEvents) {
        for(Event agentEvent : events){
            for (Event nodeEvent : nodeEvents) {
                if(agentEvent.getEventId() == (nodeEvent.getEventId()) && agentEvent.getDistance() < nodeEvent.getDistance()) {
                    nodeEvents.remove(nodeEvent);
                    nodeEvents.add(agentEvent);
                } else if(nodeEvent.getEventId() == (agentEvent.getEventId()) && agentEvent.getDistance() > nodeEvent.getDistance()){
                	events.remove(agentEvent);
                	events.add(nodeEvent);
                }
            }
        }
    }

    public void addEvent(Event event) {
		events.add(event);
	}

	public Event getEvent(int requestedEventId) {
        for(Event event : events) {
            if(event.getEventId() == requestedEventId) {
                return event;
            }
        }
        return null;
	}

    public ArrayList<Event> getEventList(){
		return events;
	}

    public void incrementEventDistances() {
        for(Event event : events) {
            event.incrementDistance();
        }
    }

    public void changeEventPosition(Position previousNodePosition, Position currentNodePosition, ArrayList<Event> eventList) {
        for(Event event : events) {
            for(Event nodeEvent : eventList) {
                if(event.equals(nodeEvent) && (nodeEvent.getPosition() != currentNodePosition && event.getDistance() != 0)) {
                    event.setPosition(previousNodePosition);
                    nodeEvent.setPosition(previousNodePosition);
                }
            }
        }
    }

    @Override
    public String toString() {
        String out = "";
        for(Event event : events) {
            out += event.toString();
        }
        return out;
    }
}
