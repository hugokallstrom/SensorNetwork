/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

public class RoutingTable {

	private ArrayList<Event> events;
	
	public RoutingTable() {
		events = new ArrayList<Event>();
	}

	public ArrayList<Event> syncEvents(RoutingTable routingTable) {
		ArrayList<Event> nodeEventList = routingTable.getEventList();
        ArrayList<Event> syncedList = union(nodeEventList, events);
        events = (ArrayList<Event>) syncedList.clone();
        return syncedList;

	}

    public <T> ArrayList<T> union(List<T> nodeEventList, List<T> agentEventList) {
        Set<T> set = new HashSet<T>();
        set.addAll(nodeEventList);
        set.addAll(agentEventList);
        return new ArrayList<T>(set);
    }

    public void findShortestPath(RoutingTable nodeRoutingTable) {
        ArrayList<Event> nodeEvents = nodeRoutingTable.getEventList();
        for(Event agentEvent : events){
            for(Event nodeEvent : nodeEvents) {

                if(agentEvent.getEventId() == nodeEvent.getEventId() && agentEvent.getDistance() < nodeEvent.getDistance()) {
                    nodeEvents.remove(nodeEvent);
                    Event agentEventCopy = new Event(agentEvent);
                    nodeEvents.add(agentEventCopy);
                }

                if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() > nodeEvent.getDistance()) {
                	events.remove(agentEvent);
                    Event nodeEventCopy = new Event(nodeEvent);
                	events.add(nodeEventCopy);
                }
            }
        }
    }

    public void changeEventPosition(Node previousNode, RoutingTable nodeRoutingTable) {
        ArrayList<Event> nodeEventList = nodeRoutingTable.getEventList();
        Position previousNodePosition = previousNode.getMyPosition();
        for(Event agentEvent : events) {
            for(Event nodeEvent : nodeEventList) {
                if(agentEvent.getEventId() == nodeEvent.getEventId() && (nodeEvent.getDistance() != 0 && agentEvent.getDistance() != 0)) {
                    agentEvent.setPosition(previousNodePosition);
                    nodeEvent.setPosition(previousNodePosition);
                }
            }
        }
    }

    public void incrementEventDistances() {
        for(Event event : events) {
            event.incrementDistance();
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

    public void setEventList(ArrayList<Event> eventList){
        this.events = eventList;
    }

    @Override
    public String toString() {
        String out = "";
        for(Event event : events) {
            out += event.toString();
        }
        return out;
    }

    /*
     for(Event event : eventList) {
            if(!events.contains(event)) {
                Event eventCopy = new Event(event);
                events.add(eventCopy);
            }
        }


		for(Event event : events) {
            if (!eventList.contains(event)) {
                Event eventCopy = new Event(event);
                eventList.add(eventCopy);
            }
        }
     */
}
