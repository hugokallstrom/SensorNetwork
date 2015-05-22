
public class QueryTimer {

	private int eventId;
	private int querySteps;
	
	public QueryTimer(int eventId){
		this.eventId = eventId;
		querySteps=1;
	}

    public int getEventId(){
        return eventId;
    }

	public int getQuerySteps(){
		return querySteps;
	}

	public void countQuerySteps(){
		querySteps++;
	}

	public boolean checkQuerySteps(){
		return (querySteps >= 8*Constants.timeToLiveQuery);
	}
}
