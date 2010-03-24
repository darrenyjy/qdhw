package ch4.redo;

public class NYCheesePizza extends Pizza
{

	private PizzaIngredientFactory ingredientFactory;

	public NYCheesePizza(PizzaIngredientFactory ingredientFactory)
	{
		this.ingredientFactory = ingredientFactory;
		setName("NYPizza");
	}

	@Override
	void prepare()
	{
		System.out.println("Preparing " + name);
		dough = ingredientFactory.createDough();
		sauce = ingredientFactory.createSauce();
		cheese = ingredientFactory.createCheese();

	}

}
