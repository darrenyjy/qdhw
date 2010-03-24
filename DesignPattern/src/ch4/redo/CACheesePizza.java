package ch4.redo;

public class CACheesePizza extends Pizza
{
	PizzaIngredientFactory ingredientFactory;

	public CACheesePizza(PizzaIngredientFactory ingredientFactory)
	{
		this.ingredientFactory = ingredientFactory;
		setName("CACheesePizza");// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch4.redo.Pizza#prepare()
	 */
	@Override
	void prepare()
	{
		System.out.println("Preparing " + name);
		dough = ingredientFactory.createDough();
		sauce = ingredientFactory.createSauce();
		cheese = ingredientFactory.createCheese();

	}

}
