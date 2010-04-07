package hw1.salary.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ManagerLevelTwoSalaryCalculatorTest
{
	SalaryCalculator sc;

	@Before
	public void setUp() throws Exception
	{
		sc = new ManagerLevelTwoSalaryCalculator();
	}

	@Test
	public void testCalculateSalary()
	{
		assertEquals(25000, sc.calculateSalary(10000, 10));
		assertEquals(25000, sc.calculateSalary(10000, 20));
		assertEquals(20000, sc.calculateSalary(5000, 0));
	}

	@Test
	public void testCalculateSalaryWithMerit()
	{
		assertTrue(sc instanceof ManagerLevelTwoSalaryCalculator);
		ManagerLevelTwoSalaryCalculator salaryCalculator = (ManagerLevelTwoSalaryCalculator) sc;
		salaryCalculator.setMeritSalary(10000);
		assertEquals(35000, sc.calculateSalary(10000, 0));
	}

}
