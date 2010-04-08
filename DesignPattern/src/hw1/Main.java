package hw1;

import hw1.employee.Employee;
import hw1.employee.EmployeeLoader;
import hw1.salary.printer.SalaryPrinter;
import hw1.salary.printer.SalaryPrinterImpl;

import java.util.List;

public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		List<Employee> list = EmployeeLoader.getInstance().loadEmployee();

		SalaryPrinter printer = new SalaryPrinterImpl();

		printer.printSalary(list);
	}

}
