/**
 * 
 */
package ch2.redo;

/**
 * @author Coddy Created at 2010-1-20 上午09:46:11
 */
public class PhoneFinder implements Observer
{
	String number;
	String name;

	/**
	 * 
	 */
	public PhoneFinder(String name)
	{
		this.name = name;
		number = "";
	}

	@Override
	public void update(String number)
	{
		System.out.println(name + " got updated phone number");
		this.number = number;

	}

	public void showNumber()
	{
		System.out.println("current number is " + number);
	}

}
