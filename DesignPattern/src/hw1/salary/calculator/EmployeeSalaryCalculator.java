/**
 * 
 */
package hw1.salary.calculator;


/**
 * @author coddy
 * 
 */
public class EmployeeSalaryCalculator implements SalaryCalculator
{

	/**
	 * 
	 */
	public EmployeeSalaryCalculator()
	{
	}

	@Override
	public long calculateSalary(long baseSalary, int overworkTime)
	{
		long dailySalary = calculateOverworkDailySalary(baseSalary);
		return baseSalary + overworkTime * dailySalary;
	}

	private long calculateOverworkDailySalary(long baseSalary)
	{
		return baseSalary / 30 * 2;
	}

}
