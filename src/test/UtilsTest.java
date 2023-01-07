package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utils.Utils;
/**
 * Test on method from the class Utils
 *
 */
public class UtilsTest {

	@Test
	/**
	 * Test on number of 0
	 */
	public void testPrintTime() {
		int time = 360;
		assertEquals("00:06:00", Utils.timeIntToString(time));
	}
	@Test
	/**
	 * Test on a random time
	 */
	public void testPrintTime2() {
		int time = 33366;
		assertEquals("09:16:06", Utils.timeIntToString(time));
	}
	
	@Test
	/**
	 * Test on the time with more than a day
	 */
	public void testPrintTime3() {
		int time = 86400;
		System.out.println(Utils.timeIntToString(time));
		assertEquals("1 day(s) 00:00:00", Utils.timeIntToString(time));
	}
}
