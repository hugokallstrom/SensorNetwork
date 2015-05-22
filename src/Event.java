/**
 * Created by hugo on 5/11/15.
 */

/**
 * Class that will represent the event. An event consists an ID, a distance to
 * the root, position working as a direction to the root of the event and 
 * the time showing when the event was created.
 * 
 * @author charlotteristiniemi
 */
public class Event {

	private int eventId;
    private int distance;
    private Position position;
	private int timeOfEvent;

	/**
	 * Constructor that will create an Event.
	 * @param eventId
	 * @param timeOfEvent
	 * @param position
	 */
	public Event(int eventId, int timeOfEvent, Position position){
		this.eventId = eventId;
		this.timeOfEvent = timeOfEvent;
		this.position = position;
		distance = 0;
	}

    public Event(Event newEvent) {
    	
        this.distance = newEvent.getDistance();
        this.eventId = newEvent.getEventId();
        this.position = newEvent.getPosition();
        this.timeOfEvent = newEvent.getTimeOfEvent();
    }

	/**
	 * Returns the event ID
	 * @return eventId
	 */
  	public int getEventId(){
		return eventId;
	}

  	/**
  	 * Returns the distance to the root of the event in steps.
  	 * @return distance
  	 */
	public int getDistance(){
		return distance;
	}

	/**
	 * Method to set distance
	 * @param distance
	 */
	public void setDistance(int distance){
		this.distance = distance;
	}

	/**
	 * Returns position. When an event is created, position will work as the
	 * root of the event. Position will work as a direction to go to if
	 * query/agent is not standing at the root of the event.
	 *
	 * @return position
	 */
	public Position getPosition(){
		return position;
	}

	/**
	 * Method to set a position
	 * @param position
	 */
	public void setPosition(Position position){
		this.position = position;
	}

	/**
	 * Returns the time that the event was created. Time is represented by
	 * time steps.
	 * @return timeOfEvent
	 */
	public int getTimeOfEvent(){
		return timeOfEvent;
	}

	/**
	 * Method to set the time of the event.
	 * @param timeOfEvent
	 */
    public void setTimeOfEvent(int timeOfEvent) {
        this.timeOfEvent = timeOfEvent;
    }

	/**
	 *
	 */
    public void incrementDistance() {
        distance++;
    }
}
