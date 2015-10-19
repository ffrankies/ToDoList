package pack1;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Frank
 *
 */
public class Task implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** A short description of the task */
	private String taskName;
	
	/** A detailed description of the task */
	private String description;
	
	/** The due date for the task */
	private GregorianCalendar dueDate;
	
	/** Whether or not the task is urgent. Possibly unneeded */
	private boolean important;
	
	/** Whether or not the task has been completed */
	private boolean completed;
	
	/** The type of repetition the task has */
	private Repeat rep;
	
	/** Stores the particular days of the week on which the task 
	 * repeats */
	private ArrayList<String> weekdays;
	
	/** Stores the number of days between the due dates for the task */
	private int daysBetween;
	
	/** A formatter for the date of the task */
	private final SimpleDateFormat fmt = 
			new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * Default constructor sets all values to defaults
	 */
	public Task() {
		super();
		this.taskName = "Short task description";
		this.description = "Detailed description here";
		this.dueDate = new GregorianCalendar(TimeZone.getTimeZone(
				"EST"));
		this.rep = Repeat.NONE;
		this.weekdays = new ArrayList<String>();
		this.daysBetween = 0;
		this.completed = false;
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
	
	/**
	 * Converts the task to a readable string
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String str = taskName + "|" + description + "|" + 
				fmt.format(dueDate.getTime()) + "|";
		if(rep == Repeat.NUMDAY) {
			str += daysBetween + "|";
		}
		if(rep == Repeat.SPDAY) {
			for(String day: weekdays) {
				str += day + "|";
			}
		}
		str += important + "|" + completed; 
		return str;
	}
	
	/**
	 * Checks if a task is the same as another task
	 */
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
	
	public Repeat getRepeat() {
		return this.rep;
	}
	
	public void setRepeat(Repeat rep) {
		this.rep = rep;
	}
	
	public ArrayList<String> getWeekdays() {
		return this.weekdays;
	}
	
	public void setWeekdays(ArrayList<String> weekdays) {
		this.weekdays = weekdays;
	}
	
	public int getDaysBetween() {
		return this.daysBetween;
	}
	
	public void setDaysBetween(int daysBetween) {
		this.daysBetween = daysBetween;
	}
}
