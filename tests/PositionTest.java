import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for class Position.
 *
 * @author      Charlotte Ristiniemi
 */


public class PositionTest {
	Position p;

	/**
	 * Initializing a new position to use in tests.
	 */
	@Before
	public void init(){
		p = new Position(40,2); 
	}
	/**
	 * Test to see that at least something was created with constructor of 
	 * Position.
	 */
	@Test
	public void testPosition() {
		assertNotNull(p);
	}
	
	/**
	 * Test for method getX. Creates a new position with x=40 and
	 * y=2. Then compares if equal with getX and 40.
	 */
	@Test
	public void testGetX() {
		assertEquals(p.getX(), 40);
	}
	
	/**
	 * Test for method getY. Creates a new position with x=40 and
	 * y=2. Then compares if equal with getY and 2.
	 */
	@Test
	public void testGetY() {
		assertEquals(p.getY(), 2);
	}
	
	/**
	 * Testing if a given neighbor is indeed neighbors.
	 */
	@Test
	public void testIsNeighbors(){
		Position pos = new Position(40,1);
		assertTrue(p.isNeighbour(pos));
	}
}