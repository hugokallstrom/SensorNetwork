/**
 * Created by hugo on 5/11/15.
 */
public class Event {

	private int eventId;
	private int distance;
	private Position position;
	private int timeOfEvent;
	
	
	public Event(int eventId, int timeOfEvent, Position position){
		this.eventId = eventId;
		this.timeOfEvent = timeOfEvent;
		this.position = position;
		distance = 0;
	}
	
	public int getEventId(){
		return eventId;
	}
	
	public int getDistance(){
		return distance;
	}
	
	public void setDistance(int distance){
		this.distance = distance;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public void setPosition(Position position){
		this.position = position;
	}
	
	public int getTimeOfEvent(){
		return timeOfEvent;
	}
}
