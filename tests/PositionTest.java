import static org.junit.Assert.*;

import org.junit.Test;


/**
 * JUnit test for class Position.
 */
public class PositionTest {

	/**
	 * Test to see that at least something was created with constructor of 
	 * Position.
	 */
	@Test
	public void testPosition() {
		Position p = new Position(3,20);
		assertNotNull(p);
	}
	
	/**
	 * Test for method getX. Creates a new position with x-value 40 and 
	 * y-value 2. Then compares if equal with getX and 40.
	 */
	@Test
	public void testGetX() {
		Position p = new Position(40,2);
		assertEquals(p.getX(), 40);
	}
	
	/**
	 * Test for method getY. Creates a new position with x-value 40 and 
	 * y-value 2. Then compares if equal with getY and 2.
	 */
	@Test
	public void testGetY() {
		Position p = new Position(40,2);
		assertEquals(p.getY(), 2);
	}
}











