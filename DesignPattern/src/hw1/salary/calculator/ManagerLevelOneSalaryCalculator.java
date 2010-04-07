/**
 * 
 */
package hw1.salary.calculator;


/**
 * @author coddy
 * 
 */
public class ManagerLevelOneSalaryCalculator implements SalaryCalculator
{
	private static long OVERWORK_SALARY = 10000;

	private long meritSalary = 0;

	/**
	 * 
	 */
	public ManagerLevelOneSalaryCalculator()
	{
		meritSalary = 0;
	}

	long getMeritSalary()
	{
		return meritSalary;
	}

	void setMeritSalary(long meritSalary)
	{
		this.meritSalary = meritSalary;
	}

	@Override
	public long calculateSalary(long baseSalary, int overworkTime)
	{
		return baseSalary + OVERWORK_SALARY + meritSalary;
	}

}
