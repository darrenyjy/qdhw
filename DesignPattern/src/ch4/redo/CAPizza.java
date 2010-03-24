/**
 * 
 */
package ch4.redo;

/**
 * @author Coddy Created at 2010-1-19 下午03:03:49
 */
public class CAPizza extends Pizza
{
	PizzaIngredientFactory ingredientFactory;

	/**
	 * 
	 */
	public CAPizza(PizzaIngredientFactory ingredientFactory)
	{
		this.ingredientFactory = ingredientFactory;
		setName("CAPizza");
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
	}

}
