package hw1.salary.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EmployeeSalaryCalculatorTest
{
	SalaryCalculator sCalculator;

	@Before
	public void setUp() throws Exception
	{
		sCalculator = new EmployeeSalaryCalculator();
	}

	@Test
	public void testCalculateSalary()
	{
		assertEquals(9000, sCalculator.calculateSalary(9000, 0));
		assertEquals(sCalculator.calculateSalary(9000, 10), 15000);
	}

}
