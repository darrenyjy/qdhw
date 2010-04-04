package hw2.ex1;

public class Car
{
	String color;
	String model;

	public Car()
	{
		this("红色", "轿车");

	}

	public Car(String color, String model)
	{
		super();
		this.color = color;
		this.model = model;
	}

	public String getColor()
	{
		return color;
	}

	public String getModel()
	{
		return model;
	}

}
