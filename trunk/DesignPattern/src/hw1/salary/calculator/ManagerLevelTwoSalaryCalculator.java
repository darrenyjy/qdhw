/**
 * 
 */
package hw1.salary.calculator;


/**
 * @author coddy
 * 
 */
public class ManagerLevelTwoSalaryCalculator implements SalaryCalculator
{
	private static long OVERWORK_SALARY = 15000;

	private long meritSalary = 0;

	/**
	 * 
	 */
	public ManagerLevelTwoSalaryCalculator()
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
		// TODO Auto-generated method stub
		return baseSalary + OVERWORK_SALARY + meritSalary;
	}

}
