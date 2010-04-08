package hw1.position;

import hw1.salary.calculator.SalaryCalculator;

public interface Posotion
{

	/**
	 * 
	 */
	public int getOverworkTime();

	public void setOverWorkTime(int overworkTime);

	public String getPositioName();

	/**
	 * 
	 */
	public long calculateSalary();

	public long getBaseSalary();

	public int getLevel();

	public void setLevel(int level);

	public void setSalaryCalculator(SalaryCalculator salaryCalculator);

}
