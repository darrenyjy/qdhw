package ch2;

public class WeatherStation
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		WeatherData wd = new WeatherData();
		wd.setHumidity(40);
		wd.setTemp(23);
		wd.setPressure(10);
		
		CurrentConditionDisplay ccd = new CurrentConditionDisplay(wd);
		ForecastDisplay fd = new ForecastDisplay(wd);
		StaticsDisplay sd = new StaticsDisplay(wd); 
		
		ccd.display();
		fd.display();
		sd.display();
		
		System.out.println();
		System.out.println("--------------Weather changed-----------");
		wd.setTemp(14);
		wd.measurementsChanged();
		
		
		System.out.println();
		System.out.println("--------------Observer Changed-----------");
		wd.registerObserver(ccd);
		wd.removeObserver(fd);
		
		System.out.println();
		System.out.println("--------------Weather changed-----------");
		wd.setTemp(23);
		wd.measurementsChanged();
		
	}

}
