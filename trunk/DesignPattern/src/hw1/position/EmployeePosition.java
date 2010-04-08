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
public class EmployeePosition implements Posotion
{
	private static final String POSITION_NAME = "Employee";
	private static long DEFAULT_BASE_SALARY = 10000;
	private long baseSalary;
	private int overworkTime;
	private SalaryCalculator salaryCalculator;
	private String positionName;

	/**
	 * 
	 */
	public EmployeePosition()
	{
		this(DEFAULT_BASE_SALARY);
	}

	public EmployeePosition(long baseSalary)
	{
		super();
		this.baseSalary = baseSalary;
		this.overworkTime = 0;
		this.salaryCalculator = SalaryCalculatorFactory.getInstance()
				.createEmployeeSalaryCalculator();
		this.positionName = POSITION_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hw1.Posotion#calculateSalary()
	 */
	@Override
	public long calculateSalary()
	{
		return salaryCalculator.calculateSalary(getBaseSalary(),
				getOverworkTime());
	}

	@Override
	public long getBaseSalary()
	{
		return baseSalary;
	}

	@Override
	public int getLevel()
	{
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hw1.Posotion#getOverWorkDay()
	 */
	@Override
	public int getOverworkTime()
	{
		return overworkTime;
	}

	@Override
	public void setLevel(int level)
	{
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
