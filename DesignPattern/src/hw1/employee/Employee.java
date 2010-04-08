/**
 * 
 */
package hw1.employee;

import hw1.position.Posotion;

/**
 * @author coddy
 * 
 */
public class Employee
{

	/**
	 * 
	 */
	private String name = null;

	private Posotion position = null;

	/**
	 * 
	 */
	public Employee(String name, Posotion posotion)
	{
		this.name = name;
		this.position = posotion;
	}

	public String getName()
	{
		return name;
	}

	Posotion getPosition()
	{
		return position;
	}

	public long getSalary()
	{
		return position.calculateSalary();
	}

	public void setPosition(Posotion position)
	{
		this.position = position;
	}

	public String getPositionName()
	{
		return position.getPositioName();
	}

}
