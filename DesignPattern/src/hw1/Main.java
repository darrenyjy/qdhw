package hw1;

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
		Person p1 = new Person("Coddy", new ManagerPosition());
		Person p2 = new Person("Somebody", new EmployeePosition());

		List<Person> list = new ArrayList<Person>();
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
