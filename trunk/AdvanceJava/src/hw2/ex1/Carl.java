package hw2.ex1;

public class Carl extends Car
{
	String manufacturer;

	public Carl(String color, String model, String manufacturer)
	{
		super(color, model);
		this.manufacturer = manufacturer;
	}

	public Carl(String colorString)
	{
		super();
		this.color = colorString;
		this.manufacturer = "上海大众";
	}

	public Carl()
	{
		this("红色");
	}

	public String getManufacturer()
	{
		return manufacturer;
	}

	public String getInfo()
	{
		return this.manufacturer + " " + this.color + " " + this.model;
	}

	public static void main(String args[])
	{
		Carl c1 = new Carl();
		Carl c2 = new Carl("黑色");
		Carl c3 = new Carl("白色", "小轿车", "QQ");

		System.out.println(c1.getInfo());
		System.out.println(c2.getInfo());
		System.out.println(c3.getInfo());

	}
}
