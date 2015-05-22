import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for class QueryTimer.
 *
 * File:        QueryTimerTest.java
 * @author      Charlotte Ristiniemi
 * Contact:     charlotte.ristiniemi@hotmail.com
 * Date:        2015-05-22
 */
public class QueryTimerTest {

    QueryTimer qT;

    /**
     * Creates a new QueryTimer to use in tests. Set with event ID 10.
     */
    @Before
    public void init(){
        qT = new QueryTimer(10);
    }

    /**
     * Test to see that something was created with QueryTimer's constructor.
     */
    @Test
    public void testQueryTimer(){
        assertNotNull(qT);
    }

    /**
     * Test to get event ID. ID was set to 10 in constructor.
     * Check if method getEventId equals 50.
     */
    @Test
    public void testGetEventId(){
        assertEquals(qT.getEventId(),10);
    }

    /**
     * Test to see that method countQuerySteps increases querySteps, with 1,
     * every time it is called. querySteps is set to 1 in constructor,
     * increased with one in method and then checked.
     */
    @Test
    public void testCountQuerySteps(){
        qT.countQuerySteps();
        assertEquals(qT.getQuerySteps(), 2);
    }

    /**
     * checkQuerySteps returns false if node shouldn't resend the Query.
     * If Query was send one time and fulfilled its time to live,
     * checkQuerySteps should return true.
     */
    @Test
    public void testCheckQuerySteps(){
        assertFalse(qT.checkQuerySteps());
        for (int i=0;i<370;i++){
            qT.countQuerySteps();
        }
        assertTrue(qT.checkQuerySteps());
    }
}