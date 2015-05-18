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
		for(Event event : eventList) {
            if (!events.contains(event)) {
                events.add(event);
            }
        }
        findShortestPath(eventList);
	}

    private void findShortestPath(ArrayList<Event> nodeEvents) {
        for(Event agentEvent : events){
            for (Event nodeEvent : nodeEvents) {
                if(agentEvent.equals(nodeEvent) && agentEvent.getDistance() < nodeEvent.getDistance()) {
                    nodeEvents.remove(nodeEvent);
                    nodeEvents.add(agentEvent);
                }
                else if(nodeEvent.equals(agentEvent) && agentEvent.getDistance() > nodeEvent.getDistance()){
                	events.remove(agentEvent);
                	events.add(nodeEvent);
                }
            }
        }
    }

    public void addEvent(Event event) {
		events.add(event);
	}

	public Event getEvent(int requestedEventId) throws NoSuchElementException {
        for(Event event : events) {
            if(event.getEventId() == requestedEventId) {
                return event;
            }
        }
        throw new NoSuchElementException("Event not found.");
	}

    public ArrayList<Event> getEventList(){
		return events;
	}
}
