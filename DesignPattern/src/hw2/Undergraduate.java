/**
 * 
 */
package hw2;

import hw2.task.Task;
import hw2.task.TaskGenerator;

import java.util.List;

/**
 * @author Coddy created on 2010-5-5
 * 
 */
public class Undergraduate
{

	/**
	 * 
	 */
	private long id;
	private String name;

	public Undergraduate(long id, String name)
	{
		super();
		this.id = id;
		this.name = name;
		System.out.println(id + name);
	}

	/**
	 * 
	 */
	public void prepareForInternship()
	{
		TaskGenerator taskGenerator = TaskGenerator.getInstance();
		List<Task> list = taskGenerator.generateInternshipTaskList();
		carryOut(list);
	}

	public void prepareForGraduation()
	{
		TaskGenerator taskGenerator = TaskGenerator.getInstance();
		List<Task> list = taskGenerator.generateGraduationTaskList();
		carryOut(list);
	}

	/**
	 * 
	 */
	protected void carryOut(List<Task> taskList)
	{
		for (Task task : taskList)
		{
			task.execute();
		}
	}

}
