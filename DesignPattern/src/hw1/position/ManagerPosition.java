/**
 * 
 */
package hw1.position;

import hw1.salary.calculator.SalaryCalculator;
import hw1.salary.calculator.SalaryCalculatorFactory;

/**
 * @author coddy
 * 
 */
public class ManagerPosition implements Position
{
	private static long DEFAULT_BASE_SALARY = 10000;
	private static String POSITION_NAME = "Manager";
	private static int DEFAULT_LEVEL = 1;
	private long baseSalary;
	private int overworkTime;
	private SalaryCalculator salaryCalculator;
	private int level;
	private String positionName;

	/**
	 * 
	 */
	public ManagerPosition()
	{
		// TODO Auto-generated constructor stub
		this(DEFAULT_BASE_SALARY, DEFAULT_LEVEL);

	}

	public ManagerPosition(long baseSalary, int level)
	{
		this.baseSalary = baseSalary;
		this.level = level;
		this.salaryCalculator = SalaryCalculatorFactory.getInstance()
				.createSalaryCalculator(level);
		this.overworkTime = 0;
		this.positionName = POSITION_NAME;

	}

	@Override
	public long calculateSalary()
	{
		return salaryCalculator.calculateSalary(getBaseSalary(),
				getOverworkTime());
	}

	@Override
	public int getOverworkTime()
	{
		return overworkTime;
	}

	@Override
	public long getBaseSalary()
	{
		return baseSalary;
	}

	@Override
	public int getLevel()
	{
		return level;
	}

	@Override
	public void setLevel(int level)
	{
		this.level = level;
	}

	@Override
	public void setOverWorkTime(int overworkTime)
	{

		this.overworkTime = overworkTime;

	}

	@Override
	public void setSalaryCalculator(SalaryCalculator salaryCalculator)
	{
		this.salaryCalculator = salaryCalculator;
	}

	@Override
	public String getPositioName()
	{
		return this.positionName;
	}

}
