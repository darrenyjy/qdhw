package dp.composite.menu;

import java.util.ArrayList;

public class Menu extends MenuComponent
{
	ArrayList menuComponents = new ArrayList();
	String name;
	String description;

	public Menu(String name, String description)
	{
		super();
		this.name = name;
		this.description = description;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(MenuComponent menuComponent)
	{
		this.menuComponents.add(menuComponent);
	}

	@Override
	public MenuComponent getChild(int i)
	{
		// TODO Auto-generated method stub
		return (MenuComponent) menuComponents.get(i);
	}

	@Override
	public String getDescription()
	{
		return this.description;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public double getPrice()
	{
		// TODO Auto-generated method stub
		return super.getPrice();
	}

	@Override
	public boolean isVegetarian()
	{
		// TODO Auto-generated method stub
		return super.isVegetarian();
	}

	@Override
	public void print()
	{
		// TODO Auto-generated method stub
		super.print();
	}

	@Override
	public void remove(MenuComponent menuComponent)
	{
		// TODO Auto-generated method stub
		super.remove(menuComponent);
	}

}
