package pack1;

import java.io.*;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TaskList extends AbstractTableModel{
	
	private ArrayList <Task> tasks;
	
	File file = new File ("taskList");
	
	private String[] columnNames = {"Task", "Due Date",  "Urgent?", 
			"Completed?"};
	
	public TaskList() {
		try {
			load();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add(Task task) {
		tasks.add(task);
		fireTableRowsInserted(0, tasks.size());
	}
	
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
	
	public void clear() {
		tasks.clear();
	}
	
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

	@Override
	public Object getValueAt(int row, int col) {
		switch (col) {
		case 0: 
			return tasks.get(row).getTaskName();
		case 1:
			return tasks.get(row).getDate();
		case 2:
			return tasks.get(row).isImportant();
		case 3:
			return tasks.get(row).isCompleted();
		default:
			return null;
		}
	}
}
