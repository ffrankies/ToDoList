package pack1;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Frank
 *
 */
public class Task implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String taskName;
	private String description;
	private GregorianCalendar dueDate;
	private boolean important;
	private boolean completed;
	SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
	
	
	public Task() {
		super();
		this.taskName = "Do this";
		this.description = "A description of the task";
		this.dueDate = new GregorianCalendar(TimeZone.getTimeZone(
				"EST"));
		this.important = false;
		this.important = true;
		fmt.setLenient(false);
	}
	
//	public Task(String taskName, String date, boolean important) {
//		super();
//		this.taskName = taskName;
//		fmt.setLenient(false);
//		this.dueDate = new GregorianCalendar();
//		try {
//			dueDate.setTime(fmt.parse(date));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.important = important;
//		this.completed = false;
//		
//	}
	
//	public Task(String taskName, Date date, boolean important) {
//		super();
//		this.taskName = taskName;
//		this.dueDate.setTime(date);
//		this.important = important;
//		this.completed = false;
//		fmt.setLenient(false);
//	}
	
	public String toString() {
		return taskName + "|" + description + "|" + 
				fmt.format(dueDate.getTime()) + "|" + important + "|" 
				+ completed; 
	}
	
	public boolean equals(Task other) {
		return this.taskName.equals(other.taskName) && 
				this.dueDate.getTime().equals(other.dueDate.getTime()) 
				&& this.important == other.important;
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
	public GregorianCalendar getDate() {
		//return date;
		return dueDate;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		try {
			this.dueDate.setTime(fmt.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void setDate(GregorianCalendar dueDate) {
		this.dueDate = dueDate;
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
	
	/*
	 * 
	 */
	public String getDescription() {
		return description;
	}
	
	/*
	 * 
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
