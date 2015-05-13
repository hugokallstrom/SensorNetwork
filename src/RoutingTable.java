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
        for(Event event : events) {
            if(!events.contains(event)) {
                events.add(event);
            }
        }

		for(Event event : eventList) {
			if(!eventList.contains(event)) {
				eventList.add(event);
			}
		}
        findShortestPath(eventList);
	}

    private void findShortestPath(ArrayList<Event> nodeEvents) {
        for(Event agentEvent : events) {
            for (Event nodeEvent : nodeEvents) {
                if(agentEvent.equals(nodeEvent) && agentEvent.getDistance() < nodeEvent.getDistance()) {
                    nodeEvents.remove(nodeEvent);
                    nodeEvents.add(agentEvent);
                }
            }
        }
    }

    public void addEvent(Event event) {
		events.add(event);
	}

	public Event getEvent(Event event) {
		if(events.contains(event)) {
			return event;
		}
        return null;
	}

	public ArrayList<Event> getEventList(){
		return events;
	}
}
