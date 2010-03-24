/**
 * 
 */
package ch4.redo;

/**
 * @author Coddy Created at 2010-1-19 下午03:11:13
 */
public class CAPizzaStore implements PizzaStore
{
	PizzaIngredientFactory ingredientFactory = new CAPizzaIngredientFactory();
	Pizza pizza = null;

	/**
	 * 
	 */
	public CAPizzaStore()
	{
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch4.redo.PizzaStore#producePizza()
	 */
	@Override
	public Pizza producePizza(String name)
	{
		if (name.equals("cheese"))
			pizza = new CACheesePizza(this.ingredientFactory);
		else
			pizza = new CAPizza(this.ingredientFactory);

		pizza.prepare();
		return pizza;
	}

}
