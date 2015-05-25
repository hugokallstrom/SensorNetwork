import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by charlotteristiniemi on 15-05-22.
 */
public class QueryTimerTest {

    QueryTimer qT;

    /**
     * Initialises
     */
    @Before
    public void init(){
        qT = new QueryTimer(10);
    }

    @Test
    public void testQueryTimer(){
        assertNotNull(qT);
    }

    @Test
    public void testGetEventId(){
        assertEquals(qT.getEventId(),10);
    }

    @Test
    public void testCountQuerySteps(){
        qT.countQuerySteps();
        assertEquals(qT.getQuerySteps(), 2);
    }

    @Test
    public void testCheckQuerySteps(){
        assertFalse(qT.checkQuerySteps());
        for (int i=0;i<370;i++){
            qT.countQuerySteps();
        }
        assertTrue(qT.checkQuerySteps());
    }
}
