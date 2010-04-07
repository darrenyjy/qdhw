package hw1.salary.printer;

import hw1.Person;

import java.util.List;

public class SalaryPrinterImpl implements SalaryPrinter
{
	String format = "%1$-24s %2$-16s %3$16s \n";

	@Override
	public void printSalary(List<Person> persons)
	{
		// System.out.println("Name\t\t\tPosition\t\tSalary");

		System.out.printf(format, "Name", "Position", "Salary");
		for (int i = 0; i < persons.size(); i++)
		{
			Person person = persons.get(i);
			System.out.printf(format, person.getName(), person
					.getPositionName(), person.getSalary());
		}

	}

}
