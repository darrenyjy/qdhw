package ch3.redo;

import ch3.redo.concretebeverage.DarkRoast;
import ch3.redo.concretebeverage.Espresso;
import ch3.redo.concretebeverage.HouseBlend;
import ch3.redo.condiment.Mocha;
import ch3.redo.condiment.Soy;
import ch3.redo.condiment.Whip;

public class StarbuzzCoffee
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		IBeverage beverage = new Espresso(EBeverageSize.TALL);
        System.out.println(beverage.getDescription() 
                + " $" + beverage.getCost());
 
        IBeverage beverage2 = new DarkRoast();
        beverage2 = new Mocha(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        beverage2 = new Soy(beverage2);
        System.out.println(beverage2.getDescription() 
                + " $" + beverage2.getCost());
 
        IBeverage beverage3 = new HouseBlend(EBeverageSize.VENTI);
        beverage3 = new Soy(beverage3);
        beverage3 = new Mocha(beverage3);
        beverage3 = new Whip(beverage3);
        System.out.println(beverage3.getDescription() 
                + " $" + beverage3.getCost());
	}

}
