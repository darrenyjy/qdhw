package ch2.redo.java;

public class ObserverTester
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		NewsStation s = new NewsStation("News");

		NewsSubscriber ns1 = new NewsSubscriber();
		NewsSubscriber ns2 = new NewsSubscriber();

		s.addObserver(ns1);
		s.addObserver(ns2);

		s.updateNews("Haiti Earthquake");

		s.deleteObserver(ns2);

		s.updateNews("Global Warming");

	}

}
