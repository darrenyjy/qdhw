package hw1.sorter;

import hw1.employee.Employee;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SalarySorter implements Sorter
{
	class ComparatorEmployee implements Comparator
	{

		@Override
		public int compare(Object arg0, Object arg1)
		{
			Employee e1 = (Employee) arg0;
			Employee e2 = (Employee) arg1;
			int flag = 0;

			long s1 = e1.getSalary();
			long s2 = e2.getSalary();

			if (s1 > s2)
				flag = 1;
			if (s1 == s2)
				flag = 0;
			if (s1 < s2)
				flag = -1;

			if (flag == 0)
				return e1.getName().compareToIgnoreCase(e2.getName());
			else
				return flag;
			// return .compareTo(e2.getSalary());
		}

	}

	@Override
	public List<Employee> sort(List<Employee> array)
	{
		Comparator<Employee> comparator = new ComparatorEmployee();
		Collections.sort(array, comparator);
		return array;
	}
}
