/**
 * 
 */
package ch4.redo;

/**
 * @author Coddy Created at 2010-1-19 下午02:28:48
 */
public class CAPizzaIngredientFactory implements PizzaIngredientFactory
{

	/**
	 * 
	 */
	public CAPizzaIngredientFactory()
	{
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch4.redo.PizzaIngredientFactory#createCheese()
	 */
	@Override
	public Cheese createCheese()
	{
		// TODO Auto-generated method stub
		return new CACheese();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch4.redo.PizzaIngredientFactory#createDough()
	 */
	@Override
	public Dough createDough()
	{
		// TODO Auto-generated method stub
		return new CADough();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch4.redo.PizzaIngredientFactory#createSauce()
	 */
	@Override
	public Sauce createSauce()
	{
		// TODO Auto-generated method stub
		return new CASauce();
	}

}
