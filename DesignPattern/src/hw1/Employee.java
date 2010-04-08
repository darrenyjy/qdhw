/**
 * 
 */
package hw1;

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

	void setPosition(Posotion position)
	{
		this.position = position;
	}

	public String getPositionName()
	{
		return position.getPositioName();
	}

}
