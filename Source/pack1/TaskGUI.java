package pack1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TaskGUI extends JFrame implements ActionListener {

	private JTable table;
	private TaskList model;
	private JScrollPane scrollPane;
	private JButton add, close;
	
	public TaskGUI() {
		model = new TaskList();
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		
		add = new JButton("Add");
		add.addActionListener(this);
		close = new JButton("Close");
		close.addActionListener(this);
		
		add(scrollPane);
		add(add);
		add(close);
		
		setUndecorated(true);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(button == add) {
			
		}
			
	}
	
}
