package hw1.sorter;

import hw1.employee.Employee;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NameSorter implements Sorter
{

	@Override
	public List<Employee> sort(List<Employee> array)
	{
		Comparator<Employee> comparator = new ComparatorEmployee();
		Collections.sort(array, comparator);
		return array;
	}

	class ComparatorEmployee implements Comparator
	{

		@Override
		public int compare(Object arg0, Object arg1)
		{
			Employee e1 = (Employee) arg0;
			Employee e2 = (Employee) arg1;

			return e1.getName().compareToIgnoreCase(e2.getName());
		}

	}
}
