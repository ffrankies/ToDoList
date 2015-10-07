package pack1;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TaskGUI extends JFrame implements ActionListener {

	private JTable table;
	private TaskList model;
	private JScrollPane scrollPane;
	private JButton add, close;
	private JPanel panel;
	
	public TaskGUI() {
		model = new TaskList();
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		panel = new JPanel();
		
		add = new JButton("Add");
		add.addActionListener(this);
		add.setMaximumSize(new Dimension(100,100));
		close = new JButton("Close");
		close.addActionListener(this);
		add.setMaximumSize(new Dimension(100,100));
		
		panel.add(scrollPane);
		panel.add(add);
		panel.add(close);
		add(panel);
		
		setSize(800,500);
		setUndecorated(true);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(button == add) {
			Task task = new Task();
			TaskWindow window = new TaskWindow(this,task,model);
			//model.add(task);
		}
		if(button == close)
			System.exit(0);
	}
	
	public static void main(String[] args) {
		new TaskGUI();
	}
}
