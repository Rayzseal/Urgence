package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import utils.Utils;

public class UtilsTest {

	@Test
	public void testPrintTime() {
		int time =360;
		assertEquals("00:06:00", Utils.globalWaitingTime(time));
	}
	@Test
	public void testPrintTime2() {
		int time = 33366;
		assertEquals("09:16:06", Utils.globalWaitingTime(time));
	}
	
	@Test
	public void testPrintTime3() {
		int time = 86400;
		System.out.println(Utils.globalWaitingTime(time));
		assertEquals("1 day(s) 00:00:00", Utils.globalWaitingTime(time));
	}
}
