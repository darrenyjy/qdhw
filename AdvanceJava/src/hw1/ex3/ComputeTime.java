package hw1.ex3;

public class ComputeTime
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		if (args.length != 4)
		{
			System.out.println("Error in the arguments!");
		} else
		{
			String name = "hw1.ex3." + args[0];
			int a = Integer.parseInt(args[1]);
			int b = Integer.parseInt(args[2]);
			int c = Integer.parseInt(args[3]);
			try
			{
				Common common = (Common) Class.forName(name).newInstance();
				System.out.println(common.computeTime(a, b, c));
			} catch (InstantiationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
