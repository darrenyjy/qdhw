/**
 * 
 */
package hw1.employee;

import hw1.position.Position;

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

	private Position position = null;

	/**
	 * 
	 */
	public Employee(String name, Position posotion)
	{
		this.name = name;
		this.position = posotion;
	}

	public String getName()
	{
		return name;
	}

	Position getPosition()
	{
		return position;
	}

	public long getSalary()
	{
		return position.calculateSalary();
	}

	public void setPosition(Position position)
	{
		this.position = position;
	}

	public String getPositionName()
	{
		return position.getPositioName();
	}

}
