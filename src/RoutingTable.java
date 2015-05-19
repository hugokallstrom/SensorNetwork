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

        //return union(nodeEventList, events);
	}

    public <T> ArrayList<T> union(List<T> nodeEventList, List<T> agentEventList) {
        Set<T> set = new HashSet<T>();
        set.addAll(nodeEventList);
        set.addAll(agentEventList);
        return new ArrayList<T>(set);
    }

    public ArrayList<Event> findShortestPath(RoutingTable nodeRoutingTable) {
    	
        ArrayList<Event> nodeEvents = nodeRoutingTable.getEventList();
        ArrayList<Event> shortestList = new ArrayList<Event>();
        
        System.out.println("----*****----");
        System.out.println("Leanght of agentlist: " + events.size());
        System.out.println("Leangth of nodelist: " +nodeEvents.size());
        System.out.println("Shortest : " + shortestList.size());
        
        for(Event agentEvent : events) {
            for(Event nodeEvent : nodeEvents) {
                if(agentEvent.getEventId() == nodeEvent.getEventId() && agentEvent.getDistance() < nodeEvent.getDistance()) {

                    shortestList.add(agentEvent);
                }
                else if(nodeEvent.getEventId() == agentEvent.getEventId() && agentEvent.getDistance() > nodeEvent.getDistance()) {
                	
                    shortestList.add(nodeEvent);
                }
                /**
                 * om de har samma distance.
                 */
                else if(nodeEvent.getEventId() == agentEvent.getEventId()) {
                	
                	shortestList.add(agentEvent);
                }
            }
        }
        System.out.println("After loop: "+shortestList.size());
        
            /**
             * det verkar som att man inte får öka på objektet man itererear
             * igenom
             */
        for(Event agent : events) {
            for(Event shortest : shortestList) {
             	System.out.println("shortest id " + shortest.getEventId() );
                System.out.println("agent id "+agent.getEventId());
            	if(agent.getEventId() != shortest.getEventId()) {
               
                    shortestList.add(agent);
                }
            }
        }
        System.out.println("After loop2: "+shortestList.size());

        for(Event agentEve : nodeEvents) {
            for(Event shortest2 : shortestList) {
            	System.out.println("shortest id " + shortest2.getEventId() );
                System.out.println("agent id "+agentEve.getEventId());
            	if(agentEve.getEventId() != shortest2.getEventId()) {
                	
                    shortestList.add(agentEve);
                }
            }
        }
        System.out.println("After loop3: "+shortestList.size());

        return shortestList ;
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

    public ArrayList<Event> copyEventList(ArrayList<Event> eventList) {
        ArrayList<Event> copiedEventList = new ArrayList<Event>();
        for (int i = eventList.size() - 1; i >= 0; --i) {
            Event event = eventList.get(i);
            if (event != null) {
                copiedEventList.add(new Event(event));
            }
        }
        return copiedEventList;
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
     */
}
