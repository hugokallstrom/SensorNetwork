/**
 * Keeps track of the time since a sender node sent a query.
 * @author Viktor Lindblad
 */
public class QueryTimer {

	private int eventId;
	private int querySteps;

    /**
     * Creates a new QueryTimer, with same event id as the query has.
     * @param eventId
     */
	public QueryTimer(int eventId){
		this.eventId = eventId;
		querySteps=1;
	}

    /**
     * @return returns the eventId.
     */
    public int getEventId(){
        return eventId;
    }

    /**
     * @return returns the number of steps.
     */
	public int getQuerySteps(){
		return querySteps;
	}

    /**
     * Increments querySteps.
     */
	public void countQuerySteps(){
		querySteps++;
	}

    /**
     * Checks if the time since the query
     * was sent exceeds the time limit.
     * @return true if the time is exceeded.
     */
	public boolean checkQuerySteps(){
		return (querySteps >= 8*Constants.timeToLiveQuery);
	}
}
