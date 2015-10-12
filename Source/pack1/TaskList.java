package pack1;

import java.io.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class TaskList extends AbstractTableModel {

	/**
	 * Default serial number
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList <Task> tasks;

	File file = new File ("taskList");

	private String[] columnNames = {"Task", "Due Date",  "Urgent?", 
	"Completed?"};

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
		if(col == 2 || col == 3)
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
			getTask(row).setImportant((boolean) value);
		if(col == 3)
			getTask(row).setCompleted((boolean) value);
		fireTableCellUpdated(row, col);
	}
	
	/*@Override
    public Class getColumnClass(int column) {
    return getValueAt(0, column).getClass();
    }*/
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
			return tasks.get(row).isImportant();
		case 3:
			return tasks.get(row).isCompleted();
		default:
			return null;
		}
	}

	//	@Override
	//	public void tableChanged(TableModelEvent e) {
	//		if(e.getSource().getClass()==Boolean.class){
	//			getSelectedRow().
	//		}
	//			
	//	}
}
