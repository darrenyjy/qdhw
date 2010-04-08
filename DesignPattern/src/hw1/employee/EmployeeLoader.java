package hw1.employee;

import hw1.position.Position;
import hw1.position.PositionFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class EmployeeLoader
{
	private static final String DEFAULT_FILE_NAME = "Employees.txt";
	private static EmployeeLoader instance;
	private static String file;

	private EmployeeLoader()
	{
		this.file = DEFAULT_FILE_NAME;
	}

	public static EmployeeLoader getInstance()
	{
		if (instance == null)
		{
			instance = new EmployeeLoader();
		}
		return instance;
	}

	// public static EmployeeLoader getInstance(String fileName)
	// {
	// instance = getInstance();
	// file = fileName;
	// return instance;
	// }

	public List<Employee> loadEmployee()
	{
		return loadEmployee(file);
	}

	public List<Employee> loadEmployee(String filename)
	{
		List<Employee> list = new ArrayList<Employee>();
		PositionFactory factory = PositionFactory.getInstance();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(filename));

			String line;
			while ((line = reader.readLine()) != null)
			{
				StringTokenizer tokenizer = new StringTokenizer(line);

				while (tokenizer.hasMoreTokens())
				{
					String name = tokenizer.nextToken();
					String positionName = tokenizer.nextToken();
					Position position = null;
					if (positionName.equalsIgnoreCase("M"))
					{
						position = factory.createManagerPosition();
					} else
					{
						position = factory.createEmployeePosition();
					}

					Employee employee = new Employee(name, position);
					list.add(employee);
				}

			}
		} catch (FileNotFoundException e)
		{
			System.err.println("File Not Found!");
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return list;
	}
}
