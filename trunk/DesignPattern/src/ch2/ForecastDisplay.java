package ch2;

public class ForecastDisplay implements DisplayElement, Observer
{
	Subject weatherData;
	
	public ForecastDisplay(Subject weatherData)
	{
		super();
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	public void display()
	{
		// TODO Auto-generated method stub
		System.out.println("Forecast Displayer - No Forecast Data");

	}

	public void update(float temp, float humidity, float pressure)
	{
		// TODO Auto-generated method stub
		display();

	}

}
