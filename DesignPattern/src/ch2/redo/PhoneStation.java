/**
 * 
 */
package ch2.redo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Coddy Created at 2010-1-20 上午09:45:37
 */
public class PhoneStation implements Subject
{
	private List<Observer> list;
	private String number;

	/**
	 * 
	 */
	public PhoneStation(String number)
	{
		// TODO Auto-generated constructor stub
		this.number = number;
		list = new ArrayList<Observer>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch2.redo.Subject#notifyObservers()
	 */
	@Override
	public void notifyObservers()
	{
		if (list.size() > 0)
		{
			for (Observer o : list)
			{
				o.update(number);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch2.redo.Subject#registerObserver(ch2.redo.Observer)
	 */
	@Override
	public void registerObserver(Observer o)
	{
		list.add(o);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch2.redo.Subject#removeObserver(ch2.redo.Observer)
	 */
	@Override
	public void removeObserver(Observer o)
	{
		list.remove(o);
	}

	@Override
	public void changeNumber(String number)
	{
		if (!this.number.equals(number))
		{
			this.number = number;
			this.notifyObservers();
		}

	}

}
