package hw1;

import hw1.employee.Employee;
import hw1.position.EmployeePosition;
import hw1.position.ManagerPosition;
import hw1.salary.printer.SalaryPrinter;
import hw1.salary.printer.SalaryPrinterImpl;

import java.util.ArrayList;
import java.util.List;

public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Employee p1 = new Employee("Coddy", new ManagerPosition());
		Employee p2 = new Employee("Somebody", new EmployeePosition());

		List<Employee> list = new ArrayList<Employee>();
		list.add(p1);
		list.add(p2);

		SalaryPrinter printer = new SalaryPrinterImpl();
		printer.printSalary(list);

		p2.setPosition(new ManagerPosition(20000, 2));
		list.remove(1);
		list.add(p2);

		printer.printSalary(list);
	}

}
