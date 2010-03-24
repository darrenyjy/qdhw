package ch4.factory.store;

import ch4.factory.pizza.Pizza;

public abstract class PizzaStore
{

	
	public void createPizza(String type)
	{
		Pizza pizza;
		pizza = orderPizza(type);
		
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		
	}

	protected abstract Pizza orderPizza(String type);
}
