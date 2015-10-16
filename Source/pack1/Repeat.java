package pack1;

/*
 * Used to select how the task repeats itself
 * NONE: the task does not repeat - once it is completed, it is
 * 		deleted from the taskList upon next program startup
 * NUMDAY: the task repeats every set number of days - once it is
 * 		completed, the due date is updated to the specified number of 
 * 		days after it was due upon next startup
 * SPDAY: the task repeats every specified day of the week - once it is
 * 		completed, the due date is updated to the next day that matches
 * 		one of the specified days of the week
 */
public enum Repeat {
	NONE, NUMDAY, SPDAY
}
