package ch2;

public class CurrentConditionDisplay implements Observer, DisplayElement
{
	private Subject weatherData;
	private float temp;
	private float humidity;

	public CurrentConditionDisplay(Subject weatherData)
	{
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	public void update(float temp, float humidity, float pressure)
	{
		// TODO Auto-generated method stub
		this.temp = temp;
		this.humidity = humidity;
		display();
	}

	public void display()
	{
		// TODO Auto-generated method stub
		System.out.println("Current Condition Display - Temp: "+ temp + " Humidity: "+humidity);

	}

}
