
public class QueryTimer {

	private int eventId;
	private int querySteps;
	
	public QueryTimer(int eventId){
		this.eventId = eventId;
		querySteps=1;
	}
	
	public void countQuerySteps(){
		querySteps++;
	}
	
	public int getEventId(){
		return eventId;
	}
	
	public boolean checkQuerySteps(){
		return (querySteps<Constants.timeToLiveQuery);
	}
}
