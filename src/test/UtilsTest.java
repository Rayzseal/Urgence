package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import utils.Utils;
/**
 * Test on method from the class Utils
 *
 */
public class UtilsTest {

	/**
	 * Test on number of 0
	 */
	@Test
	public void testPrintTime() {
		int time = 360;
		assertEquals("00:06:00", Utils.timeIntToString(time));
	}
	
	/**
	 * Test on a random time
	 */
	@Test
	public void testPrintTime2() {
		int time = 33366;
		assertEquals("09:16:06", Utils.timeIntToString(time));
	}
	
	/**
	 * Test on the time with more than a day
	 */
	@Test
	public void testPrintTime3() {
		int time = 86400;
		System.out.println(Utils.timeIntToString(time));
		assertEquals("1 day(s) 00:00:00", Utils.timeIntToString(time));
	}
	
	/**
	 * Test on the time using max int value
	 */
	@Test
	public void testPrintTime4() {
		int time = 2147483647;
		System.out.println(Utils.timeIntToString(time));
		assertEquals("24855 day(s) 03:14:07", Utils.timeIntToString(time));
	}
	
}
