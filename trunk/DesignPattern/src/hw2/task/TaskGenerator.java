/**
 * 
 */
package hw2.task;

import java.util.List;

/**
 * @author Coddy created on 2010-5-5
 * 
 */
public class TaskGenerator
{
	private static TaskGenerator instance;
	private List<Task> taskList;
	private Task task;

	private TaskGenerator()
	{
	};

	public static TaskGenerator getInstance()
	{
		if (instance == null)
		{
			synchronized (TaskGenerator.class)
			{
				if (instance == null)
				{
					instance = new TaskGenerator();
				}
			}
		}
		return instance;
	}

	public List<Task> generateInternshipTaskList()
	{
		return taskList;
	};

	public List<Task> generateGraduationTaskList()
	{
		return taskList;
	};

	public Task createUploadTask()
	{
		return new UploadTask();
	}

	public Task createCheckBookTask()
	{
		return new CheckBookTask();
	}

	public Task createTask(String tasktype)
	{
		return task;
	}

	public Task createCheckPunishmentTask()
	{
		return new CheckPunishmentTask();
	}

	public Task createCheckCreditTask()
	{
		return new CheckCreditTask();
	}

	public Task createPayTuitionTask()
	{
		return new PayTuitionTask();
	}

	public Task createGetOpinionTask()
	{
		return new GetOpinionTask();
	}
}