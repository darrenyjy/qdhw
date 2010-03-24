package ch2.redo;

public class ObserverTester
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		PhoneStation ps = new PhoneStation("110");

		PhoneFinder p1 = new PhoneFinder("Peter");
		PhoneFinder p2 = new PhoneFinder("John");

		ps.registerObserver(p2);
		ps.registerObserver(p1);

		p1.showNumber();
		p2.showNumber();

		ps.changeNumber("120");
		p1.showNumber();
		p2.showNumber();

		ps.removeObserver(p2);
		ps.changeNumber("168");
		p1.showNumber();
		p2.showNumber();

	}

}
