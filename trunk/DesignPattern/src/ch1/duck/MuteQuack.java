package ch1.duck;

public class MuteQuack implements QuackBehavior
{

	public void quack()
	{
		System.out.println("<Silence...>");
	}
}
