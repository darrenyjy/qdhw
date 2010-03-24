package ch2.redo.java;

import java.util.Observable;
import java.util.Observer;

public class NewsSubscriber implements Observer
{

	@Override
	public void update(Observable obs, Object arg1)
	{
		// TODO Auto-generated method stub
		if (obs instanceof NewsStation)
		{
			NewsStation s = (NewsStation) obs;

			System.out.println(s.getNews());
		}

	}

}
