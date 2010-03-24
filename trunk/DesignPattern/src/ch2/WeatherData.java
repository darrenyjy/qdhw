package ch2;

import java.util.ArrayList;
import java.util.Iterator;

public class WeatherData implements Subject
{
	private ArrayList<Observer> observers;
	private float temp;
	private float humidity;
	private float pressure;

	public WeatherData()
	{
		observers = new ArrayList<Observer>();
		temp = 0;
		humidity = 0;
		pressure = 0;
	}

	public void notifyObservers()
	{
		// TODO Auto-generated method stub
		Iterator<Observer> it = observers.iterator();
		while (it.hasNext())
		{
			Observer o = it.next();
			o.update(temp, humidity, pressure);
		}
	}

	public void registerObserver(Observer o)
	{
		if (!observers.contains(o))
			observers.add(o);
	}

	public void removeObserver(Observer o)
	{
		// TODO Auto-generated method stub
		if (observers.contains(o))
			observers.remove(o);

	}

	public void setObservers(ArrayList<Observer> observers)
	{
		this.observers = observers;
	}

	public void setTemp(float temp)
	{
		this.temp = temp;
	}

	public void setHumidity(float humidity)
	{
		this.humidity = humidity;
	}

	public void setPressure(float pressure)
	{
		this.pressure = pressure;
	}
	
	public void measurementsChanged()
	{
		notifyObservers();
	}

}
