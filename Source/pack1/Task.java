package pack1;

import java.io.Serializable;

/**
 * @author Frank
 *
 */
public class Task implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String taskName;
	private String date;
	private boolean important;
	private boolean completed;
	
	public Task() {
		super();
		this.taskName = "Do this";
		this.date = "01/01/2015";
		this.important = false;
		this.important = true;
	}
	
	public Task(String taskName, String date, boolean important) {
		super();
		this.taskName = taskName;
		this.date = date;
		this.important = important;
		this.completed = false;
	}
	
	public String toString() {
		return taskName + "|" + date + "|" + important; 
	}
	
	public boolean equals(Task other) {
		return this.taskName.equals(other.taskName) && 
				this.date.equals(other.date) &&
				this.important == other.important;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the important
	 */
	public boolean isImportant() {
		return important;
	}

	/**
	 * @param important the important to set
	 */
	public void setImportant(boolean important) {
		this.important = important;
	}
	
	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
