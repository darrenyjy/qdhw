package ch2;

public class StaticsDisplay implements Observer, DisplayElement
{
	private Subject weatherData;
	private float min;
	private float max;
	private float temp;

	public StaticsDisplay(Subject weatherData)
	{
		super();
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
		min = max = 0;
	}

	public void update(float temp, float humidity, float pressure)
	{
		// TODO Auto-generated method stub
		this.temp = temp;
		if (temp > max)
			max = temp;
		if (temp < min)
			min = temp;
		display();
	}

	public void display()
	{
		// TODO Auto-generated method stub
		System.out.println("Statics Display - Current temp: " + temp + " Max: " + max + " Min: "
				+ min);
	}

}
