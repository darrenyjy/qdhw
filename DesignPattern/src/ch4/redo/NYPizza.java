package ch4.redo;

public class NYPizza extends Pizza
{
	PizzaIngredientFactory ingredientFactory;

	public NYPizza(PizzaIngredientFactory ingredientFactory)
	{
		this.ingredientFactory = ingredientFactory;
		setName("NYPizza");
	}

	@Override
	void prepare()
	{
		System.out.println("Preparing " + name);
		dough = ingredientFactory.createDough();

	}

}
