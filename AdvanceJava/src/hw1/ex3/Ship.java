package hw1.ex3;

public class Ship implements Common
{
	int a, b, c;

	@Override
	public int computeTime(int a, int b, int c)
	{
		// TODO Auto-generated method stub
		return a + b * c;
	}

	public void setA(int a)
	{
		this.a = a;
	}

	public void setB(int b)
	{
		this.b = b;
	}

	public void setC(int c)
	{
		this.c = c;
	}
}