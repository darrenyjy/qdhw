package hw1.ex3;

import junit.framework.TestCase;

public class Car007Test extends TestCase
{
	Common common;

	protected void setUp() throws Exception
	{
		super.setUp();
		common = new Car007();
	}

	public void testComputeTime()
	{
		common.setA(3);
		common.setB(10);
		common.setC(5);
		assertEquals(common.computeTime(3, 10, 5), 6);
	}

}
