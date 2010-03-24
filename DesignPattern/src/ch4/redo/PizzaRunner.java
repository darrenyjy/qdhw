package ch4.redo;

public class PizzaRunner
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		PizzaStore store = new CAPizzaStore();
		Pizza pizza = store.producePizza("cheese");
		System.out.println(pizza);

		pizza = store.producePizza("other");
		System.out.println(pizza);

		store = new NYPizzaStore();
		pizza = store.producePizza("cheese");
		System.out.println(pizza);

		pizza = store.producePizza("other");
		System.out.println(pizza);

	}

}
