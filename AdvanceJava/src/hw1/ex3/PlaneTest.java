package hw1.ex3;

import junit.framework.TestCase;

public class PlaneTest extends TestCase
{
	Common common;

	protected void setUp() throws Exception
	{
		super.setUp();
		common = new Plane();
	}

	public void testComputeTime()
	{
		common.setA(3);
		common.setB(4);
		common.setC(3);
		assertEquals(common.computeTime(3, 4, 3), 10);
	}
}
