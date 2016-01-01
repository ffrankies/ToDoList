package pack1;

import java.io.*;
//import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

//import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class TaskList extends AbstractTableModel {

	/**
	 * Default serial number
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList <Task> tasks;

	private File file = new File ("taskList");

	private String filePath = "C:\\Users\\" + 
			System.getProperty("user.name") + "\\AppData\\Roaming\\Microsoft\\"
			+ "Windows\\Start Menu\\Programs\\Startup\\ToDoList.bat";

	private File startup = new File (filePath);

	private String[] columnNames = {"Task", "Due Date", "Completed?"};

	/*
	 * Automatically loads saved tasks from taskList file
	 */
	public TaskList() {
		try {
			load();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		startUp();
	}

	/*
	 * Adds task to arrayList of tasks
	 */
	public void add(Task task) {
		tasks.add(task);
		fireTableRowsInserted(0, tasks.size());
	}

	/*
	 * Deletes task from arrayList of tasks
	 */
	public void remove(Task task) {
		tasks.remove(task);
		fireTableRowsDeleted(0, tasks.size());
	}

	public Task getTask(int i) {
		return tasks.get(i);
	}

	public int getSize() {
		return tasks.size();
	}

	public ArrayList <Task> getTasks() {
		return this.tasks;
	}

	/*
	 * Deletes all tasks from arrayList
	 */
	public void clear() {
		tasks.clear();
	}

	/*
	 * Saves contents of taskList to file as an arrayList of tasks to
	 * be performed
	 */
	public void save() {
		try {
			file.delete();
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(tasks);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Loads contents of file - should be serialized arrayList of 
	 * tasks
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		tasks = new ArrayList<Task>();
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream is = new ObjectInputStream(fis);
			tasks = (ArrayList <Task> ) is.readObject();
			fireTableRowsInserted(0, tasks.size() - 1);
			is.close();
		} catch (Exception ex) {	//if no save file, create one
			save();
		}
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return tasks.size();
	}

	/*
	 * Changes column headings to those in columnNames array
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/*
	 * Should make columns 2 and 3 editable
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int col) { 
		if(col == 2)
			return true;
		else
			return false;
	}

	/*
	 * Allows the Important and Completed values of Tasks in table
	 * to be changed by clicking on the checkboxes.
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object value, int row, int col) {
		if(col == 2)
			getTask(row).setCompleted((boolean) value);
		fireTableCellUpdated(row, col);
	}

	/*@Override
    public Class getColumnClass(int column) {
    return getValueAt(0, column).getClass();
    }*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int column) {
		switch (column) {
		case 0:
			return String.class;
		case 1:
			return String.class;
			//            case 2:
			//                return Boolean.class;
			//            case 3:
			//                return Boolean.class;
		default:
			return Boolean.class;
		}
	}

	/*
	 * Shows taskList values in table
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {
		switch (col) {
		case 0: 
			return tasks.get(row).getTaskName();
		case 1:
			//return tasks.get(row).getDate().getTime();
			return DateFormat.getDateInstance(DateFormat.SHORT).format(
					tasks.get(row).getDate().getTime());
			//return "ABC";
		case 2:
			return tasks.get(row).isCompleted();
		default:
			return null;
		}
	}

	/*
	 * Performs the right operation when a task is marked as completed:
	 * If a task does not repeat, removes task from taskList
	 * Else, sets the task's due date to the next appropriate date
	 */
	public void removeCompleted() {
		ArrayList<Task> rem = new ArrayList<Task>();
		for(Task temp: tasks) {
			if(temp.isCompleted()) {
				Repeat rep = temp.getRepeat();
				//If task doesn't repeat, remove from list of tasks
				if(rep == Repeat.NONE) {
					//remove(temp);
					rem.add(temp);
					//If task repeats ever x days, add x days to current
					//date and set that as task's new due date
				} else if(rep == Repeat.NUMDAY) {
					GregorianCalendar cal = new GregorianCalendar();
					Date date = new Date();
					cal.setTime(date);
					cal.add(Calendar.DATE, temp.getDaysBetween());
					temp.setDate(cal);
					temp.setCompleted(false);
					//If task repeats on specific weekdays, keep adding 1
					//to task's due date until the date of the due date 
					//matches one of the days of the week it's supposed
					//to repeat on
				} else if(rep == Repeat.SPDAY) {
					GregorianCalendar cal = temp.getDate();
					do {
						cal.add(Calendar.DATE, 1);
					}
					while(!temp.getWeekdays().contains(
							cal.get(Calendar.DAY_OF_WEEK)));
					temp.setDate(cal);
					temp.setCompleted(false);
				}
			}
		}
		for(Task temp: rem)
			tasks.remove(temp);
	}

	/*
	 *Makes program run at startup by editing windows registry
	 */
	private void startUp() {
		//		try {
		//			WinRegistry.deleteValue(WinRegistry.HKEY_CURRENT_USER, 
		//					"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 
		//					"java -jar ToDoList v1.00.jar");
		//		} catch (IllegalArgumentException | IllegalAccessException
		//				| InvocationTargetException e) {
		//			e.printStackTrace();
		//		}
		String fileName = "ToDoList.jar";
		//		String filePath = "%appdata%\\Microsoft\\Windows\\Start Menu\\"
		//				+ "Programs\\Startup\\ToDoList.bat";
		String info = "start \"\" \"" + System.getProperty(
				"user.dir") + "\\" + fileName + "\"" + "\n" + "exit";
		try {
			@SuppressWarnings("resource")
			Scanner fileReader = new Scanner(startup);
			while(fileReader.hasNext()){
				@SuppressWarnings("unused")
				String temp = fileReader.nextLine();
			}
//			FileInputStream fis = new FileInputStream(startup);
//			ObjectInputStream is = new ObjectInputStream(fis);
//			String temp = (String) is.readObject();
//			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				startup.delete();
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				PrintWriter out = null;
				try {
					out = new PrintWriter(new BufferedWriter(new 
							FileWriter(startup)));
					out.println(info);
					out.close();
				}
				catch (IOException e2) {
					e.printStackTrace();
				}
			}
		}
		//		String value = "\"javaw -jar " + System.getProperty(
		//				"user.dir") + "\\ToDoList v1.00.jar\"";
		//		String valueOld = "";
		//		String name = "ToDoList v1.00.jar";
		//		String nameOld = "";
		//		try {
		//			FileInputStream fis = new FileInputStream(startup);
		//			ObjectInputStream is = new ObjectInputStream(fis);
		//			valueOld = (String) is.readObject();
		//			nameOld = (String) is.readObject();
		//			is.close();
		//		}
		//		catch (Exception e) {
		//			//JOptionPane.showMessageDialog(null, "Couldn't do startup1");
		//			e.printStackTrace();
		//		}
		//		if(!valueOld.equals(value)) {
		//			try {
		//				try {
		//					WinRegistry.deleteValue(WinRegistry.HKEY_CURRENT_USER, 
		//							"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 
		//							"java -jar " + nameOld);
		//				} catch (IllegalArgumentException | IllegalAccessException
		//						| InvocationTargetException e) {
		//					e.printStackTrace();
		//				}
		//				startup.delete();
		//				FileOutputStream fos = new FileOutputStream(startup);
		//				ObjectOutputStream os = new ObjectOutputStream(fos);
		//				os.writeObject(value);
		//				os.writeObject(name);
		//				os.close();
		//				try {
		//					WinRegistry.writeStringValue(
		//							WinRegistry.HKEY_CURRENT_USER, 
		//							"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 
		//							"java -jar ToDoList v1.00.jar", value);
		//				} catch (Exception e2) {
		//					JOptionPane.showMessageDialog(null, "Couldn't do startup2");
		//				}
		//			}
		//			catch (IOException e2) {
		//				JOptionPane.showMessageDialog(null, "Couldn't do startup3");
		//			}
		//		}
	}
}
