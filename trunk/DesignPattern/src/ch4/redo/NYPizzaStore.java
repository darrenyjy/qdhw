package ch4.redo;

public class NYPizzaStore implements PizzaStore
{
	PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();
	Pizza pizza = null;

	@Override
	public Pizza producePizza(String name)
	{
		if (name.equals("cheese"))
			pizza = new NYCheesePizza(this.ingredientFactory);
		else
			pizza = new NYPizza(this.ingredientFactory);

		pizza.prepare();
		return pizza;
	}

}
