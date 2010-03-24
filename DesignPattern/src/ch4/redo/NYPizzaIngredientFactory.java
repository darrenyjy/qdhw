/**
 * 
 */
package ch4.redo;

/**
 * @author Coddy Created at 2010-1-19 下午02:24:24
 */
public class NYPizzaIngredientFactory implements PizzaIngredientFactory
{

	/**
	 * 
	 */
	public NYPizzaIngredientFactory()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public Cheese createCheese()
	{
		// TODO Auto-generated method stub
		return new NYCheese();
	}

	@Override
	public Dough createDough()
	{
		// TODO Auto-generated method stub
		return new NYDough();
	}

	@Override
	public Sauce createSauce()
	{
		// TODO Auto-generated method stub
		return new NYSauce();
	}

}
