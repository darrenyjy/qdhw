package hw1.salary.calculator;

public class SalaryCalculatorFactory
{
	private static SalaryCalculatorFactory instance;

	private SalaryCalculatorFactory()
	{

	}

	static public SalaryCalculatorFactory getInstance()
	{
		if (null == instance)
		{
			instance = new SalaryCalculatorFactory();
		}
		return instance;
	}

	public SalaryCalculator createSalaryCalculator(int level)
	{
		SalaryCalculator salaryCalculator = null;
		switch (level)
		{
		case 0:
			salaryCalculator = new EmployeeSalaryCalculator();
			break;
		case 1:
			salaryCalculator = new ManagerLevelOneSalaryCalculator();
			break;
		case 2:
			salaryCalculator = new ManagerLevelTwoSalaryCalculator();
		default:
			break;
		}
		return salaryCalculator;
	}

	public SalaryCalculator createEmployeeSalaryCalculator()
	{
		return new EmployeeSalaryCalculator();
	}

	public SalaryCalculator ManagerLevelOneSalaryCalculator()
	{
		return new ManagerLevelOneSalaryCalculator();
	}

	public SalaryCalculator ManagerLevelTwoSalaryCalculator()
	{
		return new ManagerLevelTwoSalaryCalculator();
	}
}
